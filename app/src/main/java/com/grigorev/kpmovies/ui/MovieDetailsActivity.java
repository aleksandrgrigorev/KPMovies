package com.grigorev.kpmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.grigorev.kpmovies.viewmodel.MovieDetailsViewModel;
import com.grigorev.kpmovies.R;
import com.grigorev.kpmovies.adapter.ReviewsAdapter;
import com.grigorev.kpmovies.adapter.TrailersAdapter;
import com.grigorev.kpmovies.dto.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "movie";
    private static final String TAG = "MovieDetailsActivity";

    private MovieDetailsViewModel viewModel;

    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;
    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private ImageView imageViewFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        initViews();
        trailersAdapter = new TrailersAdapter();
        reviewsAdapter = new ReviewsAdapter();
        recyclerViewTrailers.setAdapter(trailersAdapter);
        recyclerViewReviews.setAdapter(reviewsAdapter);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        if (movie.getPoster() != null) {
            Glide.with(this)
                    .load(movie.getPoster().getUrl())
                    .into(imageViewPoster);
        } else {
            Glide.with(this)
                    .load("https://st.kp.yandex.net/images/no-poster.gif")
                    .into(imageViewPoster);
        }

        textViewTitle.setText(movie.getName());
        textViewYear.setText(String.valueOf(movie.getYear()));
        textViewDescription.setText(movie.getDescription());

        viewModel.loadTrailers(movie.getId());
        viewModel.getTrailers().observe(
                this,
                trailers -> trailersAdapter.setTrailers(trailers)
        );
        trailersAdapter.setOnTrailerClickListener(trailer -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(trailer.getUrl()));
            startActivity(intent);
        });
        viewModel.getReviews().observe(
                this,
                reviews -> reviewsAdapter.setReviews(reviews)
        );
        viewModel.loadReviews(movie.getId());

        Drawable favouriteOff = ContextCompat.getDrawable(
                MovieDetailsActivity.this,
                android.R.drawable.star_big_off
        );
        Drawable favouriteOn = ContextCompat.getDrawable(
                MovieDetailsActivity.this,
                android.R.drawable.star_big_on
        );

        viewModel.getFavouriteMovie(movie.getId()).observe(
                this,
                movieFromDb -> {
                    if (movieFromDb == null) {
                        imageViewFavourite.setImageDrawable(favouriteOff);
                        imageViewFavourite.setOnClickListener(view -> viewModel.insertMovie(movie));
                    } else {
                        imageViewFavourite.setImageDrawable(favouriteOn);
                        imageViewFavourite.setOnClickListener(view -> viewModel.removeMovie(movie.getId()));
                    }
                });
    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        imageViewFavourite = findViewById(R.id.imageViewFavourite);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}