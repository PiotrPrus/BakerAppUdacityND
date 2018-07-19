package com.example.bakerappudacitynd.detail.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.network.StepsItem;
import com.example.bakerappudacitynd.step.StepViewModel;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepDetailFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String TAG = RecipeStepDetailFragment.class.getSimpleName();
    private static final String VIDEO_POSITION = "playbackPosition";
    private static final String VIDEO_WINDOW = "videoWindow";
    private static final String PLAYER_POSITION = "playback_position";
    private static final String PLAYBACK_READY = "playback_ready";
    private static final String BUNDLE_STEP_ITEM_DATA = "bundle_step_item_data";
    private static final String KEY_STEPS_LIST = "key_steps_list";

    private StepsItem stepsItem;
    private ArrayList stepsList;

    private SimpleExoPlayer exoPlayer;
    private long playbackPosition;
    private boolean playbackReady = true;
    private int currentWindow;
    private PlaybackStateCompat.Builder mStateBuilder;

    private TextView stepDescriptionTextView;
    private ImageView previousStepArrow;
    private ImageView nextStepArrow;
    private SimpleExoPlayerView exoPlayerView;

    private StepViewModel model;
    private MediaSessionCompat mediaSession;

    public static RecipeStepDetailFragment newInstance(StepsItem stepsItem, ArrayList<StepsItem> stepsList) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_STEP_ITEM_DATA, stepsItem);
        bundle.putParcelableArrayList(KEY_STEPS_LIST, stepsList);
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(StepViewModel.class);
        Bundle arguments = getArguments();
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYER_POSITION);
            playbackReady = savedInstanceState.getBoolean(PLAYBACK_READY);
        }
        if ((arguments != null) && (arguments.containsKey(BUNDLE_STEP_ITEM_DATA))) {
            stepsItem = arguments.getParcelable(BUNDLE_STEP_ITEM_DATA);
            stepsList = arguments.getParcelableArrayList(KEY_STEPS_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        initViews(rootView);
        initArrowListeners();

        if (stepsItem != null) {
            stepDescriptionTextView.setText(stepsItem.getDescription());
            if (!TextUtils.isEmpty(stepsItem.getVideoURL())) {
                initMediaSession();
                initPlayer(stepsItem.getVideoURL());
                stepDescriptionTextView.setVisibility(View.VISIBLE);
                exoPlayerView.setVisibility(View.VISIBLE);
            } else {
                exoPlayerView.setVisibility(View.GONE);
            }
        } else {
            stepDescriptionTextView.setVisibility(View.GONE);
            exoPlayerView.setVisibility(View.GONE);
        }
        return rootView;
    }

    private void initArrowListeners() {
        previousStepArrow.setOnClickListener(view -> model.selectPrevious(stepsItem));
        nextStepArrow.setOnClickListener(view -> model.selectNext(stepsItem));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playbackReady = exoPlayer.getPlayWhenReady();
        }
        outState.putLong(VIDEO_POSITION, playbackPosition);
        outState.putBoolean(PLAYBACK_READY, playbackReady);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            stepsItem = savedInstanceState.getParcelable(StepsItem.KEY_STEP_DATA);
            playbackPosition = savedInstanceState.getLong(VIDEO_POSITION);
            currentWindow = savedInstanceState.getInt(VIDEO_WINDOW);
        }
    }

    private void initPlayer(String videoURL) {
        Uri uri = Uri.parse(videoURL);
        if (exoPlayer == null) {
            TrackSelector selector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), selector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);
            String userAgent = Util.getUserAgent(getContext(), "RecipeStepVideo");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(playbackReady);
            exoPlayer.seekTo(currentWindow, playbackPosition);
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playbackReady = exoPlayer.getPlayWhenReady();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playbackPosition = exoPlayer.getCurrentPosition();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (!isVisibleToUser) {
                if (exoPlayer != null) {
                    exoPlayer.setPlayWhenReady(false);
                }
            }
        }
    }

    private void initMediaSession() {
        mediaSession = new MediaSessionCompat(getContext(), TAG);
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(mStateBuilder.build());
        mediaSession.setActive(true);
    }

    private void initViews(View rootView) {
        exoPlayerView = rootView.findViewById(R.id.step_detail_exo_player);
        stepDescriptionTextView = rootView.findViewById(R.id.step_detail_video_description);
        previousStepArrow = rootView.findViewById(R.id.step_detail_arrow_left);
        nextStepArrow = rootView.findViewById(R.id.step_detail_arrow_rigt);
        if (stepsItem.getId() == 0) {
            previousStepArrow.setVisibility(View.GONE);
        } else if (stepsItem.getId() == (stepsList.size() - 1)) {
            nextStepArrow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    exoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
