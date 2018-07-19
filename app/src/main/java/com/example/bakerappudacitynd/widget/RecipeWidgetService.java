package com.example.bakerappudacitynd.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.bakerappudacitynd.network.Recipe;

public class RecipeWidgetService extends IntentService {

    private static final String WIDGET_ACTION_UPDATE = "baking_app_action_update";
    private static final String BUNDLE_WIDGET_DATA = "baking_app_widget_data";


    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    public static void startActionUpdateRecipeWidgets(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(WIDGET_ACTION_UPDATE);
        intent.putExtra(BUNDLE_WIDGET_DATA, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            if (WIDGET_ACTION_UPDATE.equals(action) && intent.getParcelableExtra(BUNDLE_WIDGET_DATA) != null) {
                handleUpdate(intent.getParcelableExtra(BUNDLE_WIDGET_DATA));
            }
        }
    }

    private void handleUpdate(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidget.class));
        RecipeWidget.updateAppWidgets(this, appWidgetManager, recipe, appWidgetIds);
    }
}
