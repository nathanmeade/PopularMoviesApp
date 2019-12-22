package com.example.popularmoviesapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmoviesapp.Database.Favorite;
import com.example.popularmoviesapp.Database.Movie;

public class MovieDetailsViewModel extends ViewModel {
    final private int mFavoriteId;

    MovieDetailsViewModel(int favoriteId) {
        mFavoriteId = favoriteId;
    }

    public LiveData<Favorite> getFavorite(){
        return MainActivity.myAppDatabase.myDao().getFavorite(mFavoriteId);
    }
}
