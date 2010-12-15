package local.yogsms;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends ListActivity {
	private Cursor managedCursor = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			   
			Log.d("YogSMS", "start listing");
			Uri uri = Uri.parse("content://sms");
			managedCursor = managedQuery(uri, null, null, null, null);

			ListAdapter adapter = new SimpleCursorAdapter(
	                 this, // Context.
	                 R.layout.smsview,  // Specify the row template to use (here, two columns bound to the two retrieved cursor rows).
	                 managedCursor,                                              // Pass in the cursor to bind to.
	                 new String[] {"person", "address", "body"},           // Array of cursor columns to bind to.
	                 new int[] {R.id.person, R.id.address, R.id.body});  // Parallel array of which template objects to bind to those columns.

			setListAdapter(adapter);
			
		}
		catch(Exception e)
		{
			Context context = getApplicationContext();
			Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
			toast.show();
			Log.e("YogSMS", "exception:", e);			
		}		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		try
		{
			Context context = getApplicationContext();
			Toast toast = Toast.makeText(context, "click", Toast.LENGTH_SHORT);
			toast.show();			
			//TextView who = (TextView)findViewById(R.id.who);
			//ContactsContract.Contacts.getLookupUri(, arg1)
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