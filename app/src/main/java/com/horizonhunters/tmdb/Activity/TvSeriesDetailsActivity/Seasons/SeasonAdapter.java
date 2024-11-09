package com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.Seasons;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.SeasonDetails.SeasonDetailsActivity;
import com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.SimilarMovies.Series;
import com.horizonhunters.tmdb.MainActivity;
import com.horizonhunters.tmdb.R;

import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder> {

    private Context context;
    private List<Season> seasons;

    // Constructor
    public SeasonAdapter(Context context, List<Season> seasons) {
        this.context = context;
        this.seasons = seasons;
    }

    @NonNull
    @Override
    public SeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_season, parent, false);
        return new SeasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonViewHolder holder, int position) {
        Season season = seasons.get(position);

        // Set data to views
        holder.seasonName.setText(season.getName());
        holder.seasonOverview.setText(season.getOverview());
        holder.seasonEpisodeCount.setText("Episodes: " + season.getEpisodeCount());
        holder.seasonAirDate.setText("Aired on: " + season.getAirDate());
        holder.seasonVoteAverage.setText("Rating: " + season.getVoteAverage());

        // Set the poster image (for this, use an image loading library like Picasso or Glide)
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + season.getPosterPath())// Replace with an actual error drawable
                .into(holder.seasonPoster);
        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            // Pass season ID as a string to the next activity
            Intent intent = new Intent(context, SeasonDetailsActivity.class);
            intent.putExtra("season_id", season.getSeasonNumber()); // Pass the season ID

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return seasons.size();
    }

    public static class SeasonViewHolder extends RecyclerView.ViewHolder {
        TextView seasonName, seasonOverview, seasonEpisodeCount, seasonAirDate, seasonVoteAverage;
        ImageView seasonPoster;

        public SeasonViewHolder(@NonNull View itemView) {
            super(itemView);
            seasonName = itemView.findViewById(R.id.season_name);
            seasonOverview = itemView.findViewById(R.id.season_overview);
            seasonEpisodeCount = itemView.findViewById(R.id.season_episode_count);
            seasonAirDate = itemView.findViewById(R.id.season_air_date);
            seasonVoteAverage = itemView.findViewById(R.id.season_vote_average);
            seasonPoster = itemView.findViewById(R.id.season_poster);
        }
    }
}
