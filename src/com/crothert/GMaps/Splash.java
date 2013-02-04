package com.crothert.GMaps;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity{

	MediaPlayer ourSong;
	
	@Override
	protected void onCreate(Bundle corybacon) {
		// TODO Auto-generated method stub
		
		super.onCreate(corybacon);
		setContentView(R.layout.splash);
		ourSong = MediaPlayer.create(Splash.this, R.raw.wav);
		//set up preferences to play song or not on startup
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean music = getPrefs.getBoolean("checkbox", true);
		
			if (music == true)		
			ourSong.start();
			
		Thread timer = new Thread (){
			public void run(){
				try {
					sleep(3000);
					
				} catch (InterruptedException e){
					e.printStackTrace();
				} finally {
					Intent openStartingPoint = new Intent("com.crothert.GMaps.MENU");
					startActivity(openStartingPoint);
				}
			};
			
		};
		timer.start();
		
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ourSong.release();
		finish();
	}
	
	

}
