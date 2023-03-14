package com.meadetechnologies.popularmoviesapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert
    suspend fun addMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun clearMovie()

    @Query("SELECT * FROM movie_table ORDER BY id ASC")
    fun getAllMovie(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table WHERE id = :id")
    fun getMovieById(id: Int): LiveData<Movie>

    // TODO: Add additional DAO methods here
}
