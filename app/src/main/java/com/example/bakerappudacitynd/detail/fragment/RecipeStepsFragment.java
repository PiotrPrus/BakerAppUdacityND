package com.example.bakerappudacitynd.detail.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.adapter.RecipeStepAdapter;
import com.example.bakerappudacitynd.detail.SharedViewModel;
import com.example.bakerappudacitynd.network.IngredientsItem;
import com.example.bakerappudacitynd.network.Recipe;
import com.example.bakerappudacitynd.network.StepsItem;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepsFragment extends Fragment implements RecipeStepAdapter.StepOnCLickListener {

    private static final String BUNDLE_DATA_RECIPE = "BUNDLE_DATA_RECIPE";
    private SharedViewModel model;

    private List<StepsItem> stepsList;
    private RecyclerView stepsRecyclerView;
    private TextView ingredientsView;
    private List<IngredientsItem> ingredientsList;

    public RecipeStepsFragment() {
        // Required empty public constructor
    }

    public static RecipeStepsFragment newInstance(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_DATA_RECIPE, recipe);
        RecipeStepsFragment fragment = new RecipeStepsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        Bundle arguments = getArguments();

        if (arguments != null) {
            Recipe recipe = arguments.getParcelable(BUNDLE_DATA_RECIPE);
            stepsList = recipe.getSteps();
            ingredientsList = recipe.getIngredients();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        initViews(rootView);

        if (ingredientsList != null) {
            StringBuilder builder = new StringBuilder();
            builder.append("Ingredients: ");
            builder.append("\n").append("\n");
            for (IngredientsItem item : ingredientsList) {
                builder.append(ingredientsList.indexOf(item) + 1).append(". ");
                builder.append(item.getIngredient());
                builder.append("\n");
            }
            ingredientsView.setText(builder.toString());
        }
        if (stepsList != null) {
            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            RecipeStepAdapter stepAdapter = new RecipeStepAdapter(this);
            stepAdapter.setData(stepsList);
            stepsRecyclerView.setAdapter(stepAdapter);
            stepAdapter.notifyDataSetChanged();
        }
        return rootView;
    }

    private void initViews(View rootView) {
        ingredientsView = rootView.findViewById(R.id.detail_steps_ingredients);
        stepsRecyclerView = rootView.findViewById(R.id.detail_steps_list);
    }

    @Override
    public void onClick(StepsItem step) {
        model.select(step);
    }
}
