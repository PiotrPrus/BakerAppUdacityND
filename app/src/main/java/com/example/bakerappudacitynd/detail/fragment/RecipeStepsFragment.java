package com.example.bakerappudacitynd.detail.fragment;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.bakerappudacitynd.network.StepsItem;
import com.example.bakerappudacitynd.step.RecipeStepActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepsFragment extends Fragment implements RecipeStepAdapter.StepOnCLickListener{

    private SharedViewModel model;

    private List<StepsItem> stepsList;
    private RecyclerView stepsRecyclerView;
    private TextView ingredientsView;
    private List<IngredientsItem> ingredientsList;

    public RecipeStepsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        initViews(rootView);

        if (ingredientsList != null) {
            StringBuilder builder = new StringBuilder();
            for (IngredientsItem item : ingredientsList){
                builder.append(item.getIngredient());
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
        ingredientsView = (TextView) rootView.findViewById(R.id.detail_steps_ingredients);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.detail_steps_list);
    }

    public void setStepsList(List<StepsItem> stepsList){
        this.stepsList = stepsList;
    }

    public void setIngredientsList(List<IngredientsItem> ingredientsList){
        this.ingredientsList = ingredientsList;
    }

    @Override
    public void onClick(StepsItem step) {
        model.select(step);
    }
}
