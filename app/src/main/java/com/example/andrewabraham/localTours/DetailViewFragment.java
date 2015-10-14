package com.example.andrewabraham.localTours;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Andrew Abraham on 10/3/2015.
 */
public class DetailViewFragment extends Fragment implements View.OnClickListener{
private TourEvent mTourEvent;
    private String mTourTitle;
    private String mTourDescription;
    private String mTourInfo;
    private String mTourLocations;
    private List mTourNames;
    private List mTourStops;
    private Button mNavButton;
    public static final String TAG_NAV = "navigation";

    private ImageView mTourImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState = getArguments();
        mTourEvent = (TourEvent) savedInstanceState.getSerializable(TourList.tourKey);
        if (mTourEvent != null) {
            mTourTitle = mTourEvent.getTitle();
            mTourDescription = mTourEvent.getDescription();
            mTourLocations = mTourEvent.getLocations();
            mTourStops = mTourEvent.getStops();
            mTourNames = mTourEvent.getNames();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tour_detail_view,container,false);

        ImageView mTourImage = (ImageView) v.findViewById(R.id.detail_imageView);

        Picasso.with(getContext()).load(mTourEvent.getImageLocation()).fit().into(mTourImage);

        TextView detailTitleTextView =(TextView) v.findViewById(R.id.detail_title);
        detailTitleTextView.setText(mTourTitle);

        mNavButton = (Button) v.findViewById(R.id.nav_button);
        mNavButton.setTag(TAG_NAV);
        mNavButton.setOnClickListener(this);

        ScrollView detailScrollView = (ScrollView) v.findViewById(R.id.detailScrollView);

        TextView descriptionTextView = (TextView) v.findViewById(R.id.detail_description);



        TextView detailInfoTextView = (TextView) v.findViewById(R.id.detail_info_textView);
        detailInfoTextView.setText(mTourDescription);

        TextView detailRouteTextView = (TextView) v.findViewById(R.id.detail_route);
        LinearLayout scrollLayout = (LinearLayout)v.findViewById(R.id.scroll_layout);



        if (mTourNames.size() == mTourStops.size()) {
            for (int i = 0; i < mTourNames.size(); ++i) {
                TextView nameView = new TextView(getContext());
                nameView.setText((String) mTourNames.get(i));
                nameView.setPadding(setPToDP(8), setPToDP(0), setPToDP(8), setPToDP(0));
                scrollLayout.addView(nameView);
                TextView stopView = new TextView(getContext());
                stopView.setText((String)mTourStops.get(i));
                stopView.setPadding(setPToDP(8),setPToDP(0),setPToDP(8),setPToDP(0));
                scrollLayout.addView(stopView);
            }
        }


        return v;

    }
private int setPToDP(int i){
    Resources r = getContext().getResources();
    int px = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            i,
            r.getDisplayMetrics()
    );
    return px;

}


    @Override
    public void onClick(View v) {
        if (v.getTag() == TAG_NAV){
            Intent intent = new Intent(getActivity(),TourActivity.class);
            intent.putExtra(TAG_NAV, mTourEvent);
            startActivity(intent);

        }
    }
}
