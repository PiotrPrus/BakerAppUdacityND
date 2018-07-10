package com.example.bakerappudacitynd.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bakerappudacitynd.BaseMvpActivity;
import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.adapter.RecipeRecyclerAdapter;
import com.example.bakerappudacitynd.detail.DetailActivity;
import com.example.bakerappudacitynd.network.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements MainView, RecipeRecyclerAdapter.RecipeOnClickListener {

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
        adapter = new RecipeRecyclerAdapter(this);
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(getMvpView());
    }

    @Override
    public void onStartLoading() {
        mainRecyclerView.setVisibility(View.GONE);
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadCompleted(List<Recipe> data) {
        mainProgressBar.setVisibility(View.GONE);
        adapter.setData(data);
        mainRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mainRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadError() {
        mainProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Recipe recipe) {
        Toast.makeText(this, "Recipe will be opened in next version", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Recipe.KEY_RECIPE_DATA, recipe);
        startActivity(intent);
    }
}
