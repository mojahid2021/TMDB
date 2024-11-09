package com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.Credits;

public class Credit {
    private String name;
    private String knownForDepartment;
    private String profilePath;
    private String releaseDate;
    private String id;
    private String character;
    private String creditId;
    private String castId;
    private String originalName;
    private String gender;
    private boolean adult;      // Change to boolean

    // Constructor
    public Credit(String name, String knownForDepartment, String profilePath, String releaseDate, String id, String character, String creditId, String castId, String originalName, boolean adult) {
        this.name = name;
        this.knownForDepartment = knownForDepartment;
        this.profilePath = profilePath;
        this.releaseDate = releaseDate;
        this.id = id;
        this.character = character;
        this.creditId = creditId;
        this.castId = castId;
        this.originalName = originalName;
        this.gender = gender;
        this.adult = adult;
    }

    //Getter and Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getCastId() {
        return castId;
    }

    public void setCastId(String castId) {
        this.castId = castId;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }
}
