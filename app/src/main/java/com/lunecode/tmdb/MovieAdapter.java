package com.lunecode.tmdb;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private Context context;

    public MovieAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.mTitle.setText(movie.getTitle());
        holder.mReleaseRate.setText(movie.getReleaseDaete());

        Glide.with(context)
                .load(movie.getPosterPath())
                .into(holder.mPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movieId", movie.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
         ImageView mPoster;
         TextView mTitle, mReleaseRate;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mPoster = itemView.findViewById(R.id.mvPoster);
            mTitle = itemView.findViewById(R.id.mvTitle);
            mReleaseRate = itemView.findViewById(R.id.mvReleaseDate);
        }
    }
}
