package com.example.andrewabraham.localTours;

import android.widget.ImageView;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Andrew Abraham on 9/16/2015.
 */
@ParseClassName("TourEvent")
public class TourEvent extends ParseObject {
    private String mTitle;
    private ImageView mImage;
    private String mDescription;
    private long mStars;
    private String mLocations;
    private String mAuthor;
    private String mId;
    private long mRating;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public ImageView getImage() {
        return mImage;
    }

    public void setImage(ImageView image) {
        mImage = image;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getStars() {
        return mStars;
    }

    public void setStars(long stars) {
        mStars = stars;
    }

    public String getLocations() {
        return mLocations;
    }

    public void setLocations(String locations) {
        mLocations = locations;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public long getRating() {
        return mRating;
    }

    public void setRating(long rating) {
        mRating = rating;
    }



}