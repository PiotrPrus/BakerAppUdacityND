package com.example.bakerappudacitynd.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.bakerappudacitynd.network.StepsItem;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<StepsItem> selected = new MutableLiveData<>();

    public void select(StepsItem item) {
        selected.setValue(item);
    }

    public LiveData<StepsItem> getSelected() {
        return selected;
    }
}
