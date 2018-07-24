package com.example.bakerappudacitynd.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.bakerappudacitynd.BaseMvpActivity;
import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.detail.fragment.RecipeStepDetailFragment;
import com.example.bakerappudacitynd.detail.fragment.RecipeStepsFragment;
import com.example.bakerappudacitynd.network.Recipe;
import com.example.bakerappudacitynd.network.StepsItem;
import com.example.bakerappudacitynd.step.RecipeStepActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends BaseMvpActivity<DetailView, DetailPresenter> implements DetailView {

    public static final String KEY_STEPS_LIST = "key_steps_list";
    public static final String KEY_STEPS_ITEM_ID = "key_steps_item_id";
    private FragmentManager fragmentManager;

    private boolean isTablet = false;
    private Fragment fragment;

    public static Intent getIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Recipe.KEY_RECIPE_DATA, recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Recipe recipe = Objects.requireNonNull(getIntent().getExtras()).getParcelable(Recipe.KEY_RECIPE_DATA);
        setTitle(recipe.getName());
        isTablet = getResources().getBoolean(R.bool.isTablet);
        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.steps_item_container);

        if (savedInstanceState == null) {
            showRecipeDetail(recipe);
            if (isTablet) {
                Fragment recipeDetailFragment = RecipeStepDetailFragment.newInstance(recipe.getSteps().get(0), (ArrayList<StepsItem>) recipe.getSteps());
                fragmentManager.beginTransaction()
                        .replace(R.id.steps_item_container, recipeDetailFragment)
                        .commit();
            }
        }
        SharedViewModel model = ViewModelProviders.of(this).get(SharedViewModel.class);
        model.getSelected().observe(this, stepsItem -> {
            if (isTablet) {
                Fragment recipeDetailFragment = RecipeStepDetailFragment.newInstance(stepsItem, (ArrayList<StepsItem>) recipe.getSteps());
                fragmentManager.beginTransaction()
                        .replace(R.id.steps_item_container, recipeDetailFragment)
                        .commit();
            } else {
                showStepDetailActivity(recipe.getSteps(), stepsItem.getId());
            }
        });
    }

    private void showStepDetailActivity(List<StepsItem> stepsList, int stepId) {
        Intent intent = new Intent(this, RecipeStepActivity.class);
        ArrayList<StepsItem> parcelableList = new ArrayList<>(stepsList);
        intent.putParcelableArrayListExtra(KEY_STEPS_LIST, parcelableList);
        intent.putExtra(KEY_STEPS_ITEM_ID, stepId);
        startActivity(intent);
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
        fragment = RecipeStepsFragment.newInstance(recipe);
        fragmentManager.beginTransaction()
                .add(R.id.detail_container, fragment)
                .commit();
    }
}
