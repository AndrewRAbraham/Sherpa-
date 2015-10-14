package com.example.andrewabraham.localTours;

import android.widget.ImageView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Abraham on 9/16/2015.
 */
public class TourController {
    private List<TourEvent> mTourList = new ArrayList<>();
    public ImageView mImageView;
    public static TourController controller;
    private final String LOG_TAG = "download not sucessful";
    private final String REST_API_KEY = "Cf2ngrS0DClrj1W3U6CUwjc7OR1Yj1snkvowKOxk";
    private final String APP_ID = "79Huv0P0NWQ0bUJh6sUP4adeWs2LjJzi5J2v1Y85";
    //structure https://myAppID:javascript-key=myJavaScriptKey@api.parse.com/1/classes/GameScore/Ed1nuqPvcm



    private TourController() {
    }

    public static TourController getTourController() {
        if (controller == null) {
            controller = new TourController();
        }
        return controller;
    }


    public List getTourList() {
        return mTourList;
    }

    public void setTourList(List tourList) {
        mTourList = tourList;
    }


    public List tourListGenerator() {

        for (int i = 0; i < 20; i++) {

            ParseObject tourEvent = new ParseObject("TourEvent");
            tourEvent.put("title", "title" + i);
            tourEvent.put("description", "description" + i);
            tourEvent.put("author", "author" + i);
            tourEvent.put("locations", "locations" + i);
            tourEvent.put("rating", 4);
            tourEvent.saveInBackground();
        }
        return mTourList;
    }

}




