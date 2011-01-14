package local.yogsms;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

class SMSCursorAdapter extends BaseAdapter {

	public static final String TAG = "YogSMS";
	Cursor mSMS;
	Context mContext;
	int mLayout;
	LayoutInflater mInflater;
	int count;
	YogSMSConversation mConversation;
	YogSMS[] smses ;
	
	public SMSCursorAdapter(Context context, int convid) {
		super();
		
		Cursor conv = ((Activity)mContext).managedQuery(uri, null,null,null, null);
		
		
		mConversation = conv;
		
		mContext = context;
		mLayout = R.layout.sms_entry;
		mInflater = LayoutInflater.from(mContext);
		Uri uri = Uri.parse("content://sms/conversations/" + mConversation.id);
		Log.d(TAG, uri.toString());
		
		mSMS = ((Activity)mContext).managedQuery(uri, null,null,null, null);
		smses = new YogSMS[mSMS.getCount()];
	}
	
	public int getCount() {
		return mSMS.getCount();
	}

	public Object getItem(int position) {
		
		return "";
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			if (convertView == null) {
				convertView = mInflater.inflate(mLayout, null);
			} else {
				convertView = (View) convertView;
			}
			YogSMS sms = smses[position];
			if(sms == null)
			{
				sms = new YogSMS();
				sms._id = mSMS.getInt(mSMS.getColumnIndex("_id"));
				sms.address = mSMS.getString(mSMS.getColumnIndex("address"));
				sms.date = mSMS.getString(mSMS.getColumnIndex("date"));
				sms.read = mSMS.getInt(mSMS.getColumnIndex("read"));
				sms.body = mSMS.getString(mSMS.getColumnIndex("body"));
				smses[position] = sms;
			}
			
			TextView tv = (TextView) convertView.findViewById(R.id.smsdisplayname);
			tv.setText(mConversation.contact.displayname);
			TextView un = (TextView) convertView.findViewById(R.id.smsdate);
			un.setText("("+ sms.date +")");			

			TextView sn = (TextView) convertView.findViewById(R.id.smsmessage);
			sn.setText(sms.body);
			
			ImageView iv = (ImageView) convertView.findViewById(R.id.smsphoto);

			if (mConversation.contact.bm != null)
				iv.setImageBitmap(mConversation.contact.bm);
			else
			{
			  int imageResource = R.drawable.ic_contact_picture;
			  Drawable image = mContext.getResources().getDrawable(imageResource);
			  iv.setImageDrawable(image);  
			}		

		}catch (Exception e) {
			Toast toast = Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG);
			toast.show();
			Log.e(TAG, "exception:", e);
		}			
		return convertView;
	}
}


