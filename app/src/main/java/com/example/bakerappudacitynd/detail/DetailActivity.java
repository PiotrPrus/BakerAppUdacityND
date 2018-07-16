package com.example.bakerappudacitynd.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.bakerappudacitynd.BaseMvpActivity;
import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.adapter.RecipeStepAdapter;
import com.example.bakerappudacitynd.detail.fragment.RecipeStepDetailFragment;
import com.example.bakerappudacitynd.detail.fragment.RecipeStepsFragment;
import com.example.bakerappudacitynd.network.Recipe;
import com.example.bakerappudacitynd.network.StepsItem;
import com.example.bakerappudacitynd.step.RecipeStepActivity;

import java.util.Objects;

public class DetailActivity extends BaseMvpActivity<DetailView, DetailPresenter> implements DetailView {

    private RecipeStepsFragment recipeStepsFragment;
    private RecipeStepDetailFragment recipeStepsDetailFragment;
    private FragmentManager fragmentManager;

    private boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Recipe recipe = Objects.requireNonNull(getIntent().getExtras()).getParcelable(Recipe.KEY_RECIPE_DATA);
        if (findViewById(R.id.steps_item_container) != null) {
            isTablet = true;
        }
        initFragment();
        showRecipeDetail(recipe);
        SharedViewModel model = ViewModelProviders.of(this).get(SharedViewModel.class);
        model.getSelected().observe(this, stepsItem -> {
            if (isTablet) {
                Fragment recipeDetailFragment = RecipeStepDetailFragment.newInstance(stepsItem);
                fragmentManager.beginTransaction().replace(R.id.steps_item_container, recipeDetailFragment).commit();
            } else {
                Intent intent = new Intent(this, RecipeStepActivity.class);
                intent.putExtra(StepsItem.KEY_STEP_DATA, stepsItem);
                startActivity(intent);
            }
        });
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        recipeStepsFragment = new RecipeStepsFragment();
        if (isTablet) {
            recipeStepsDetailFragment = new RecipeStepDetailFragment();
            fragmentManager.beginTransaction().replace(R.id.steps_item_container, recipeStepsDetailFragment).commit();
        }
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
        recipeStepsFragment.setIngredientsList(recipe.getIngredients());
        recipeStepsFragment.setStepsList(recipe.getSteps());
        fragmentManager.beginTransaction().add(R.id.detail_container, recipeStepsFragment).commit();
    }
}
