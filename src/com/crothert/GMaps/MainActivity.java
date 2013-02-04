package com.crothert.GMaps;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.crothert.GMaps.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends MapActivity implements LocationListener{

	private MapView map;
	private MapController controller;
	MyLocationOverlay compass;	
	long start, stop;	
	int x, y;
	int t1,t2;
	int CurrentLat = 0, CurrentLon = 0, counter, ct=0,cl=0;
	double txx, txy;
	GeoPoint touchedPoint;
	Drawable d, next;
	List<Overlay> overlayList;
	LocationManager lm;
		MyLocationListener locLstnr; 
	String towers, hNumber = null;
	String sendTx;

	Button add, sub, bstop, bhover, bhome, bsend, bnewloc;
	TextView display, Sent, Set, tvdis;
	TextView Lat,Longi,Lat2,Longi2;
	//Double Lati, Longii;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        map = (MapView) findViewById(R.id.mvMain);
        controller = map.getController();
        //////////////////////
        counter = 0;
        add = (Button) findViewById(R.id.bAdd);
        sub = (Button) findViewById(R.id.bSub);
        bhover = (Button) findViewById(R.id.bHover);
        bstop = (Button) findViewById(R.id.bStop);
        bhome = (Button) findViewById(R.id.bHome);
        bsend = (Button) findViewById(R.id.bsend);
        bnewloc = (Button) findViewById(R.id.bnewloc);
        
        display = (TextView) findViewById(R.id.tvDisplay);
        Lat = (TextView) findViewById(R.id.tvLat);
        Longi = (TextView) findViewById(R.id.tvLongi);
        Lat2 = (TextView) findViewById(R.id.tvLat2);
        Longi2 = (TextView) findViewById(R.id.tvLongi2);
        Sent = (TextView) findViewById(R.id.tvsent);
        Set = (TextView) findViewById(R.id.tvset);
        tvdis = (TextView) findViewById(R.id.tvdis);
        
        
        
        //controller stuff
        t1 = 39729436;
        t2 = -121845258;
        
        GeoPoint point = new GeoPoint(t1,t2);
        Lat2.setText(""+ t1);
		Longi2.setText(""+ t2);
        
        controller.animateTo(point);
        controller.setZoom(22);
        
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locLstnr = new MyLocationListener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locLstnr);
        
        SharedPreferences info = getSharedPreferences("Prefs", MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = info.edit();
        
        add.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				counter++;
				display.setText("Newheight: " + counter);
			}
		});
        
/**************************************************************************************/
        
        sub.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				counter--;
				display.setText("Newheight: " + counter);
				
				
			}
		});
/**************************************************************************************/        
		bstop.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						sendTx = "1123,stop";
					}
				});
/**************************************************************************************/
		bhover.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
						sendTx = "1123,hover";
			}
		});
        
/**************************************************************************************/		
		bhome.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
						sendTx = "1123,home";
			}
		});
/**************************************************************************************/
		bnewloc.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
						sendTx = "1123," + txx + "," + txy + "," + counter + '\n';
						
			}
		});
/**************************************************************************************/		
	
		
		
		hNumber = info.getString("SmsNum","DEFAULT");
		Set.setText("Number set");		
					
			bsend.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if (hNumber != null){
					SmsManager sm2 = SmsManager.getDefault();
					sm2.sendTextMessage(hNumber, null, sendTx, null, null);
					Sent.setText("YaSent");
					tvdis.setText(sendTx);
					}
				}
			});
			
		
