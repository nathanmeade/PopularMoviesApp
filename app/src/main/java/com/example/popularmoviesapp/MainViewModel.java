package com.example.popularmoviesapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.popularmoviesapp.Database.Favorite;
import com.example.popularmoviesapp.Database.MyAppDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    //private MyAppDatabase myAppDatabase;
    private LiveData<List<Favorite>> mFavoritesList;


    public MainViewModel(Application application) {
        super(application);
        //MyAppDatabase myAppDatabase = MainActivity.myAppDatabase;
        mFavoritesList = MainActivity.myAppDatabase.myDao().getFavorites();
        //mFavoritesList = myAppDatabase.myDao().getFavorites();
    }

    public LiveData<List<Favorite>> getFavorites(){

        return mFavoritesList;
    }
}
