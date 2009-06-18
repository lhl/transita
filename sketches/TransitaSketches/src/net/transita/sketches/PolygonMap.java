package net.transita.sketches;

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ZoomControls;

public class PolygonMap extends MapActivity implements LocationListener {
    
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
        
        MapController mc = mapView.getController();
        
        // focus on the last known location
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);
//        criteria.setPowerRequirement(Criteria.POWER_HIGH);
//        criteria.setAltitudeRequired(true);
//        criteria.setBearingRequired(true);
//        criteria.setSpeedRequired(true);
//        criteria.setCostAllowed(true);
//        String provider = locationManager.getBestProvider(criteria, true);
        String provider = LocationManager.NETWORK_PROVIDER;
        Log.i("PolygonMap() provider:", provider);        
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            // lat lon in Android Google Maps are integers, multiply by 1million
            int latE6 = (int)(1e6 * location.getLatitude());
            int lonE6 = (int)(1e6 * location.getLongitude());
            mc.setCenter(new GeoPoint(latE6, lonE6));
            mc.zoomToSpan(42000, 52000);
        }
//        else {
//        	// or be more San Francisco-ish about things
//            int latE6 = (int)(1e6 * (37.758/2 + 37.800/2)); 
//            int lonE6 = (int)(1e6 * (-122.441/2 + -122.389/2));
//            int latSpanE6 = (int)(1e6 * (37.800 - 37.758));
//            int lonSpanE6 = (int)(1e6 * (-122.389 - -122.441)); 
//            mc.setCenter(new GeoPoint(latE6, lonE6));
//            mc.zoomToSpan(latSpanE6, lonSpanE6);
//        }
        
        // let's subscribe to location changes and see how awful that is
        long minTime = 0; // milliseconds
        float minDistance = 0.0f; // meters
        locationManager.requestLocationUpdates(provider, minTime, minDistance, this);        
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.i("PolygonMap.onLocationChanged", location.toString());
        MapView mapView = (MapView) findViewById(R.id.mapview);
        MapController mc = mapView.getController();
        int latE6 = (int)(1e6 * location.getLatitude());
        int lonE6 = (int)(1e6 * location.getLongitude());
        mc.setCenter(new GeoPoint(latE6, lonE6));
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}   
}