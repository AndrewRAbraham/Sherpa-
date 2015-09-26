package com.example.andrewabraham.localTours;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

     public static TourList newInstance() {

        Bundle args = new Bundle();

        TourList fragment = new TourList();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.recyclerview_tours, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.tour_fragment_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        new AsyncHttpTask().execute();


        return view;

    }




public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
    private Integer result =0;

    @Override
    protected Integer doInBackground(String... params) {
        TourController controller = TourController.getTourController();
        String url = controller.buildURI();
        result = controller.getDataFromDataBase(url);
        mTourList = controller.getTourList();
        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result == 1) {
            mAdapter = new TourAdapter(mTourList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
        }
    }
}




    public static class TourViewHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        TextView mTitleTextView;
        ImageView mImageView;
        TextView mDescriptionTextView;

        public TourViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tour_title_text);
            mImageView = (ImageView) itemView.findViewById(R.id.tour_image);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.tour_description);
        }



    }




    public class TourAdapter extends RecyclerView.Adapter<TourViewHolder> {

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
            tourViewHolder.mTitleTextView.setText(tourEvent.getName());
            tourViewHolder.mDescriptionTextView.setText(tourEvent.getDescription());


        }

        @Override
        public int getItemCount() {

            return mTourListData.size();
        }
    }

}
