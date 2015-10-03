package com.example.andrewabraham.localTours;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Andrew Abraham on 10/3/2015.
 */
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "79Huv0P0NWQ0bUJh6sUP4adeWs2LjJzi5J2v1Y85", "5G5nKIvjbiW6en2spU1ptK3k6tqnZbU5fxv5RaQa");
        ParseObject.registerSubclass(TourEvent.class);
    }
}
