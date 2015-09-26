package com.example.andrewabraham.localTours;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Abraham on 9/16/2015.
 */
public class TourController {
    private ArrayList<TourEvent> mTourList = new ArrayList<>();
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

    public void setTourList(ArrayList tourList) {
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

    public String buildURI(){

        String URLStart = "https://"+ APP_ID +":javascript-key=" + REST_API_KEY+"@api.parse.com/1/";
        String URL = Uri.parse(URLStart)
                .buildUpon()
                .appendEncodedPath("className")
                .appendQueryParameter("TourEvent","TourEvent")
                .build().toString();
        return URL;
    }

    public Integer getDataFromDataBase(String urlMade) {
        Integer result = 0;
        HttpURLConnection urlConnection;
        try {
            URL url = new URL(urlMade);
            urlConnection = (HttpURLConnection) url.openConnection();
            int statusCode = urlConnection.getResponseCode();

            if(statusCode == HttpURLConnection.HTTP_OK){
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null){
                    response.append(line);
                }
                parseResult(response.toString());
                result = 1;
            }else{
                result = 0;
            }
        }catch (Exception e){
            Log.d(LOG_TAG, e.getLocalizedMessage());
        }
        return result;
    }

    private void parseResult(String JSONData) throws IOException {
        try {
            JSONObject response = new JSONObject(JSONData);
            JSONArray tours = response.getJSONArray("TourEvent");
            for (int i = 0; i < tours.length(); i++) {
                JSONObject eventObject = tours.getJSONObject(i);
                TourEvent tourEvent = new TourEvent();
                tourEvent.setName(eventObject.getString("title"));
                tourEvent.setDescription(eventObject.getString("description"));
                tourEvent.setLocations(eventObject.getString("locations"));
                tourEvent.setStars(eventObject.getLong("rating"));
                mTourList.add(tourEvent);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "did not parse right");
        }
    }

}
