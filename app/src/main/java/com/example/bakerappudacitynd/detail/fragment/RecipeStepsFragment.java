package com.example.bakerappudacitynd.detail.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.network.IngredientsItem;
import com.example.bakerappudacitynd.network.StepsItem;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepsFragment extends Fragment {

    private List<StepsItem> stepsList;
    private RecyclerView stepsRecyclerView;
    private TextView ingredientsView;
    private List<IngredientsItem> ingredientsList;

    public RecipeStepsFragment() {
        // Required empty public constructor
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

        }


        return rootView;
    }

    private void initViews(View rootView) {

    }

    protected void setStepsList(List<StepsItem> stepsList){
        this.stepsList = stepsList;
    }

    protected void setIngredientsList(List<IngredientsItem> ingredientsList){
        this.ingredientsList = ingredientsList;
    }

}
