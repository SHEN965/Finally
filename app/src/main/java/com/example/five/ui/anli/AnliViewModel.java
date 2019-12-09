package com.example.five.ui.anli;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnliViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AnliViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is anli fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}