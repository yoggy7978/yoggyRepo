package local.yogsms;

import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ContactList extends Activity {

	public static final String TAG = "YogSMS";
	private ListView mContactList;
	private static YogSMSConversation mLastSelectedYogConversation= null;

	public static YogSMSConversation getLastSelectedYogConversation()
	{
		return mLastSelectedYogConversation;
	}
	
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

			mContactList.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> a, View v, int position, long id) {
					mLastSelectedYogConversation = (YogSMSConversation)mContactList.getItemAtPosition(position);
					startActivity(new Intent(ContactList.this, SMSList.class)) ;
				}
			});	
			//dumpContentProvider("content://sms");

			
			
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
	
	/*private Cursor querySMS() {
		Uri uri = Uri.parse("content://sms");
		return managedQuery(uri, null, null, null, null);
	}*/
	
	public static void dumpContentProvider(Context context, Uri uri) {
		Log.d(TAG, "Uri: " + uri.toString());
		Cursor cur = ((Activity)context).managedQuery(uri, null, null, null, null);
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
		
	}			
}