/**************************************************************************************/				
		
        

    	prefEditor.putInt("newHeight", counter);
    	prefEditor.commit();

    	//set up the map
        map.setClickable(true);
        map.setBuiltInZoomControls(true);
        
        Touchy t = new Touchy();
        overlayList = map.getOverlays();
        //add the touchy instance to the overlays
        overlayList.add(t);
             
        //compass stuff
        compass = new MyLocationOverlay(MainActivity.this, map);
        overlayList.add(compass);

        d = getResources().getDrawable(R.drawable.home);
        next = getResources().getDrawable(R.drawable.ic_launcher);        
        
        OverlayItem overlayItem = new OverlayItem(point, "What up", "2nd String");
        
		CustomPinpoint custom = new CustomPinpoint(d, MainActivity.this);
		custom.insertPinpoint(overlayItem);
		overlayList.add(custom);
			
        //Placing pinpoint at location
        Criteria crit = new Criteria();
        
        towers = lm.getBestProvider(crit, false);
        Location location = lm.getLastKnownLocation(towers);
        
        if(location != null){
        	CurrentLat = (int) (location.getLatitude() * 1E6);
        	CurrentLon = (int) (location.getLongitude() * 1E6);
        
        	prefEditor.putInt("CurrentLat", CurrentLat);
        	prefEditor.putInt("CurrentLon", CurrentLon);
        	prefEditor.commit();       	
        	            
            GeoPoint ourLocation = new GeoPoint(CurrentLat,CurrentLon);
            controller.animateTo(ourLocation);
            
            //OverlayItem overlayItem = new OverlayItem(ourLocation, "What up", "2nd String");
    		//CustomPinpoint custom = new CustomPinpoint(d, MainActivity.this);
    		custom.insertPinpoint(overlayItem);
    		overlayList.add(custom);
    		
        }else{
        	Toast.makeText(MainActivity.this, "Could not get provider!@", Toast.LENGTH_SHORT).show();
        }

        
//****End of onCreate
     }  
    
    
  

    //***

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    class Touchy extends Overlay{
		public boolean onTouchEvent(MotionEvent e, MapView m){
			
			if(e.getAction() == MotionEvent.ACTION_DOWN){
				start = e.getEventTime();
				//txx = e.getX();
				//txy = e.getY();
				
				x = (int)e.getX();//setting the GEOPOINTS
				y = (int)e.getY();	
				
				touchedPoint = map.getProjection().fromPixels(x, y);	
				
				txx = touchedPoint.getLatitudeE6();
				txy = touchedPoint.getLongitudeE6();
			}
			
			if(e.getAction() == MotionEvent.ACTION_UP){
				stop = e.getEventTime();
			}
			
			if((stop - start) < 1500){
			
				Lat.setText(""+ txx);
				Longi.setText(""+ txy);
				
			}
			
			if((stop - start) > 1500){
				Lat.setText(""+ txx);
				Longi.setText(""+ txy);
				
				AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
				alert.setTitle("Pick an option");
				alert.setMessage("I told you to pick an option..");
//*************************************************************************************************
//*************************************************************************************************
				alert.setButton("place a pin!", new DialogInterface.OnClickListener() {
					
					
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						OverlayItem overlayItem = new OverlayItem(touchedPoint, "What up", "2nd String");
						CustomPinpoint custom = new CustomPinpoint(next, MainActivity.this);
						custom.insertPinpoint(overlayItem);
						overlayList.add(custom);	
						

						
					}
					
				});
//*************************************************************************************************
//*************************************************************************************************
				alert.setButton2("Get address", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
						
						try{
							List<Address> address = geocoder.getFromLocation(touchedPoint.getLatitudeE6() / 1E6, touchedPoint.getLongitudeE6() / 1E6, 1);
						
							if (address.size() > 0){
								String display = "";
								for (int i = 0; i <address.get(0).getMaxAddressLineIndex();i++){
									display += address.get(0).getAddressLine(i) + "\n";
								}
								//dislpay the address
								Toast t = Toast.makeText(getBaseContext(), display, Toast.LENGTH_LONG);
								t.show();									
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{							
						}						
					}
				});				
				//Button to see which view the user wants.
//*************************************************************************************************
//*************************************************************************************************
				alert.setButton3("Toggle Views", new DialogInterface.OnClickListener() {
					
					@SuppressWarnings("deprecation")
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						if(map.isSatellite()){
							map.setSatellite(false);
							map.setStreetView(true);
						}else{
							map.setStreetView(false);
							map.setSatellite(true);
						}						
					}
				});

				alert.show();
				return true;	

				
			}		
			
			return false;
		}	
	}

	public void onLocationChanged(Location l) {
		// TODO Auto-generated method stub
		CurrentLat = (int) (l.getLatitude() *1E6);
		CurrentLon = (int) (l.getLongitude() *1E6);
		//paint a new icon
		GeoPoint ourLocation = new GeoPoint(CurrentLat,CurrentLon);
        OverlayItem overlayItem = new OverlayItem(ourLocation, "What up", "2nd String");
		CustomPinpoint custom = new CustomPinpoint(d, MainActivity.this);
		custom.insertPinpoint(overlayItem);
		overlayList.add(custom);
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
