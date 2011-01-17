package local.yogsms;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class SMSList extends Activity {

	public static final String TAG = "YogSMS";
	private ListView mSMSList;	
	private YogSMSConversation mConversation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try
		{
			setContentView(R.layout.conversation_list);
	
			mConversation = ContactList.getLastSelectedYogConversation();
			
			mSMSList = (ListView) findViewById(R.id.contactList);
			SMSCursorAdapter adapter = new SMSCursorAdapter(
					this, 
					mConversation);
			mSMSList.setAdapter(adapter);
		} catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
			toast.show();
			Log.e(TAG, "exception:", e);
		}
	}
}
