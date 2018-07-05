package com.gglabs.materna.Model;

/**
 * Created by GG on 18/02/2018.
 */

public class Story {

    private String id;
    private String title;
    private String shortStory;
    private String longStory;

    public Story(String title, String shortStory, String longStory) {
        this.title = title;
        this.shortStory = shortStory;
        this.longStory = longStory;
    }

    @Override
    public String toString() {
        return id;
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

    public String getShortStory() {
        return shortStory;
    }

    public void setShortStory(String shortStory) {
        this.shortStory = shortStory;
    }

    public String getLongStory() {
        return longStory;
    }

    public void setLongStory(String longStory) {
        this.longStory = longStory;
    }
}
