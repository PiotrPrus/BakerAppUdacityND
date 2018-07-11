package com.example.bakerappudacitynd.step;

import com.example.bakerappudacitynd.network.StepsItem;
import com.hannesdorfmann.mosby.mvp.MvpView;

public interface RecipeStepView extends MvpView {
    void displayRecipeStepDetail(StepsItem step);
}
