package com.example.popularmoviesapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.popularmoviesapp.Database.Favorite;
import com.example.popularmoviesapp.Database.MyAppDatabase;

public class MovieDetailsViewModel extends ViewModel {
    final private MyAppDatabase myAppDatabase;
    final private String mFavoriteId;
    private LiveData<Favorite> favorite;

    public MovieDetailsViewModel(MyAppDatabase database, String favoriteId) {
        myAppDatabase = database;
        mFavoriteId = favoriteId;
    }

    public LiveData<Favorite> getFavorite(){
        favorite = MainActivity.myAppDatabase.myDao().getFavorite(mFavoriteId);
        return favorite;
    }
}
