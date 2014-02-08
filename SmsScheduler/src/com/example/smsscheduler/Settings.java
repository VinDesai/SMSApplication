package com.example.smsscheduler;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

public class Settings extends Service{
	 String smsNumberToSend, smsTextToSend;

	@Override
	public IBinder onBind(Intent arg0) {
		
		return null;
	}

	public void onCreate() {
				super.onCreate();
				

			      Toast.makeText(this, "Settings.onCreate()", Toast.LENGTH_LONG).show();
			     }


			     @Override
			     public void onDestroy() {
			     
			       super.onDestroy();
			       Toast.makeText(this, "Settings.onDestroy()", Toast.LENGTH_LONG).show();
			     }

			     @Override
			     public void onStart(Intent intent, int startId) {

			         Toast.makeText(this, "Settings.onStart()", Toast.LENGTH_LONG).show();
			      
			      //super.onStart(intent, startId);

			      Bundle bundle = intent.getExtras();
			           smsNumberToSend = (String) bundle.getCharSequence("Number");
			           smsTextToSend = (String) bundle.getCharSequence("Message");

			      Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();
			      Toast.makeText(this,
			             "MyAlarmService.onStart() with \n" +
			             "smsNumberToSend = " + smsNumberToSend + "\n" +
			             "smsTextToSend = " + smsTextToSend,
			             Toast.LENGTH_LONG).show();

			      SmsManager smsManager = SmsManager.getDefault();
			      smsManager.sendTextMessage(smsNumberToSend, null, smsTextToSend, null, null);

			     }
			   
			}
	


