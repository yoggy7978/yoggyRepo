package local.yogsms;

import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class ContactList extends Activity {

	public static final String TAG = "YogSMS";
	private ListView mContactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.conversation_list);

			mContactList = (ListView) findViewById(R.id.contactList);

			Cursor cur = querySMSConversations();
			ContactCursorAdapter adapter = new ContactCursorAdapter(
					this, 
					cur);
			mContactList.setAdapter(adapter);

		} catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
			toast.show();
			Log.e(TAG, "exception:", e);
		}
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

	private Cursor querySMSConversations() {
		Uri uri = Uri.parse("content://sms/conversations");
		return managedQuery(uri, null, null, null, null);
	}	
	
	/*private void dumpContentProvider(String struri) {
		Log.d(TAG, struri);
		Uri uri = Uri.parse(struri);
		Cursor cur = managedQuery(uri, null, null, null, null);
		int count = cur.getColumnCount();
		Log.d(TAG, "Nb columns: " + count);
		cur.moveToFirst();
		for(int i = 0 ; i < count ; i++)
		{
			Log.d(TAG, cur.getColumnName(i));
		}
		Log.d(TAG, "Nb records: " + cur.getCount());
		boolean go = true;
		while(go)
		{
			Log.d(TAG, "    - - - - - - - - - - ");
			for(int i = 0 ; i < count ; i++)
			{
				try
				{
					Log.d(TAG, "    " + cur.getColumnName(i) + "(string): " + cur.getString(i));
				}
				catch(Exception e)
				{
					try
					{
						Log.d(TAG, "    " + cur.getColumnName(i) + "(int): " + cur.getInt(i));
					}
					catch(Exception ex)
					{
						try
						{
							Log.d(TAG, "    " + cur.getColumnName(i) + "no known data type");
						}
						catch(Exception exx)
						{
							
						}						
					}				
					
				}
			}
			go =cur.moveToNext();
		}
		
	}				*/
}