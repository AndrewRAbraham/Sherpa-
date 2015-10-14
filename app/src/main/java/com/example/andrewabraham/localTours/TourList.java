package com.example.andrewabraham.localTours;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Andrew Abraham on 9/16/2015.
 */
public class TourList extends Fragment {
    private RecyclerView.Adapter mAdapter;
    private final String LOG_TAG = "download not sucessful";
    private List mTourList = new ArrayList();
    private RecyclerView mRecyclerView;
    private TourController mTourController = TourController.getTourController();
    public static final String tourKey = "event";

     public static TourList newInstance() {

        Bundle args = new Bundle();

        TourList fragment = new TourList();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DownloadTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.recyclerview_tours, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.tour_fragment_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new TourAdapter(mTourList);
        mRecyclerView.setAdapter(mAdapter);
        return view;

    }







    public class TourViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        CardView mCardView;
        TextView mTitleTextView;
        ImageView mImageView;
        TextView mDescriptionTextView;

        public TourViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mCardView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tour_title_text);
            mImageView = (ImageView) itemView.findViewById(R.id.tour_image);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.tour_description);
        }

        @Override
        public void onClick(View v) {
            List list = mTourController.getTourList();
            int i = this.getAdapterPosition();
            TourEvent event = (TourEvent)list.get(i);

            DetailViewFragment fragment = new DetailViewFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(tourKey, event);
            fragment.setArguments(bundle);
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.fragment_holder, fragment).addToBackStack("fragment 1")
                    .commit();

        }
    }




    public class TourAdapter extends RecyclerView.Adapter<TourViewHolder>
    {

        private List mTourListData = new ArrayList();

        public TourAdapter(List list) {
            mTourListData = list;


        }


        @Override
        public TourViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View tourView = inflater.inflate(R.layout.tour, viewGroup, false);
            TourViewHolder tvh = new TourViewHolder(tourView);
            return tvh;

        }

        @Override
        public void onBindViewHolder(TourViewHolder tourViewHolder, int i) {
            TourEvent tourEvent = (TourEvent) mTourListData.get(i);
            tourViewHolder.mTitleTextView.setText(tourEvent.getTitle());
            tourViewHolder.mDescriptionTextView.setText(tourEvent.getDescription());
            if (tourEvent.getImageLocation() != null) {
                Picasso.with(getContext()).load(tourEvent.getImageLocation()).fit().into(tourViewHolder.mImageView);
            }else{
                Picasso.with(getContext()).cancelRequest(tourViewHolder.mImageView);
            }

        }

        @Override
        public int getItemCount() {
            return mTourListData.size();
        }

        @Override
        public void onViewRecycled(TourViewHolder holder) {
            super.onViewRecycled(holder);
            holder.mImageView.setImageBitmap(null);
        }
    }


    private class DownloadTask extends AsyncTask<Void, Void, List> {


        @Override
        protected List doInBackground(Void... params) {
            ParseQuery<TourEvent> query = new ParseQuery<TourEvent>("TourEvent");
            List<TourEvent> list = new ArrayList<>();
            try {
                list = query.find();
                if (list != null) {
                    for (TourEvent tour : list) {
                        TourEvent tourEvent = new TourEvent();
                        tourEvent.setTitle(tour.getString("title"));
                        tourEvent.setDescription(tour.getString("description"));
                        tourEvent.setLocations(tour.getString("locations"));
                        tourEvent.setNames(tour.getList("names"));
                        tourEvent.setStops(tour.getList("stops"));
                        tourEvent.setRating(tour.getLong("rating"));
                        tourEvent.setAuthor(tour.getString("author"));
                        tourEvent.setId(tour.getString("objectId"));
                        if (tour.get("image") != null) {
                            tourEvent.setImageLocation(tour.getParseFile("image").getUrl());
                        }
                        mTourList.add(tourEvent);
                    }
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }

                    return mTourList;
            }



        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            updateUI(list);
        }
    }

    private void updateUI(List list) {
        mAdapter.notifyDataSetChanged();
        mTourController.setTourList(list);
    }

}
