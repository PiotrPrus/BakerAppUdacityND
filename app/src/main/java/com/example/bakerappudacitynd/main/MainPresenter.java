package com.example.bakerappudacitynd.main;

import android.util.Log;

import com.example.bakerappudacitynd.network.Recipe;
import com.example.bakerappudacitynd.network.RecipeService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter extends MvpBasePresenter<MainView> {

    private final static String TAG = MainPresenter.class.getSimpleName();

    private MainView view;
    private List<Recipe> data;

    public MainPresenter(MainView view) {
    this.view = view;
    }

    public void loadData() {
        view.onStartLoading();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RecipeService service = retrofit.create(RecipeService.class);
        Call<List<Recipe>> call = null;
        call = service.getBakeRecipesJson();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                data = response.body();
                Log.i(TAG, "Number of recipes fetched: " + data.size());
                view.onLoadCompleted(data);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.w(TAG, "Error trying parse Json" + t.getMessage());
                view.onLoadError();
            }
        });
    }

}
