package com.example.bakerappudacitynd.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.network.StepsItem;

import java.util.List;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.ViewHolder> {
    private List<StepsItem> stepsList;
    private StepOnCLickListener stepOnCLickListener;

    public RecipeStepAdapter(List<StepsItem> stepsList, StepOnCLickListener stepOnCLickListener) {
        this.stepsList = stepsList;
        this.stepOnCLickListener = stepOnCLickListener;
    }

    public interface StepOnCLickListener {
        void onClick(StepsItem step);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
