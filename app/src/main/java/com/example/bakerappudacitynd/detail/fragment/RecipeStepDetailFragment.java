package com.example.bakerappudacitynd.detail.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.network.StepsItem;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepDetailFragment extends Fragment {

    private static final String TAG = RecipeStepDetailFragment.class.getSimpleName();
    private static final String VIDEO_POSITION = "videoPosition";
    private static final String VIDEO_WINDOW = "videoWindow";
    private static final String BUNDLE_STEP_ITEM_DATA = "bundle_step_item_data";

    private StepsItem stepsItem;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView exoPlayerView;
    private Long videoPosition;
    private int currentWindow;

    private TextView stepDescriptionTextView;
    private TextView stepDetailNoDataTextView;

    public static RecipeStepDetailFragment newInstance(StepsItem stepsItem) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_STEP_ITEM_DATA, stepsItem);
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if ((arguments != null) && (arguments.containsKey(BUNDLE_STEP_ITEM_DATA))){
            stepsItem = arguments.getParcelable(BUNDLE_STEP_ITEM_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        initViews(rootView);

        if (stepsItem != null) {
            stepDetailNoDataTextView.setVisibility(View.GONE);
            stepDescriptionTextView.setVisibility(View.VISIBLE);
            stepDescriptionTextView.setText(stepsItem.getDescription());
            if (!TextUtils.isEmpty(stepsItem.getVideoURL())) {
                exoPlayerView.setVisibility(View.VISIBLE);
                initMediaSession();
                initPlayer(stepsItem.getVideoURL());
            } else {
                exoPlayerView.setVisibility(View.GONE);
            }
        } else {
            stepDetailNoDataTextView.setVisibility(View.VISIBLE);
            exoPlayerView.setVisibility(View.GONE);
            stepDescriptionTextView.setVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(StepsItem.KEY_STEP_DATA, stepsItem);
        if (exoPlayer != null) {
            videoPosition = exoPlayer.getCurrentPosition();
            outState.putLong(VIDEO_POSITION, videoPosition);
            currentWindow = exoPlayer.getCurrentWindowIndex();
            outState.putInt(VIDEO_WINDOW, currentWindow);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            stepsItem = savedInstanceState.getParcelable(StepsItem.KEY_STEP_DATA);
            videoPosition = savedInstanceState.getLong(VIDEO_POSITION);
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
            if (videoPosition != null) {
                exoPlayer.seekTo(currentWindow, videoPosition);
            }
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.release();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            videoPosition = exoPlayer.getCurrentPosition();
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
        MediaSessionCompat mediaSession = new MediaSessionCompat(getContext(), TAG);
        mediaSession.setActive(true);
    }

    private void initViews(View rootView) {
        exoPlayerView = rootView.findViewById(R.id.step_detail_exo_player);
        stepDescriptionTextView = rootView.findViewById(R.id.step_detail_video_description);
        stepDetailNoDataTextView = rootView.findViewById(R.id.step_detail_no_data_tv);
    }
}
