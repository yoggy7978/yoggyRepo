package local.yogsms;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Main extends Activity {
	private Cursor managedCursor = null;
	private ListView dd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			   
			Log.d("YogSMS", "start listing");
			Uri uri = Uri.parse("content://sms");
			managedCursor = managedQuery(uri, null, null, null, null);

			managedCursor.moveToFirst();
			for(int i = 0 ; i < managedCursor.getColumnCount() ; i++)
			{
				Log.d("YogSMS", "SMS: " + i + " : "+ managedCursor.getColumnName(i));
			}			
			ListAdapter adapter = new SimpleCursorAdapter(
	                 this,
	                 R.layout.smsview,
	                 managedCursor,
	                 new String[] {"person", "address", "body"},
	                 new int[] {R.id.person, R.id.address, R.id.body});

			dd = (ListView)findViewById(R.id.list);;
			dd.setAdapter(adapter);
			dd.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
					try
					{
						Context context = getApplicationContext();

						managedCursor.moveToPosition(position);
						String text = managedCursor.getString(managedCursor.getColumnIndex("address"));
						
						Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
						toast.show();

						// Android 2.0 and later
						Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(text));

						// Query the filter URI
						String[] projection = new String[]{ PhoneLookup.DISPLAY_NAME };
						Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
						
						TextView texte = (TextView)findViewById(R.id.who);
						ImageView image = (ImageView)findViewById(R.id.imagecontact);
						if(cursor.getCount() == 0)
						{
							texte.setText("");
						}
						else
						{
							cursor.moveToFirst();
							for(int i = 0 ; i < cursor.getColumnCount() ; i++)
							{
								try
								{
									Log.d("YogSMS", "Contact : " + i + " : "+ cursor.getColumnName(i) + " : " + cursor.getString(i));
								}
								catch(Exception e)
								{
								
								}
							}							
							texte.setText(cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME)));
	/*						public InputStream openPhoto(long contactId) {
							     Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId);
							     Uri photoUri = Uri.withAppendedPath(contactUri, Contacts.Photo.CONTENT_DIRECTORY);
							     Cursor cursor = getContentResolver().query(photoUri,
							          new String[] {Contacts.Photo.PHOTO}, null, null, null);
							     if (cursor == null) {
							         return null;
							     }
							     try {
							         if (cursor.moveToFirst()) {
							             byte[] data = cursor.getBlob(0);
							             if (data != null) {
							                 return new ByteArrayInputStream(data);
							             }
							         }
							     } finally {
							         cursor.close();
							     }
							     return null;
							 }
*/		
		
							Bitmap bm = Bitmap.createBitmap(100,100, null);
							bm.
							
							}
					}
					catch(Exception e)
					{
						Context context = getApplicationContext();
						Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
						toast.show();
						Log.e("YogSMS", "exception:", e);
					}					
				}				
			});
		}
		catch(Exception e)
		{
			Context context = getApplicationContext();
			Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
			toast.show();
			Log.e("YogSMS", "exception:", e);			
		}		
	}
}