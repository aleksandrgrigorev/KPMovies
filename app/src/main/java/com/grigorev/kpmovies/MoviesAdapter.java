package com.grigorev.kpmovies;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private final String DUMMY_POSTER = "https://st.kp.yandex.net/images/no-poster.gif";

    private List<Movie> movies = new ArrayList<>();

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_item,
                parent,
                false
        );
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        if (movie.getPoster() != null) {
            Glide.with(holder.itemView)
                    .load(movie.getPoster().getUrl())
                    .into(holder.imageViewPoster);
        } else {
            Glide.with(holder.itemView)
                    .load(DUMMY_POSTER)
                    .into(holder.imageViewPoster);
        }

        double rating = movie.getRating().getKp();
        int backgroundId;
        if (rating > 7) {
            backgroundId = R.drawable.circle_green;
        } else if (rating > 5) {
            backgroundId = R.drawable.circle_orange;
        } else {
            backgroundId = R.drawable.circle_red;
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundId);
        holder.textViewRating.setBackground(background);

        holder.textViewRating.setText(String.format("%.1f", rating));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewPoster;
        private final TextView textViewRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }

    }
}