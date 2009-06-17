package net.transita.sketches;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ZoomControls;

public class PolygonMap extends MapActivity {
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // to add controls to the map
        LinearLayout layout = (LinearLayout) findViewById(R.id.zoomview);
        MapView mapView = (MapView) findViewById(R.id.mapview);
        ZoomControls mZoom = (ZoomControls) mapView.getZoomControls();
        layout.addView(mZoom);

        mapView.setSatellite(true);
        mapView.setHapticFeedbackEnabled(true);
        
        // lat lon in Android Google Maps are integers, multiply by 1million
        int latE6 = (int)(1e6 * (37.758/2 + 37.800/2)); 
        int lonE6 = (int)(1e6 * (-122.441/2 + -122.389/2));
        int latSpanE6 = (int)(1e6 * (37.800 - 37.758));
        int lonSpanE6 = (int)(1e6 * (-122.389 - -122.441)); 

        MapController mc = mapView.getController();
        mc.setCenter(new GeoPoint(latE6, lonE6));
        mc.zoomToSpan(latSpanE6, lonSpanE6);        
        
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}