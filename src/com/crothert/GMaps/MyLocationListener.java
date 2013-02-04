package com.crothert.GMaps;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class MyLocationListener implements LocationListener
{
	MapView map;	
	MapController controller;
	
public void onLocationChanged(Location loc)
{
	
    controller = map.getController();
    
loc.getLatitude();
loc.getLongitude();
String Text = "My current location is: " +
"Latitud = " + loc.getLatitude() +
"Longitud = " + loc.getLongitude();
Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();

String coordinates[] = {""+loc.getLatitude(), ""+loc.getLongitude()};
double lat = Double.parseDouble(coordinates[0]);
double lng = Double.parseDouble(coordinates[1]);

GeoPoint p = new GeoPoint(
(int) (lat * 1E6),
(int) (lng * 1E6));

controller.animateTo(p);
controller.setZoom(7);
map.invalidate();


}

private Context getApplicationContext() {
	// TODO Auto-generated method stub
	return null;
}

public void onProviderDisabled(String provider)
{
Toast.makeText( getApplicationContext(),
"Gps Disabled",
Toast.LENGTH_SHORT ).show();
}


public void onStatusChanged(String provider, int status, Bundle extras)
{

}

public void onProviderEnabled(String provider) {
	// TODO Auto-generated method stub
	
}




}

