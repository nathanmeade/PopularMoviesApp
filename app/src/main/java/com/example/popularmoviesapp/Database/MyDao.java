package com.example.popularmoviesapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void addFavorite(Favorite favorite);


    @Query("select * from favorites")
    public LiveData<List<Favorite>> getFavorites();

    @Query("select * from favorites where movieId = :movieId")
    public LiveData<Favorite> getFavorite(String movieId);

/*    @Delete
    public void deleteFavorite(Favorite favorite);*/

    @Query("delete from favorites where movieId = :movieId")
    public void deleteFavorite(String movieId);
}
