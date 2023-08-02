package com.grigorev.kpmovies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailsViewModel extends AndroidViewModel {

    private static final String TAG = "MovieDetailsViewModel";

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    private final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();

    private final MoviesDao moviesDao;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        moviesDao = MoviesDatabase.getInstance(application).moviesDao();
    }

    public LiveData<Movie> getFavouriteMovie(int movieId) {
        return moviesDao.getFavouriteMovie(movieId);
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public void loadReviews(int id) {
        Disposable disposable = ApiFactory.apiService.loadReviews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ReviewsResponse::getReviews)
                .subscribe(
                        reviews::setValue,
                        throwable -> Log.d(TAG, throwable.toString())
                );
        compositeDisposable.add(disposable);
    }

    public void insertMovie(Movie movie) {
        Disposable disposable = moviesDao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> Log.d(TAG, "subscribe"),
                        throwable -> Log.d(TAG, "Error insertMovie")
                );
        compositeDisposable.add(disposable);
    }

    public void removeMovie(int movieId) {
        Disposable disposable = moviesDao.removeMovie(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> Log.d(TAG, "subscribe"),
                        throwable -> Log.d(TAG, "Error removeMovie")
                );
        compositeDisposable.add(disposable);
    }

    public void loadTrailers(int id) {
        Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(trailersResponse -> trailersResponse.getTrailersList().getTrailers())
                .subscribe(
                        trailers::setValue,
                        throwable -> Log.d(TAG, throwable.toString())
                );
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
