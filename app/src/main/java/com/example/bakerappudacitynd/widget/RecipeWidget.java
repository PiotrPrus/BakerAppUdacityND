package com.example.bakerappudacitynd.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.bakerappudacitynd.R;
import com.example.bakerappudacitynd.detail.DetailActivity;
import com.example.bakerappudacitynd.network.IngredientsItem;
import com.example.bakerappudacitynd.network.Recipe;

public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, Recipe recipe, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        Recipe recipe, int appWidgetId) {
        Intent intent = DetailActivity.getIntent(context, recipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baker_widget);
        views.removeAllViews(R.id.widget_ingredients_list_layout);
        views.setTextViewText(R.id.widget_recipe_name, recipe.getName());
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

        for (IngredientsItem ingredient : recipe.getIngredients()) {
            RemoteViews rvIngredient = new RemoteViews(context.getPackageName(),
                    R.layout.widget_list_item);
            rvIngredient.setTextViewText(R.id.widget_ingredient_item,
                    String.valueOf(ingredient.getQuantity()) +
                            String.valueOf(ingredient.getMeasure()) + " " + ingredient.getIngredient());
            views.addView(R.id.widget_ingredients_list_layout, rvIngredient);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }
}
