package local.yogsms;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class ContactList extends Activity {

	public static final String TAG = "YogSMS";
	private ListView mContactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.d(TAG, "ENTERING onCreate");

		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.contact_list);

			mContactList = (ListView) findViewById(R.id.contactList);

			ContactCursorAdapter adapter = new ContactCursorAdapter(
					this, 
					getSMSContacts());
			mContactList.setAdapter(adapter);

		} catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
			toast.show();
			Log.e(TAG, "exception:", e);
		}
		Log.d(TAG, "EXITING onCreate");
	}

	public static Bitmap loadContactPhoto(ContentResolver cr, long id) {
		Uri uri = ContentUris.withAppendedId(
				ContactsContract.Contacts.CONTENT_URI, id);
		InputStream input = ContactsContract.Contacts
				.openContactPhotoInputStream(cr, uri);
		if (input == null) {
			return null;
		}
		return BitmapFactory.decodeStream(input);
	}

	private ArrayList<SMSContact> getSMSContacts()
	{
		ArrayList<SMSContact> smscontacts = new ArrayList<SMSContact>();
		Cursor sms = querySMS(null, null, null, null);
		
		java.util.HashMap<String, SMSContact> map = new HashMap<String, SMSContact>();
		int c = sms.getCount() ;
		Log.d(TAG, "Nb SMS: " + c);
		for(int i = 0 ; i < c ; i++)
		{
			sms.moveToPosition(i); 

		/*	Log.d(TAG, "--> " + i);
				Log.d(TAG, "    person " + sms.getString(sms.getColumnIndex("person")));
			Log.d(TAG, "    address " + sms.getString(sms.getColumnIndex("address")));
			Log.d(TAG, "    thread_id " + sms.getString(sms.getColumnIndex("thread_id")));
			Log.d(TAG, "    date " + sms.getString(sms.getColumnIndex("date")));
			Log.d(TAG, "    protocol " + sms.getString(sms.getColumnIndex("protocol")));
			Log.d(TAG, "    read " + sms.getString(sms.getColumnIndex("read")));
			Log.d(TAG, "    status " + sms.getString(sms.getColumnIndex("status")));
			Log.d(TAG, "    type " + sms.getString(sms.getColumnIndex("type")));
			Log.d(TAG, "    reply_path_present " + sms.getString(sms.getColumnIndex("reply_path_present")));
			Log.d(TAG, "    subject " + sms.getString(sms.getColumnIndex("subject")));
			Log.d(TAG, "    body " + sms.getString(sms.getColumnIndex("body")));
			Log.d(TAG, "    service_center " + sms.getString(sms.getColumnIndex("service_center")));
			Log.d(TAG, "    locked " + sms.getString(sms.getColumnIndex("locked")));	*/			
			
			String address = sms.getString(sms.getColumnIndex("address"));
			Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));
			Cursor lookup = managedQuery(uri, null,null,null, null);
			int id = 0 ;
			if(lookup.getCount() > 0)
			{
				lookup.moveToFirst();
				id = lookup.getInt(lookup.getColumnIndex(PhoneLookup._ID));
				address = lookup.getString(lookup.getColumnIndex(PhoneLookup.NUMBER));
			}
			
			SMSContact cont ;
			if(!map.containsKey(address))
			{
				cont = new SMSContact();
				smscontacts.add(cont);
				map.put(address, cont);
			}
			else
			{
				cont = (SMSContact)map.get(address);
			}
			cont.contactID = id;
			cont.smscount += 1;
			// = sms.getInt(sms.getColumnIndex("person"));
			cont.telephone = address;
			cont.hasphoto = false;
			if(lookup.getCount() > 0)
			{
				if(lookup.getString(lookup.getColumnIndex(PhoneLookup.PHOTO_ID)) != null)
				{
					cont.hasphoto = true;
				}
				cont.knownContact = true;
				cont.displayName = lookup.getString(lookup.getColumnIndex(PhoneLookup.DISPLAY_NAME));
			}
			else
			{
				cont.knownContact = false;
				cont.displayName = cont.telephone;
			}
				
			if(!sms.getString(sms.getColumnIndex("read")).equals("1"))
			{
				cont.unread ++;
			}
		}
		return smscontacts;
	}
	/*
	private Cursor getContactFromId(int id) {
		try {
			Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
			return managedQuery(uri, null, null, null, null);
		}catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
			toast.show();
			Log.e(TAG, "exception:", e);
		}		
		return null;
		
	}		*/
	
	private Cursor querySMS(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Uri uri = Uri.parse("content://sms");
		return managedQuery(uri, null, null, null, null);
	}			
	
	/* SMS table
	_id 
	thread_id 
	address 
	person 
	date 
	protocol 
	read 
	status 
	type 
	reply_path_present 
	subject 
	body 
	service_center 
	locked */
	
	/*int collcount = sms.getColumnCount();
	
	sms.moveToFirst();
	for(int i = 0 ; i < collcount ; i++)
	{
		Log.d(TAG, sms.getColumnName(i) + " ;");
	}*/
	




}