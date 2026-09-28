package com.lunecode.tmdb;

public class Movie {
    private String id;
    private String title;
    private String posterPath;
    private String releaseDaete;

    public Movie(String id, String title, String posterPath, String releaseDaete) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDaete = releaseDaete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDaete() {
        return releaseDaete;
    }

    public void setReleaseDaete(String releaseDaete) {
        this.releaseDaete = releaseDaete;
    }
}
