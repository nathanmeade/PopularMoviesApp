package com.example.popularmoviesapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

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
