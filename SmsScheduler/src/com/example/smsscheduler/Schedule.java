package com.example.smsscheduler;

import java.util.Calendar;

import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class Schedule extends Activity {
	Button save;
	EditText contacts,msg;
	TimePicker tp;
	DatePicker dp;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calender);
		setContentView(R.layout.test);
		save=(Button) findViewById(R.id.save);
		contacts=(EditText) findViewById(R.id.contacts);
        msg=(EditText) findViewById(R.id.msg);
		 int Hour = tp.getCurrentHour();
         int Minute = tp.getCurrentMinute();

     DatePicker Date_Picker = (DatePicker)findViewById(R.id.datePicker1);
     int day = Date_Picker.getDayOfMonth();
      int month = Date_Picker.getMonth() + 1;
      int year = Date_Picker.getYear();


     Intent myIntent = new Intent(Schedule.this, Settings.class);

     Bundle bundle = new Bundle();
              bundle.putCharSequence("Number", contacts.getText().toString());
              bundle.putCharSequence("Message", msg.getText().toString());
              myIntent.putExtras(bundle);

              PendingIntent  pendingIntent = PendingIntent.getService(Schedule.this, 0, myIntent, 0);

AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(System.currentTimeMillis());
      //calendar.add(Calendar.SECOND, 10);
      calendar.set(year, month, day, Hour, Minute);

      alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

              Toast.makeText(Schedule.this,"Start Alarm with \n" + 
              "smsNumber = " + contacts.getText().toString() + 
              "\n" + "smsText = " + msg.getText().toString() + "\nScheduled for :"
              + Hour +" "+Minute,
                Toast.LENGTH_LONG).show();
		
				
		
	}

	

}
