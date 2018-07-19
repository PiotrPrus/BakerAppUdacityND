package com.example.bakerappudacitynd.step;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.bakerappudacitynd.network.StepsItem;

public class StepViewModel extends ViewModel {
    private final MutableLiveData<StepsItem> previousSelected = new MutableLiveData<>();
    private final MutableLiveData<StepsItem> nextSelected = new MutableLiveData<>();

    public void selectPrevious(StepsItem item) {
        previousSelected.setValue(item);
    }

    public LiveData<StepsItem> getPreviousSelected() {
        return previousSelected;
    }

    public void selectNext(StepsItem item) {
        nextSelected.setValue(item);
    }

    public LiveData<StepsItem> getNextSelected() {
        return nextSelected;
    }
}
