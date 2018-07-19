package com.example.bakerappudacitynd.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.bakerappudacitynd.BaseMvpActivity;
import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.adapter.RecipeStepAdapter;
import com.example.bakerappudacitynd.detail.fragment.RecipeStepDetailFragment;
import com.example.bakerappudacitynd.detail.fragment.RecipeStepsFragment;
import com.example.bakerappudacitynd.main.MainActivity;
import com.example.bakerappudacitynd.network.Recipe;
import com.example.bakerappudacitynd.network.StepsItem;
import com.example.bakerappudacitynd.step.RecipeStepActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends BaseMvpActivity<DetailView, DetailPresenter> implements DetailView {

    public static final String KEY_STEPS_LIST = "key_steps_list";
    public static final String KEY_STEPS_ITEM_ID = "key_steps_item_id";
    public static final String SHARED_PREFS = "baker_app_preferences";
    public static final String RECIPE_NAME = "recipe_name";
    private RecipeStepsFragment recipeStepsFragment;
    private FragmentManager fragmentManager;

    private boolean isTablet = false;

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
        saveRecipeToSharedPrefs(recipe);
        if (findViewById(R.id.steps_item_container) != null) {
            isTablet = true;
        }
        initFragment();
        showRecipeDetail(recipe);
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

    private void saveRecipeToSharedPrefs(Recipe recipe) {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = preferences.edit();
        prefEditor.putString(RECIPE_NAME, recipe.getName()).apply();
    }

    private void showStepDetailActivity(List<StepsItem> stepsList, int stepId) {
        Intent intent = new Intent(this, RecipeStepActivity.class);
        ArrayList<StepsItem> parcelableList = new ArrayList<>(stepsList);
        intent.putParcelableArrayListExtra(KEY_STEPS_LIST, parcelableList);
        intent.putExtra(KEY_STEPS_ITEM_ID, stepId);
        startActivity(intent);
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        recipeStepsFragment = new RecipeStepsFragment();
        if (isTablet) {
            RecipeStepDetailFragment recipeStepsDetailFragment = new RecipeStepDetailFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_item_container, recipeStepsDetailFragment)
                    .commit();
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
        fragmentManager.beginTransaction()
                .replace(R.id.detail_container, recipeStepsFragment)
                .commit();
    }
}
