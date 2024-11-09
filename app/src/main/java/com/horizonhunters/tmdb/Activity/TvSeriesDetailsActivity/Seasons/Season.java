package com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.Seasons;

public class Season {
    private String id;
    private String name;
    private String episodeCount;
    private String airDate;
    private String overview;
    private String seasonNumber;
    private String posterPath;
    private double voteAverage;

    // Constructor
    public Season(String id, String name, String episodeCount, String airDate, String overview, String seasonNumber, String posterPath, double voteAverage) {
        this.id = id;
        this.name = name;
        this.episodeCount = episodeCount;
        this.airDate = airDate;
        this.overview = overview;
        this.seasonNumber = seasonNumber;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEpisodeCount() {
        return episodeCount;
    }

    public String getAirDate() {
        return airDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getSeasonNumber() {
        return seasonNumber;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}
