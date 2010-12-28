package local.yogsms;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

			this.populateContactList();

		} catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(), e
					.getMessage(), Toast.LENGTH_LONG);
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

	/**
	 * Populate the contact list based on account currently selected in the
	 * account spinner.
	 */
	private void populateContactList() {
		ContactCursorAdapter adapter = new ContactCursorAdapter(
				this, 
				getContacts());
		mContactList.setAdapter(adapter);

	}

	/**
	 * Obtains the contact list for the currently selected account.
	 * 
	 * @return A cursor for for accessing the contact list.
	 */
	private Cursor getContacts() {
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME };
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

		return managedQuery(uri, projection, selection, selectionArgs, sortOrder);
	}	
}

class ContactCursorAdapter extends BaseAdapter {

	public static final String TAG = "YogSMS";
	Cursor mContacts;
	Cursor mSMS;
	Context mContext;
	int mLayout;
	LayoutInflater mInflater;
	java.util.HashMap<String, Integer> map = new HashMap<String, Integer>();
		
	ArrayList<SMSContact> smsSenders;
	
	public ContactCursorAdapter(Context context, Cursor contacts) {
		super();
		mContext = context;
		mContacts = contacts;
		mLayout = R.layout.contact_entry;
		mInflater = LayoutInflater.from(mContext);
		
		Cursor sms = querySMS(null, null, null, null);
		
		/*int collcount = sms.getColumnCount();
		
		sms.moveToFirst();
		for(int i = 0 ; i < collcount ; i++)
		{
			Log.d(TAG, sms.getColumnName(i) + " ;");
		}*/
		int c = sms.getCount() ;
		Log.d(TAG, "Nb SMS: " + c);
		for(int i = 0 ; i < c ; i++)
		{
			sms.moveToPosition(i);
			Log.d(TAG, "--> " + i);
			String pers = sms.getString(sms.getColumnIndex("person"));
			Log.d(TAG, "    person " + pers);
			String address = sms.getString(sms.getColumnIndex("address"));
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
			Log.d(TAG, "    locked " + sms.getString(sms.getColumnIndex("locked")));
/*			SMSContact cont ;
			if(!map.containsKey(person))
			{
				cont = new SMSContact();
			}
			else
			{
				cont = map.get(key)
			}*/
		}
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
	
	
	private Cursor querySMS(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Uri uri = Uri.parse("content://sms");
		return ((Activity) mContext).managedQuery(uri, null, null, null, null);
	}	
	
	public int getCount() {
		return mContacts.getCount();
	}

	public Object getItem(int arg0) {
		mContacts.moveToPosition(arg0);
		return mContacts.getString(mContacts.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		mContacts.moveToPosition(position);
		if (convertView == null) {
			convertView = mInflater.inflate(mLayout, null);
		} else {
			convertView = (View) convertView;
		}
		TextView tv = (TextView) convertView.findViewById(R.id.contact_displayname);
		tv.setText(mContacts.getString(mContacts.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)));
		ImageView iv = (ImageView) convertView.findViewById(R.id.contact_photo);
		Bitmap bm = ContactList.loadContactPhoto(mContext.getContentResolver(), mContacts.getInt(mContacts.getColumnIndex(PhoneLookup._ID)));
		if (bm != null)
			iv.setImageBitmap(bm);
		
		return convertView;
	}

}

class SMSContact
{
	public boolean knownContact ;
	public String displayName;
	public String telephone;
	public int contactID;
	public int unread;
	SMSContact()
	{
		unread = 0;
	}

}
