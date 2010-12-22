package local.yogsms;

import java.io.InputStream;
import java.security.Provider;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

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

	Cursor mContacts;
	Cursor mSMS;
	Context mContext;
	int mLayout;
	LayoutInflater mInflater;
	java.util.HashSet<String> set = new HashSet<String>();
	
	Dictionary<String, Integer> smsSenders ;
	
	public ContactCursorAdapter(Context context, Cursor contacts) {
		super();
		mContext = context;
		mContacts = contacts;
		mLayout = R.layout.contact_entry;
		mInflater = LayoutInflater.from(mContext);
		
		Cursor sms = querySMS(null, null, null, null);
		smsSenders = new Dictionary<String, Integer>();
		if(sms.getCount() > 0)
		{
			sms.moveToFirst();
			while(!sms.isLast())
			{
				
			}
		}
	}

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
