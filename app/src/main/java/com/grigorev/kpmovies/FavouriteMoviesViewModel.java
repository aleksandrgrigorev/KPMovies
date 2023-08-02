package com.grigorev.kpmovies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouriteMoviesViewModel extends AndroidViewModel {

    private final MoviesDao moviesDao;

    public FavouriteMoviesViewModel(@NonNull Application application) {
        super(application);
        moviesDao = MoviesDatabase.getInstance(application).moviesDao();
    }

    public LiveData<List<Movie>> getMovies() {
        return moviesDao.getFavouriteMovies();
    }
}
