package com.crothert.GMaps;

import com.crothert.GMaps.R;
import android.content.SharedPreferences;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetNum extends Activity implements View.OnClickListener {


	EditText targetNum;
	String hovaCoptNum;
	Button bPhone;
	TextView display;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setnum);
		initializeVars();
		bPhone.setOnClickListener(this);
	}

	private void initializeVars() {
		// TODO Auto-generated method stub
		targetNum = (EditText) findViewById(R.id.etNum);
		bPhone = (Button) findViewById(R.id.bSetNum);
		display = (TextView) findViewById(R.id.tvNumber);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
				
	String number = targetNum.getText().toString();	
	display.setText(number);
	
	
	SharedPreferences info = getSharedPreferences("Prefs", MODE_PRIVATE);  
	SharedPreferences.Editor prefEditor = info.edit();  
	prefEditor.putString("SmsNum", number);  
	prefEditor.putBoolean("PaidUser", false);  
	prefEditor.commit();
	
	
	}

	private void convertEditTextVarsIntoStringsAndYesThisIsAMethodWeCreated() {
		// TODO Auto-generated method stub
		hovaCoptNum = targetNum.getText().toString();
	
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}


}
