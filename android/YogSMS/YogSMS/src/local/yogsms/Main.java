package local.yogsms;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
						Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
						cursor.moveToFirst();
						for(int i = 0 ; i < cursor.getColumnCount() ; i++)
						{
							Log.d("YogSMS", "Contact : " + i + " : "+ cursor.getColumnName(i));
						}
						TextView texte = (TextView)findViewById(R.id.who);
						texte.setText("");
						texte.setText(cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME)));
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