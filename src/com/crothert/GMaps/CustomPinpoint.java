package com.crothert.GMaps;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class CustomPinpoint extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> pinpoints = new ArrayList<OverlayItem>();
	private Context c;
	
	
	public CustomPinpoint(Drawable defaultMarker) {
		super(boundCenter(defaultMarker));
		// TODO Auto-generated constructor stub
	}
	
	public CustomPinpoint(Drawable m, Context context) { //create this context c as the context passed in
		// TODO Auto-generated constructor stub
		this(m);
		c = context;		
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return pinpoints.get(i); //return the value in our arraylist
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return pinpoints.size();
	}
	
	public void insertPinpoint(OverlayItem item){
		pinpoints.add(item);
		this.populate();
	}

}
