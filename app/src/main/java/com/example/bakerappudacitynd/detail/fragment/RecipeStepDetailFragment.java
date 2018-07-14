package com.example.bakerappudacitynd.detail.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
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
import com.google.android.exoplayer2.trackselection.TrackSelection;
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

    private StepsItem stepsItem;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView exoPlayerView;
    private Long videoPosition;
    private int currentWindow;

    private TextView stepDescriptionTextView;

    public RecipeStepDetailFragment() {
        // Required empty public constructor
    }

    public void setStepItem(StepsItem stepsItem) {
        this.stepsItem = stepsItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        initViews(rootView);

        if (stepsItem != null) {
            initMediaSession();
            initPlayer(stepsItem.getVideoURL());
            stepDescriptionTextView.setText(stepsItem.getDescription());
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
        if (savedInstanceState != null){
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
            videoPosition= exoPlayer.getCurrentPosition();
            exoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void initMediaSession() {
        MediaSessionCompat mediaSession = new MediaSessionCompat(getContext(), TAG);
        mediaSession.setActive(true);
    }

    private void initViews(View rootView) {
        exoPlayerView = rootView.findViewById(R.id.step_detail_exo_player);
        stepDescriptionTextView = rootView.findViewById(R.id.step_detail_video_description);
    }
}
