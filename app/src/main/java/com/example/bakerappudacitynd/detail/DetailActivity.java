package com.example.bakerappudacitynd.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.example.bakerappudacitynd.BaseMvpActivity;
import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.detail.fragment.RecipeStepsFragment;
import com.example.bakerappudacitynd.network.Recipe;

import java.util.Objects;

public class DetailActivity extends BaseMvpActivity<DetailView, DetailPresenter> implements DetailView {

    private RecipeStepsFragment recipeStepsFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Recipe recipe = Objects.requireNonNull(getIntent().getExtras()).getParcelable(Recipe.KEY_RECIPE_DATA);
        showRecipeDetail(recipe);
        initFragment();
    }

    private void initFragment() {
        recipeStepsFragment = new RecipeStepsFragment();
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @NonNull
    @Override
    public DetailPresenter createPresenter() {
        return new DetailPresenter();
    }

    @Override
    public void showRecipeDetail(Recipe recipe) {
        fragmentManager.beginTransaction().add(R.id.detail_container, recipeStepsFragment).commit();
    }
}
