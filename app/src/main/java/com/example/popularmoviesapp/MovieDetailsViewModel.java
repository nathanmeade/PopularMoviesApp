package com.example.popularmoviesapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmoviesapp.Database.Favorite;

public class MovieDetailsViewModel extends ViewModel {
    final private String mFavoriteId;

    MovieDetailsViewModel(String favoriteId) {
        mFavoriteId = favoriteId;
    }

    public LiveData<Favorite> getFavorite(){
        return MainActivity.myAppDatabase.myDao().getFavorite(mFavoriteId);
    }
}
