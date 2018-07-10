package com.example.bakerappudacitynd.detail;

import com.example.bakerappudacitynd.network.Recipe;
import com.hannesdorfmann.mosby.mvp.MvpView;

public interface DetailView extends MvpView {
    void showRecipeDetail(Recipe recipe);
}
