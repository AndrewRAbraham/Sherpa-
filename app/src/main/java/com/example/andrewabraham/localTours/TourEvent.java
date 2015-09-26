package com.example.andrewabraham.localTours;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.UUID;

/**
 * Created by Andrew Abraham on 9/16/2015.
 */
public class TourEvent {
    private String mName;
    private ImageView mImage;
    private String mDescription;
    private long mStars;
    private UUID mUUID;
    private String locations;

    public TourEvent(){
        UUID uuid = UUID.randomUUID();
        setUUID(uuid);
    }
    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public void setImagePicasso(ImageView imageView, Context context){
        File file = new File("file:///android_asset/Image.jpg");
        Picasso.with(context).load(file).into(imageView);

    }
}
