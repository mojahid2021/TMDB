package com.horizonhunters.tmdb.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.horizonhunters.tmdb.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final List<Movie> movieList;
    private final Context context;

    public MovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }


    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_search, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
    //get the current movie and bind data to the view
        Movie movie = movieList.get(position);
        holder.originalLanguage.setText(movie.getOriginalLanguage());
        holder.originaltitle.setText(movie.getOriginalTitle());
        holder.overview.setText(movie.getOverview());
        holder.releaseDate.setText(movie.getReleaseDate());
        holder.voteAverage.setText(String.valueOf(movie.getVoteAverage()));

        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView originalLanguage, originaltitle, overview, releaseDate, voteAverage;
        ImageView poster, backdrop;
        MovieViewHolder(View itemView) {
            super(itemView);

            originalLanguage = itemView.findViewById(R.id.original_language);
            originaltitle = itemView.findViewById(R.id.title);
            overview = itemView.findViewById(R.id.overview);
            releaseDate = itemView.findViewById(R.id.release_date);
            voteAverage = itemView.findViewById(R.id.vote_average);
            poster = itemView.findViewById(R.id.poster);
//            backdrop = itemView.findViewById(R.id.backdrop);


        }
    }
}
