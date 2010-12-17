package local.yogsms;

import java.io.InputStream;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ContactList extends Activity {

	public static final String TAG = "YogSMS";
	private ListView mContactList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.d(TAG, "ENTERING onCreate"); 
		
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.contact_list);
			
			mContactList = (ListView)findViewById(R.id.contactList);
			
			this.populateContactList();
			/*
			 * Log.d("YogSMS", "start listing"); Uri uri =
			 * Uri.parse("content://contacts"); managedCursor =
			 * managedQuery(uri, null, null, null, null);
			 * 
			 * managedCursor.moveToFirst(); for(int i = 0 ; i <
			 * managedCursor.getColumnCount() ; i++) { Log.d("YogSMS", "SMS: " +
			 * i + " : "+ managedCursor.getColumnName(i)); } ListAdapter adapter
			 * = new SimpleCursorAdapter( this, R.layout.smsview, managedCursor,
			 * new String[] {"person", "address", "body"}, new int[]
			 * {R.id.person, R.id.address, R.id.body});
			 * 
			 * dd = (ListView)findViewById(R.id.list);; dd.setAdapter(adapter);
			 * dd.setOnItemClickListener(new OnItemClickListener() { public void
			 * onItemClick(AdapterView<?> arg0, View arg1, int position, long
			 * id) { try { Context context = getApplicationContext();
			 * 
			 * managedCursor.moveToPosition(position); String text =
			 * managedCursor.getString(managedCursor.getColumnIndex("address"));
			 * 
			 * Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			 * toast.show();
			 * 
			 * // Android 2.0 and later Uri uri =
			 * Uri.withAppendedPath(ContactsContract
			 * .PhoneLookup.CONTENT_FILTER_URI, Uri.encode(text));
			 * 
			 * // Query the filter URI String[] projection = new String[]{
			 * PhoneLookup.DISPLAY_NAME }; ContentResolver cr =
			 * context.getContentResolver(); Cursor cursor = cr.query(uri, null,
			 * null, null, null);
			 * 
			 * TextView texte = (TextView)findViewById(R.id.who); ImageView
			 * image = (ImageView)findViewById(R.id.imageItem);
			 * if(cursor.getCount() == 0) { texte.setText(""); } else {
			 * cursor.moveToFirst(); for(int i = 0 ; i < cursor.getColumnCount()
			 * ; i++) { try { Log.d("YogSMS", "Contact : " + i + " : "+
			 * cursor.getColumnName(i) + " : " + cursor.getString(i)); }
			 * catch(Exception e) {
			 * 
			 * } }
			 * texte.setText(cursor.getString(cursor.getColumnIndex(PhoneLookup
			 * .DISPLAY_NAME))); int imageID =
			 * cursor.getInt(cursor.getColumnIndex(PhoneLookup._ID)); Bitmap bm
			 * = Main.loadContactPhoto(cr, imageID ); if(bm != null)
			 * image.setImageBitmap(bm);
			 * 
			 * } } catch(Exception e) { Context context =
			 * getApplicationContext(); Toast toast = Toast.makeText(context,
			 * e.getMessage(), Toast.LENGTH_LONG); toast.show(); Log.e("YogSMS",
			 * "exception:", e); } } });
			 */
		} catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
			toast.show();
			Log.e(TAG, "exception:", e);
		}
		Log.d(TAG, "EXITING onCreate");
	}

	public static Bitmap loadContactPhoto(ContentResolver cr, long id) {
		Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
		InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
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
		// Build adapter with contact entries
		Cursor cursor = getContacts();
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				this,
				R.layout.contact_entry, 
				cursor, 
				new String[] { ContactsContract.Data.DISPLAY_NAME },
				new int[] { R.id.contact_displayname });
		mContactList.setAdapter(adapter);
	}

	/**
	 * Obtains the contact list for the currently selected account.
	 * 
	 * @return A cursor for for accessing the contact list.
	 */
	private Cursor getContacts() {
		// Run query
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME };
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

		return managedQuery(uri, projection, selection, selectionArgs, sortOrder);
	}
}

class ContactCursorAdapter extends BaseAdapter
{

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	
}