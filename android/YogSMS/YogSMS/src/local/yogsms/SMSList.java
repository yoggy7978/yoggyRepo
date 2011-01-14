package local.yogsms;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

public class SMSList extends Activity {

	public static final String TAG = "YogSMS";
	private ListView mSMSList;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conversation_list);

		mSMSList = (ListView) findViewById(R.id.contactList);
		int convID = savedInstanceState.getInt("conversationID");
		SMSCursorAdapter adapter = new SMSCursorAdapter(
				this, 
				convID);
		mSMSList.setAdapter(adapter);		
	}

}
