package com.unithon.cafee;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

/**
 * Created by Youngdo on 2016-07-31.
 */
public class NMapMenu extends com.nhn.android.maps.NMapActivity {
    private NMapController mMapController;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mOverlayManager;
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "NMapViewer";
    NMapPOIitem item;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.map_menu);
        NMapView nMapView = (NMapView) findViewById(R.id.map_menu);
        nMapView.setClientId("dhLzbdmdF8IWWm6cKD85");

// set the activity content to the map view


// initialize map view
        nMapView.setClickable(true);
        //mMapView.setBuiltInZoomControls(true, null);
// register listener for map state changes
        mMapController = nMapView.getMapController();
        //onMapInitHandler(mMapView,);
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this, nMapView, mMapViewerResourceProvider);
        testPOIdataOverlay();
    }

    private void testPOIdataOverlay() {

        // Markers for POI item
        int markerId = NMapPOIflagType.PIN;

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        for(int i = 0; i<DataStorage.getItem_array().size();i++) {
            poiData.addPOIitem(DataStorage.getItem_array().get(i).getLongtitude(), DataStorage.getItem_array().get(i).getLatitude(),
                    DataStorage.getItem_array().get(i).getTitle(), markerId, 0);
        }

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
            Toast.makeText(NMapMenu.this, "onCalloutClick: " + item.getTitle(), Toast.LENGTH_LONG).show();
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