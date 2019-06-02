package com.example.popularmoviesapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.popularmoviesapp.Database.Favorite;

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
