package com.unithon.cafee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

/**
 * Created by Youngdo on 2016-07-30.
 */
public class NMapActivity extends com.nhn.android.maps.NMapActivity {
    private NMapController mMapController;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mOverlayManager;
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "NMapViewer";
    double Latitude, Longtitude;
    TextView title, workgroup_type, text, create_at, join, max, location;
    int position = 0;
    Button make;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.map_activity);
        title = (TextView) findViewById(R.id.title);
        workgroup_type = (TextView) findViewById(R.id.workgroup_type);
        text = (TextView) findViewById(R.id.text);
        create_at = (TextView) findViewById(R.id.time);
        join = (TextView) findViewById(R.id.join);
        max = (TextView) findViewById(R.id.max);
        location = (TextView) findViewById(R.id.location);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        Latitude = DataStorage.getItem_array().get(position).getLatitude();
        Longtitude = DataStorage.getItem_array().get(position).getLongtitude();
        make = (Button) findViewById(R.id.button2);
        make.onc
        title.setText(DataStorage.getItem_array().get(position).getTitle());
        workgroup_type.setText(DataStorage.getItem_array().get(position).getWorkgroup_type());
        text.setText(DataStorage.getItem_array().get(position).getText());
        create_at.setText(DataStorage.getItem_array().get(position).getCreated_at());
        join.setText(String.valueOf(DataStorage.getItem_array().get(position).getJoin_user_count()));
        max.setText(String.valueOf(DataStorage.getItem_array().get(position).getMax_user_count()));
        location.setText(DataStorage.getItem_array().get(position).getWorkplace_name());
        NMapView mMapView = (NMapView) findViewById(R.id.mapview);
        // set Client ID for Open MapViewer Library
        mMapView.setClientId("dhLzbdmdF8IWWm6cKD85");

// set the activity content to the map view


// initialize map view
        mMapView.setClickable(true);
        //mMapView.setBuiltInZoomControls(true, null);
// register listener for map state changes
        mMapController = mMapView.getMapController();
        //onMapInitHandler(mMapView,);
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);
        testPOIdataOverlay();
    }

    private void testPOIdataOverlay() {

        // Markers for POI item
        int markerId = NMapPOIflagType.PIN;

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        NMapPOIitem item = poiData.addPOIitem(Longtitude, Latitude, DataStorage.getItem_array().get(position).getTitle(), markerId, 0);
        item.setRightAccessory(true, NMapPOIflagType.CLICKABLE_ARROW);
        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

        // set event listener to the overlay
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

        // select an item
        poiDataOverlay.selectPOIitem(0, true);

        // show all POI data
        //poiDataOverlay.showAllPOIdata(0);
    }

    private final NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {

        @Override
        public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            if (DEBUG) {
                Log.i(LOG_TAG, "onCalloutClick: title=" + item.getTitle());
            }

            // [[TEMP]] handle a click event of the callout
            Toast.makeText(NMapActivity.this, "onCalloutClick: " + item.getTitle(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            if (DEBUG) {
                if (item != null) {
                    Log.i(LOG_TAG, "onFocusChanged: " + item.toString());
                } else {
                    Log.i(LOG_TAG, "onFocusChanged: ");
                }
            }
        }
    };
}
