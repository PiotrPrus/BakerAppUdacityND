package com.example.bakerappudacitynd.step;

import android.support.annotation.NonNull;

import com.example.bakerappudacitynd.BaseMvpActivity;
import com.example.bakerappudacitynd.network.StepsItem;

public class RecipeStepActivity extends BaseMvpActivity<RecipeStepView, RecipeStepPresenter> implements RecipeStepView {
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void displayRecipeStepDetail(StepsItem step) {

    }

    @NonNull
    @Override
    public RecipeStepPresenter createPresenter() {
        return new RecipeStepPresenter();
    }
}
