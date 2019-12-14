package com.example.popularmoviesapp;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.NonNull;

import com.example.popularmoviesapp.Database.MyAppDatabase;

public class MovieDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    final private MyAppDatabase mDb;
    final private String mFavoriteId;

    public MovieDetailsViewModelFactory(MyAppDatabase database, String favoriteId) {
        mDb = database;
        mFavoriteId = favoriteId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(mDb, mFavoriteId);
    }
}
