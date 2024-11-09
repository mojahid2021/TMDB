package com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.SeasonDetails.Episode;

public class Episode {
    private String airDate;
    private int episodeNumber;
    private String name;
    private String overview;
    private String stillPath;
    private double voteAverage;
    private int runtime;

    // Constructor
    public Episode(String airDate, int episodeNumber, String name, String overview, String stillPath, double voteAverage, int runtime) {
        this.airDate = airDate;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.overview = overview;
        this.stillPath = stillPath;
        this.voteAverage = voteAverage;
        this.runtime = runtime;
    }

    // Getters and Setters
    // (add standard getters and setters for each field)

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
}
