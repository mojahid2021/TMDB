package com.horizonhunters.tmdb.Activity.MovieDetailsActivity.SimilarMovies;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.horizonhunters.tmdb.Activity.MovieDetailsActivity.MovieDetailsActivity;
import com.horizonhunters.tmdb.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_poster, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        if (movie.getPosterPath() != null) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(holder.poster);
        } else {
            // Set a placeholder image if posterPath is null
            holder.poster.setImageResource(R.drawable.error_poster);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieId = movie.getId();
                Log.d("ContentAdapter3", "Movie ID: " + movieId); // Log the ID
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movieId", movieId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
        }
    }
}
