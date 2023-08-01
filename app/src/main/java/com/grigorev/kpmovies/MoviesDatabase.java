package com.grigorev.kpmovies;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    private static final String DB_NAME = "movies.db";
    private static MoviesDatabase instance = null;

    public static MoviesDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    MoviesDatabase.class,
                    DB_NAME
                    ).build();
        }
        return instance;
    }

    abstract MoviesDao moviesDao();
}
