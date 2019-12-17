package com.example.popularmoviesapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    void addFavorite(Favorite favorite);


    @Query("select * from favorites")
    LiveData<List<Favorite>> getFavorites();

    @Query("select * from favorites where movieId = :movieId")
    LiveData<Favorite> getFavorite(String movieId);

    @Query("delete from favorites where movieId = :movieId")
    void deleteFavorite(String movieId);
}
