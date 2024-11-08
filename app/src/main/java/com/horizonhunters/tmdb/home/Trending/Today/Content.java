package com.horizonhunters.tmdb.home.Trending.Today;

public class Content {
    private String title;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private double voteAverage;
    private int id;
    private String backdropPath;
    private String originalLanguage;
    private String originalTitle;
    private String mediaType;  // Media type should be a String
    private boolean adult;      // Change to boolean

    // Constructor
    public Content(String title, String overview, String posterPath, String releaseDate, double voteAverage, int id,
                   String backdropPath, String originalLanguage, String originalTitle, String mediaType, boolean adult) {
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.id = id;
        this.backdropPath = backdropPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.mediaType = mediaType;
        this.adult = adult;
    }
    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getMediaType() {  // Changed to return String
        return mediaType;
    }

    public void setMediaType(String mediaType) {  // Updated parameter type
        this.mediaType = mediaType;
    }

    public boolean isAdult() {  // Changed return type to boolean
        return adult;
    }

    public void setAdult(boolean adult) {  // Updated parameter type
        this.adult = adult;
    }
}
