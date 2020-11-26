package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;


    public MyViewModelFactory(Application application){
        mApplication=application;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new LocationsViewModel(mApplication);
    }
}
