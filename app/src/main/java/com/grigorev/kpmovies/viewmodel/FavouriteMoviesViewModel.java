package com.grigorev.kpmovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.grigorev.kpmovies.dao.MoviesDao;
import com.grigorev.kpmovies.db.MoviesDatabase;
import com.grigorev.kpmovies.dto.Movie;

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
