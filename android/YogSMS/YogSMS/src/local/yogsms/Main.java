package local.yogsms;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class Main extends ListActivity {
	private Cursor managedCursor = null;
	private List<String> list = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try
		{
			
			Log.d("YogSMS", "start listing");
			Uri uri = Uri.parse("content://sms");
			managedCursor = managedQuery(uri, null, null, null, null);
			/*	list = new ArrayList<String>();
			
			if(managedCursor.moveToFirst())
			{
				int count = managedCursor.getColumnCount();
				for(int i = 0 ; i < count; i++)
				{
					Log.d("YogSMS", managedCursor.getColumnName(i) + " : " + managedCursor.getString(i));
				}				
				do
				{
					String sms = managedCursor.getString(managedCursor.getColumnIndex("address")) +
					" - " + managedCursor.getString(managedCursor.getColumnIndex("body"));
			
					list.add(sms);
				} while (managedCursor.moveToNext());
			}*/
			   ListAdapter adapter = new SimpleCursorAdapter(
		                 this, // Context.
		                 android.R.layout.two_line_list_item,  // Specify the row template to use (here, two columns bound to the two retrieved cursor rows).
		                 managedCursor,                                              // Pass in the cursor to bind to.
		                 new String[] {"address", "body"},           // Array of cursor columns to bind to.
		                 new int[] {android.R.id.text1, android.R.id.text2});  // Parallel array of which template objects to bind to those columns.

		         // Bind to our new adapter.
		         setListAdapter(adapter);
			
		}
		catch(Exception e)
		{
			Log.e("YogSMS", "Access list:", e);
		}		
	}
}