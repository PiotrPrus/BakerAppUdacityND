package com.example.bakerappudacitynd.step;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.bakerappudacitynd.BaseMvpActivity;
import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.detail.fragment.RecipeStepDetailFragment;
import com.example.bakerappudacitynd.network.StepsItem;

public class RecipeStepActivity extends BaseMvpActivity<RecipeStepView, RecipeStepPresenter> implements RecipeStepView {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StepsItem stepsItem = getIntent().getExtras().getParcelable(StepsItem.KEY_STEP_DATA);
        initFragment();
        displayRecipeStepDetail(stepsItem);
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
        Fragment recipeDetailFragment = RecipeStepDetailFragment.newInstance(step);
        fragmentManager.beginTransaction().add(R.id.step_container, recipeDetailFragment).commit();
    }

    @NonNull
    @Override
    public RecipeStepPresenter createPresenter() {
        return new RecipeStepPresenter();
    }
}
