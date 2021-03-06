package com.crothert.GMaps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsListener extends BroadcastReceiver{

    //SharedPreferences info = getSharedPreferences("Prefs", MODE_PRIVATE);
    //SharedPreferences.Editor prefEditor = info.edit();
	String msgBody = null;
	String msg_from;
	
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            //@SuppressWarnings("unused")
			
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        
                        
                    
                    }
                }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
                }
                
                
                
            }
        }
    }


}