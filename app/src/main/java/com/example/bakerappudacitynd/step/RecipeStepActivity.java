package com.example.bakerappudacitynd.step;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.bakerappudacitynd.BaseMvpActivity;
import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.detail.fragment.RecipeStepDetailFragment;
import com.example.bakerappudacitynd.network.StepsItem;

import java.util.ArrayList;

import static com.example.bakerappudacitynd.detail.DetailActivity.KEY_STEPS_ITEM_ID;
import static com.example.bakerappudacitynd.detail.DetailActivity.KEY_STEPS_LIST;

public class RecipeStepActivity extends BaseMvpActivity<RecipeStepView, RecipeStepPresenter> implements RecipeStepView {

    private FragmentManager fragmentManager;
    private int itemsQuantity;
    private ArrayList<StepsItem> stepsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepsList = getIntent().getExtras().getParcelableArrayList(KEY_STEPS_LIST);
        int stepId = getIntent().getExtras().getInt(KEY_STEPS_ITEM_ID, 0);
        initFragment();
        displayRecipeStepDetail(stepsList.get(stepId));
        itemsQuantity = stepsList.size();
        StepViewModel model = ViewModelProviders.of(this).get(StepViewModel.class);
        model.getPreviousSelected().observe(this, item -> {
            int currentId = item.getId();
            displayRecipeStepDetail(stepsList.get(currentId - 1));
        });
        model.getNextSelected().observe(this, item -> {
            int currentId = item.getId();
            if (currentId != itemsQuantity) {
                displayRecipeStepDetail(stepsList.get(currentId + 1));
            }
        });
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_step;
    }

    @Override
    public void displayRecipeStepDetail(StepsItem step) {
        Fragment recipeDetailFragment = RecipeStepDetailFragment.newInstance(step, stepsList);
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, recipeDetailFragment)
                .commit();
    }

    @NonNull
    @Override
    public RecipeStepPresenter createPresenter() {
        return new RecipeStepPresenter();
    }
}
