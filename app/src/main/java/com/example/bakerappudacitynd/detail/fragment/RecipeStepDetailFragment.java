package com.example.bakerappudacitynd.detail.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.network.StepsItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepDetailFragment extends Fragment {

    private StepsItem stepsItem;

    private TextView videoUrlTextView;
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
            videoUrlTextView.setText(stepsItem.getVideoURL());
            stepDescriptionTextView.setText(stepsItem.getDescription());
        }
        return rootView;
    }

    private void initViews(View rootView) {
        videoUrlTextView = rootView.findViewById(R.id.step_detail_video_url);
        stepDescriptionTextView = rootView.findViewById(R.id.step_detail_video_description);
    }
}
