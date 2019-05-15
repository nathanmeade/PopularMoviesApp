package com.example.popularmoviesapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void addFavorite(Favorite favorite);


    @Query("select * from favorites")
    public List<Favorite> getFavorites();
}
