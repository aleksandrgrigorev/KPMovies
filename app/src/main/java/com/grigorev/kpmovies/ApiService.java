package com.grigorev.kpmovies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=0T18DY9-PQB4FHS-MAFPVZY-0KQ2T19&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=40")
    Single<MovieResponse> loadMovies(@Query("page") int page);
}
