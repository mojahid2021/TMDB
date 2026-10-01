package com.lunecode.tmdb;

public class Casting {
    private String id;
    private String name;
    private String profilePath;
    private String characterName;

    public Casting(String id, String name, String profilePath, String characterName) {
        this.id = id;
        this.name = name;
        this.profilePath = profilePath;
        this.characterName = characterName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
