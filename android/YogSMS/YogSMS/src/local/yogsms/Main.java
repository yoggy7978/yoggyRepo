package local.yogsms;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

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
			list = new ArrayList<String>();
			
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
			}
		}
		catch(Exception e)
		{
			Log.e("YogSMS", "Access list:", e);
		}
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list));
		
	}
}