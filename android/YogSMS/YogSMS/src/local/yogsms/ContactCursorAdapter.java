package local.yogsms;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

class ContactCursorAdapter extends BaseAdapter {

	public static final String TAG = "YogSMS";
	Cursor mContacts;
	Context mContext;
	int mLayout;
	LayoutInflater mInflater;
		
	ArrayList<SMSContact> mSMSContacts ;
	
	public ContactCursorAdapter(Context context, ArrayList<SMSContact> smsContacts) {
		super();
		mContext = context;
		mSMSContacts = smsContacts;
		mLayout = R.layout.contact_entry;
		mInflater = LayoutInflater.from(mContext);
	}
	
	public int getCount() {
		Log.d(TAG, "getCount return " + mSMSContacts.size());
		return mSMSContacts.size();
	}

	public Object getItem(int index) {
		Log.d(TAG, "getItem" + index);
		return mSMSContacts.get(index);
	}

	public long getItemId(int index) {
		Log.d(TAG, "getItemId" + index);
		return index;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		Log.d(TAG, "getView" + position);
		try {
			if (convertView == null) {
				convertView = mInflater.inflate(mLayout, null);
			} else {
				convertView = (View) convertView;
			}
			TextView tv = (TextView) convertView.findViewById(R.id.contact_displayname);
			SMSContact contact = mSMSContacts.get(position);
			tv.setText(contact.displayName + " (" + contact.unread + "/" + contact.smscount + ")");
			ImageView iv = (ImageView) convertView.findViewById(R.id.contact_photo);
			Bitmap bm = null;
			if(contact.knownContact && contact.hasphoto)
			{
				bm = ContactList.loadContactPhoto(mContext.getContentResolver(), contact.contactID);				
			}
			if (bm != null)
				iv.setImageBitmap(bm);
		}catch (Exception e) {
			Toast toast = Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG);
			toast.show();
			Log.e(TAG, "exception:", e);
		}		
		return convertView;
	}

	/*private Cursor getContactFromId(int id) {
		try {
			Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
			return mContext.getContentResolver().query(uri, null, null, null, null);
		}catch (Exception e) {
			Toast toast = Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT);
			toast.show();
			Log.e(TAG, "exception:", e);
		}		
		return null;
		
	}		*/

}