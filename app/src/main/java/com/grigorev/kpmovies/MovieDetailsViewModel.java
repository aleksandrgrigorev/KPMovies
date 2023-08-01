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

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public void loadTrailers(int id) {
        Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(trailersResponse -> trailersResponse.getTrailersList().getTrailers())
                .subscribe(
                        trailersList -> trailers.setValue(trailersList),
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
