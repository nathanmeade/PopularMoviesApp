package com.example.popularmoviesapp;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.example.popularmoviesapp.Database.Favorite;

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

    LiveData<List<Favorite>> getFavorites(){

        return mFavoritesList;
    }
}
