package com.example.bakerappudacitynd.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.network.StepsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.ViewHolder> {
    private List<StepsItem> stepsList;
    private StepOnCLickListener stepOnCLickListener;

    public RecipeStepAdapter(StepOnCLickListener stepOnCLickListener) {
        this.stepOnCLickListener = stepOnCLickListener;
    }

    public interface StepOnCLickListener {
        void onClick(StepsItem step);
    }

    public void setData(List<StepsItem> data) {
        this.stepsList = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //TODO: RecyclerView is not display data
        final StepsItem step = stepsList.get(position);
        String stepShortDescription = step.getShortDescription();
        holder.shortDesc.setText(stepShortDescription);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepOnCLickListener.onClick(step);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_item_textView)
        TextView shortDesc;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
