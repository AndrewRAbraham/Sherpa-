package com.example.andrewabraham.localTours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Andrew Abraham on 10/12/2015.
 */
public class TourActivity extends AppCompatActivity {
    public static final String EVENT = "event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        TourEvent event = (TourEvent) intent.getSerializableExtra(DetailViewFragment.TAG_NAV);
        setContentView(R.layout.gps_holder);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment gpsFragment = fragmentManager.findFragmentById(R.id.gps_holder);
        Fragment gpsInfoFragment = fragmentManager.findFragmentById(R.id.gps_info_holder);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EVENT,event);
        if (gpsFragment == null || gpsInfoFragment == null ) {
            gpsFragment = new GpsFragment();
            gpsFragment.setArguments(bundle);
            gpsInfoFragment = new InformationFragment();
            gpsInfoFragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.gps_holder, gpsFragment).add(R.id.gps_info_holder,gpsInfoFragment).commit();
        }

    }

}
