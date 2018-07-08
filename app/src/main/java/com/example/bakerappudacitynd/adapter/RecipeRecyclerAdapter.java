package com.example.bakerappudacitynd.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.network.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.ViewHolder> {
    private List<Recipe> recipeList;
    private RecipeOnClickListener onClickListener;

    public RecipeRecyclerAdapter(RecipeOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setData(List<Recipe> data) {
        this.recipeList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Recipe recipe = recipeList.get(position);
        String name = recipe.getName();
        String imagePath = recipe.getImage();
        holder.recipeName.setText(name);

        if (TextUtils.isEmpty(imagePath)) {
            Picasso.get()
                    .load(R.drawable.cake_placeholder)
                    .into(holder.recipeImage);
        } else {
            Picasso.get()
                    .load(imagePath)
                    .centerCrop()
                    .placeholder(R.drawable.cake_placeholder)
                    .error(R.drawable.cake_placeholder)
                    .into(holder.recipeImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name)
        TextView recipeName;
        @BindView(R.id.recipe_image)
        ImageView recipeImage;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface RecipeOnClickListener {
        void onClick(Recipe recipe);
    }

}
