package com.example.smsscheduler;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected static final int PICK_CONTACT = 1;
	Button info, sendnow, schedule;
	ImageButton add;
	EditText count, msg;
	int i = 0;
	TextView contacts;

	int counter = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// initialize
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		add.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case PICK_CONTACT:

				Cursor cursor = null;
				String phoneNumber = "";
				List<String> allNumbers = new ArrayList<String>();
				int phoneIdx = 0;
				try {
					Uri result = data.getData();
					String id = result.getLastPathSegment();
					cursor = getContentResolver().query(Phone.CONTENT_URI,
							null, Phone.CONTACT_ID + "=?", new String[] { id },
							null);
					phoneIdx = cursor.getColumnIndex(Phone.DATA);
					if (cursor.moveToFirst()) {
						while (cursor.isAfterLast() == false) {
							phoneNumber = cursor.getString(phoneIdx);
							allNumbers.add(phoneNumber);
							cursor.moveToNext();
						}
					} else {
						// no results actions
					}
				} catch (Exception e) {
					// error actions
				} finally {
					if (cursor != null) {
						cursor.close();
					}

					final CharSequence[] items = allNumbers
							.toArray(new String[allNumbers.size()]);
					AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setTitle("Choose a number");
					builder.setItems(items,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int item) {
									String selectedNumber = items[item]
											.toString();
									// selectedNumber =
									// selectedNumber.replace("-", "");
									contacts.setText(selectedNumber);
								}
							});
					AlertDialog alert = builder.create();
					if (allNumbers.size() > 1) {
						alert.show();
					} else {
						String selectedNumber = phoneNumber.toString();
						// selectedNumber = selectedNumber.replace("-", "");
						contacts.setText(selectedNumber);
					}
					if (phoneNumber.length() == 0) {
						// no numbers found actions
					}
				}
				break;
			}
		} else {
			// activity result error actions
		}

		/*
		 * protected void onCreate(Bundle savedInstanceState) {
		 * super.onCreate(savedInstanceState); setContentView(R.layout.test);
		 * info=(Button) findViewById(R.id.info); sendnow=(Button)
		 * findViewById(R.id.sendnow); schedule=(Button)
		 * findViewById(R.id.schedule); count=(EditText)
		 * findViewById(R.id.count); contacts=(TextView)
		 * findViewById(R.id.contacts); msg=(EditText) findViewById(R.id.msg);
		 * add=(ImageButton) findViewById(R.id.add); add.setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Intent intent = new
		 * Intent(Intent.ACTION_GET_CONTENT);
		 * intent.setType(ContactsContract.CommonDataKinds
		 * .Phone.CONTENT_ITEM_TYPE); startActivityForResult(intent,
		 * PICK_CONTACT);
		 * 
		 * } });
		 * 
		 * 
		 * 
		 * 
		 * 
		 * // Toast.makeText(getApplicationContext(),
		 * "hi contact is selected!!", // Toast.LENGTH_SHORT).show(); }
		 * 
		 * @Override protected void onActivityResult(int requestCode, int
		 * resultCode, Intent data) {
		 * 
		 * super.onActivityResult(requestCode, resultCode, data); if (resultCode
		 * == RESULT_OK) { ContentResolver cr = getContentResolver(); Uri
		 * contactData = data.getData(); Cursor c =
		 * getContentResolver().query(contactData, null, null,null, null); if
		 * (c.moveToFirst()) { String id =
		 * c.getString(c.getColumnIndex(ContactsContract.Contacts._ID)); if
		 * (Integer
		 * .parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts
		 * .HAS_PHONE_NUMBER))) > 0) { Cursor pCur = cr.query(Phone.CONTENT_URI,
		 * null,Phone.CONTACT_ID + " = ?", new String[] { id }, null);
		 * 
		 * while (pCur.moveToNext()) { String cnumber =
		 * pCur.getString(pCur.getColumnIndex(Phone.NUMBER)); //
		 * Toast.makeText(getApplicationContext(), cnumber, //
		 * Toast.LENGTH_SHORT).show(); contacts.setText(cnumber); }
		 * 
		 * }
		 * 
		 * } }
		 */
		msg.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {

				i++;
				if (i >= 160) {
					counter = counter + 1;
				}
				count.setText(String.valueOf(i) + " / "
						+ String.valueOf(counter));
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

		info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), Info.class);
				startActivity(intent);

			}
		});
		schedule.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), Schedule.class);
				startActivity(intent);

			}
		});

		sendnow.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				String phoneNo = contacts.getText().toString();
				String sms = msg.getText().toString();

				try {
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phoneNo, null, sms, null, null);
					Toast.makeText(getApplicationContext(), "SMS Sent!",
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"SMS faild, please try again later!",
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}

			}
		});
	}
}
