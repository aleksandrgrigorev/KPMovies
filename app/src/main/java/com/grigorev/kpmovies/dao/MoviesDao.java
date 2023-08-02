package com.grigorev.kpmovies.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.grigorev.kpmovies.dto.Movie;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM favourite_movies")
    LiveData<List<Movie>> getFavouriteMovies();

    @Query("SELECT * FROM favourite_movies WHERE id = :movieId")
    LiveData<Movie> getFavouriteMovie(int movieId);

    @Insert
    Completable insertMovie(Movie movie);

    @Query("DELETE FROM favourite_movies WHERE id = :movieId")
    Completable removeMovie(int movieId);
}
