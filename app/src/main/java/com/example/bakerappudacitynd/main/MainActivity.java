package com.example.bakerappudacitynd.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.bakerappudacitynd.BaseMvpActivity;
import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.adapter.RecipeRecyclerAdapter;
import com.example.bakerappudacitynd.network.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements MainView {

    @BindView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;
    @BindView(R.id.main_progress_bar)
    ProgressBar mainProgressBar;

    private RecipeRecyclerAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initUI();
        presenter.loadData();
    }

    private void initUI() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mainRecyclerView.setLayoutManager(layoutManager);

    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return null;
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onLoadCompleted(List<Recipe> data) {

    }

    @Override
    public void onLoadError() {

    }
}
