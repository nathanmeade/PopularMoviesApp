package com.example.popularmoviesapp.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Favorite.class},version = 1)
public abstract class MyAppDatabase extends RoomDatabase {
    public abstract MyDao myDao();
}
