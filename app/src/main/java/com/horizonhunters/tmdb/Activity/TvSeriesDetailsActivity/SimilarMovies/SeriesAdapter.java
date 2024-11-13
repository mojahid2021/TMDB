package com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.SimilarMovies;

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

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.MovieViewHolder> {
    private List<Series> movieList;
    private Context context;

    public SeriesAdapter(List<Series> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_poster, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Series movie = movieList.get(position);
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())// Replace with an actual error drawable
                .into(holder.poster);
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
