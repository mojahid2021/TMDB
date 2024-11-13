package com.horizonhunters.tmdb.home.Trending.Today;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.horizonhunters.tmdb.Activity.MovieDetailsActivity.MovieDetailsActivity;
import com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.TvSeriesDetailsActivity;
import com.horizonhunters.tmdb.R;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    private List<Content> contentList;
    private Context context;

    public ContentAdapter(List<Content> contentList, Context context) {
        this.contentList = contentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContentAdapter.ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trending_today, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentAdapter.ContentViewHolder holder, int position) {
        Content content = contentList.get(position);
        // Set text fields
        holder.title.setText(content.getTitle());
        holder.releaseDate.setText(content.getReleaseDate());
        holder.voteAverage.setText(String.valueOf(content.getVoteAverage()));
        holder.mediaType.setText(content.getMediaType());

        // Load poster image
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + content.getPosterPath()) // Base URL for TMDB images
                // Placeholder image
                .into(holder.posterPath);

        // Optional: Load backdrop image if needed
        if (content.getBackdropPath() != null) {
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w1280" + content.getBackdropPath())

                    .into((ImageView) holder.backdropPath);  // Ensure backdropPath is an ImageView in the layout
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ContentAdapter2", "Clicked item ID: " + content.getId());

                if (content.getMediaType().equals("movie")) {
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("movieId", content.getId());
                    context.startActivity(intent);
                } else if (content.getMediaType().equals("tv")) {
                    Intent intent = new Intent(context, TvSeriesDetailsActivity.class);
                    intent.putExtra("movieId", content.getId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView title, releaseDate, voteAverage, mediaType;
        ImageView posterPath;
        ImageView backdropPath;  // Change to ImageView for backdrop image

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            voteAverage = itemView.findViewById(R.id.voteAverage);
            mediaType = itemView.findViewById(R.id.mediaType);
            posterPath = itemView.findViewById(R.id.poster);
            backdropPath = itemView.findViewById(R.id.backDrop);
        }
    }
}
