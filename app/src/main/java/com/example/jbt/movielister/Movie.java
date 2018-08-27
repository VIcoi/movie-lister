package com.example.jbt.movielister;

import java.io.Serializable;

public class Movie implements Serializable {
    ///movie vars
    private long id ;
    private String title,overview,imageview ,date;
    private float reting;
    private boolean check;

    //movie constractor 1
    public Movie(long id, String title, String overview, String imageview, String date, float reting, boolean check) { //constractor with id for insert movie
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.imageview = imageview;
        this.date = date;
        this.reting = reting;
        this.check = check;
    }

    //movie constractor 2 (no id)- only used for items allready on the main list
    public Movie(String title, String overview, String imageview, String date, float reting, boolean check) { //constractor with id for movies thet allready exists
        this.title = title;
        this.overview = overview;
        this.imageview = imageview;
        this.date = date;
        this.reting = reting;
        this.check = check;
    }

    // setters nad getters
    public float getReting() {
        return reting;
    }

    public void setReting(float reting) {
        this.reting = reting;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getImageview() {
        return imageview;
    }

    public void setImageview(String imageview) {
        this.imageview = imageview;
    }


    //for the web list
    @Override
    public String toString() { // how every movie found on serch will be dispalyed in serch list
        return  "Movie: " + title + "\nDate : " + date ;
    }
}
