package com.crothert.GMaps;

import com.crothert.GMaps.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;

public class TestConn extends Activity implements View.OnClickListener{
	
	
	Button testConn, testYaw;
	TextView dis, resp, ang;
	String hNumber;
	int newLat, newLon, newHei;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testconn);
		initializeVars();
		testConn.setOnClickListener(this);
		testYaw.setOnClickListener(this);
		
		SharedPreferences info = getSharedPreferences("Prefs", MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = info.edit();
		hNumber = info.getString("SmsNum","DEFAULT");		
		
		newLat = info.getInt("nextLat",0);
		newLon = info.getInt("nextLon",0);
		newHei = info.getInt("nextHeight",0);
		dis.setText(hNumber);
	}

	private void initializeVars() {
		// TODO Auto-generated method stub
		testConn = (Button) findViewById(R.id.bResponding);
		testYaw = (Button) findViewById(R.id.bAngles);		
		dis = (TextView) findViewById(R.id.tvNumber1);
		resp = (TextView)findViewById(R.id.tvResp);
		ang = (TextView)findViewById(R.id.tvAng);
	}
	
	public void onClick(View v){
	
		switch (v.getId()){
		case R.id.bResponding:	
			
			SmsManager sm = SmsManager.getDefault();			
			sm.sendTextMessage(hNumber, null, "1123,?", null, null);
			resp.setText("Sent");
			
			break;
		case R.id.bAngles:
			
			SmsManager sm1 = SmsManager.getDefault();
			sm1.sendTextMessage(hNumber, null, "1123,angles", null, null);
			ang.setText("Sent");
			
			break;
			
		case 3:
			SmsManager sm2 = SmsManager.getDefault();
			sm2.sendTextMessage(hNumber, null, "1123" + "," + newLat + "," +  newLon + "," + newHei, null, null);
			ang.setText("Sent");
			
			
			
		default:
			break;
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
