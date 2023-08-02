package com.grigorev.kpmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grigorev.kpmovies.viewmodel.MainViewModel;
import com.grigorev.kpmovies.adapter.MoviesAdapter;
import com.grigorev.kpmovies.R;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private RecyclerView recyclerViewMovies;
    private ProgressBar progressBarLoading;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBarLoading = findViewById(R.id.progressBarLoading);
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        moviesAdapter = new MoviesAdapter();
        recyclerViewMovies.setAdapter(moviesAdapter);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 2));
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, movies -> moviesAdapter.setMovies(movies));
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                progressBarLoading.setVisibility(View.VISIBLE);
            } else {
                progressBarLoading.setVisibility(View.GONE);
            }
        });
        moviesAdapter.setOnReachEndListener(() -> viewModel.loadMovies());
        moviesAdapter.setOnItemClickListener(movie -> {
            Intent intent = MovieDetailsActivity.newIntent(MainActivity.this, movie);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemFavourite) {
            Intent intent = FavouriteMoviesActivity.newIntent(this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}