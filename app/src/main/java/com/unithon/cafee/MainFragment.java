package com.unithon.cafee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nhn.android.maps.NMapContext;

/**
 * Created by Youngdo on 2016-07-30.
 */
public class MainFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    private int position;
    RecyclerView recyclerView_Main;
    private NMapContext mMapContext;
    public static MainFragment newInstance(int position) {
        MainFragment f = new MainFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        mMapContext =  new NMapContext(super.getActivity());

        mMapContext.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        switch (position) {
            case 0:
                RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_main, container, false);
                final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) relativeLayout.findViewById(R.id.swipeLayout);


                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

                relativeLayout.removeAllViews();
                relativeLayout.addView(swipeRefreshLayout);

                return relativeLayout;
            case 1:/*
                RelativeLayout relativeLayout1 = (RelativeLayout) inflater.inflate(R.layout.fragment1, container, false);
                NMapView nMapView = (NMapView) relativeLayout1.findViewById(R.id.mapView);
                nMapView.setClientId("dhLzbdmdF8IWWm6cKD85");
                nMapView.setClickable(true);
                nMapView.setEnabled(true);
                nMapView.setFocusable(true);
                nMapView.setFocusableInTouchMode(true);
                nMapView.requestFocus();
                mMapContext.setupMapView(nMapView);
                relativeLayout1.removeAllViews();
                relativeLayout1.addView(nMapView);
                */
                return null;
            default:
                return null;
        }
    }
}
