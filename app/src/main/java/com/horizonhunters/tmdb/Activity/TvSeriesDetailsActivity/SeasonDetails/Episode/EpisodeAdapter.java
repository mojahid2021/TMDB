package com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.SeasonDetails.Episode;

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

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {
    private List<Episode> episodeList;

    public EpisodeAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode, parent, false);
        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode episode = episodeList.get(position);
        holder.tvEpisodeAirDate.setText("Air Date: " + episode.getAirDate());
        holder.tvEpisodeVoteAverage.setText("Rating: " + episode.getVoteAverage());

        if (episode.getName() !=null ){
            holder.tvEpisodeName.setText(episode.getName());
        }else {
            holder.tvEpisodeName.setText("No Name Found");
        }

        if (episode.getOverview() != null){
            holder.tvEpisodeOverview.setText(episode.getOverview());
        }else {
            holder.tvEpisodeOverview.setText("No Overview Found");
        }

        // Load image using Glide or any other image loading library
        if (episode.getStillPath() != null) {
            Glide.with(holder.ivEpisodeThumbnail.getContext())
                    .load("https://image.tmdb.org/t/p/w1280" + episode.getStillPath())
                    .into(holder.ivEpisodeThumbnail);
        } else {
            // Set a placeholder image if stillPath is null or empty
            Glide.with(holder.ivEpisodeThumbnail.getContext())
                    .load(R.drawable.backdrop_error)
                    .into(holder.ivEpisodeThumbnail);
        }

    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public static class EpisodeViewHolder extends RecyclerView.ViewHolder {
        ImageView ivEpisodeThumbnail;
        TextView tvEpisodeName, tvEpisodeOverview, tvEpisodeAirDate, tvEpisodeVoteAverage;

        public EpisodeViewHolder(View itemView) {
            super(itemView);
            ivEpisodeThumbnail = itemView.findViewById(R.id.ivEpisodeThumbnail);
            tvEpisodeName = itemView.findViewById(R.id.tvEpisodeName);
            tvEpisodeOverview = itemView.findViewById(R.id.tvEpisodeOverview);
            tvEpisodeAirDate = itemView.findViewById(R.id.tvEpisodeAirDate);
            tvEpisodeVoteAverage = itemView.findViewById(R.id.tvEpisodeVoteAverage);
        }
    }
}
