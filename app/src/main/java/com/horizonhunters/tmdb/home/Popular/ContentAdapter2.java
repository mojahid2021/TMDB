package com.horizonhunters.tmdb.home.Popular;

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

public class ContentAdapter2 extends RecyclerView.Adapter<ContentAdapter2.ContentViewHolder> {
    private List<Content2> contentList;
    private Context context;

    public ContentAdapter2(List<Content2> contentList, Context context) {
        this.contentList = contentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        Content2 content2 = contentList.get(position);

        // Set text fields
//        holder.title.setText(content2.getTitle());
//        holder.releaseDate.setText(content2.getReleaseDate());
//        holder.voteAverage.setText(String.valueOf(content2.getVoteAverage()));
//        holder.mediaType.setText(content2.getMediaType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ContentAdapter2", "Clicked item ID: " + content2.getId());

                if (content2.getMediaType().equals("movie")) {
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("movieId", content2.getId());
                    context.startActivity(intent);
                } else if (content2.getMediaType().equals("tv")) {
                    Intent intent = new Intent(context, TvSeriesDetailsActivity.class);
                    intent.putExtra("movieId", content2.getId());
                    context.startActivity(intent);
                }
            }
        });

        // Load poster image
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + content2.getPosterPath())
                .into(holder.posterPath);

//        // Optional: Load backdrop image if needed
//        if (content2.getBackdropPath() != null) {
//            Glide.with(context)
//                    .load("https://image.tmdb.org/t/p/w1280" + content2.getBackdropPath())
//                    .into(holder.backdropPath);
//        }
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView title, releaseDate, voteAverage, mediaType;
        ImageView posterPath;
        ImageView backdropPath;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
//            title = itemView.findViewById(R.id.title);
//            releaseDate = itemView.findViewById(R.id.releaseDate);
//            voteAverage = itemView.findViewById(R.id.voteAverage);
//            mediaType = itemView.findViewById(R.id.mediaType);
              posterPath = itemView.findViewById(R.id.poster);
//            backdropPath = itemView.findViewById(R.id.backDrop);
        }
    }
}
