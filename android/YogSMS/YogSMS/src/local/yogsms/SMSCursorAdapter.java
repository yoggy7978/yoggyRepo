package local.yogsms;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.format.DateFormat;
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
	Uri mUri ;
	
	public SMSCursorAdapter(Context context, YogSMSConversation conversation) {
		super();

		try
		{
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
			
			mConversation = conversation;
			mUri = Uri.parse("content://sms/conversations/" + mConversation.id);
			Log.d(TAG, mUri.toString());
			
			
			mSMS = ((Activity)mContext).managedQuery(mUri, null,null,null, null);
			
			Log.d(TAG,"sms count: " +  mSMS.getCount());
			smses = new YogSMS[mSMS.getCount()];
		} catch (Exception e) {
			Toast toast = Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG);
			toast.show();
			Log.e(TAG, "exception:", e);
		}
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

			YogSMS sms = smses[position];
			if(sms == null)
			{
				sms = new YogSMS();
				mSMS.moveToPosition(position);
				sms._id = mSMS.getInt(mSMS.getColumnIndex("_id"));
				sms.address = mSMS.getString(mSMS.getColumnIndex("address"));
				sms.date = mSMS.getLong(mSMS.getColumnIndex("date"));
				
				Date dada = new Date(sms.date);
				Date today = new Date();
				if((today.getYear() == dada.getYear()) && (today.getMonth() == dada.getMonth()) && (today.getDay() == dada.getDay()))
				{
					sms.displayDate = DateFormat.format("hh:mm", dada).toString();		
				}
				else
				{
					sms.displayDate = DateFormat.format("dd/MM/yyyy", dada).toString();
				}
				
				sms.read = mSMS.getInt(mSMS.getColumnIndex("read"));
				sms.body = mSMS.getString(mSMS.getColumnIndex("body"));
				sms.type = mSMS.getInt(mSMS.getColumnIndex("type"));
				smses[position] = sms;
				
				//ContactList.dumpContentProvider(mContext, mUri);
				
			}
			
			if(sms.type == 2)
			{
				
				mLayout = R.layout.sms_entry_me;
			}
			else 
			{
				mLayout = R.layout.sms_entry;
			}			
			
			convertView = mInflater.inflate(mLayout, null);
						
			if(sms.type == 2)
			{
				TextView tv = (TextView) convertView.findViewById(R.id.sms_me_displayname);
				tv.setText("Me");
				
				TextView un = (TextView) convertView.findViewById(R.id.sms_me_date);
				un.setText(sms.displayDate);

				TextView sn = (TextView) convertView.findViewById(R.id.sms_me_message);
				sn.setText(sms.body);
				
				ImageView iv = (ImageView) convertView.findViewById(R.id.sms_me_photo);

				Bitmap me = ContactList.loadContactPhoto(mContext.getContentResolver(), 0);
				
				if (me != null)
					iv.setImageBitmap(me);
				else
				{
				  int imageResource = R.drawable.ic_contact_picture;
				  Drawable image = mContext.getResources().getDrawable(imageResource);
				  iv.setImageDrawable(image);  
				}					
			}
			else 
			{
				TextView tv = (TextView) convertView.findViewById(R.id.smsdisplayname);
				tv.setText(mConversation.contact.displayname);
				
				TextView un = (TextView) convertView.findViewById(R.id.smsdate);
				un.setText(sms.displayDate);			

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
			}
		}catch (Exception e) {
			Toast toast = Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG);
			toast.show();
			Log.e(TAG, "exception:", e);
		}			
		return convertView;
	}
}


