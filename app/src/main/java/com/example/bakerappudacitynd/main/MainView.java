package com.example.bakerappudacitynd.main;

import com.example.bakerappudacitynd.network.Recipe;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

public interface MainView extends MvpView {
    void onStartLoading();
    void onLoadCompleted(List<Recipe> data);
    void onLoadError();
}
