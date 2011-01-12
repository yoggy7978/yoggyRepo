package local.yogsms;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;
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
	Cursor mConversations;
	Context mContext;
	int mLayout;
	LayoutInflater mInflater;
	YogSMSConversation[] convs;
	int count;
	
	public ContactCursorAdapter(Context context, Cursor conversations) {
		super();
		count = conversations.getCount();
		convs = new YogSMSConversation[count];
		
		mConversations = conversations;
		mContext = context;
		mLayout = R.layout.conversation_entry;
		mInflater = LayoutInflater.from(mContext);
	}
	
	public int getCount() {
		return count;
	}

	public Object getItem(int index) {
		return convs[index];
	}

	public long getItemId(int index) {
		return index;
	}
	private Cursor querySMSConversationId(int id) {
		Uri uri = Uri.parse("content://sms/conversations/" + id);
		return ((Activity)mContext).managedQuery(uri, null, null, null, null);
	}	

	public View getView(int position, View convertView, ViewGroup parent) {

		try {
			if (convertView == null) {
				convertView = mInflater.inflate(mLayout, null);
			} else {
				convertView = (View) convertView;
			}

			//Log.d(TAG, "--------- ");
			YogSMSConversation currConversation;
			if(convs[position] == null)
			{
				currConversation = new  YogSMSConversation();
				convs[position] = currConversation;
				//Log.d(TAG, "Get conversation ID");
				mConversations.moveToPosition(count-position-1);
				currConversation.id = mConversations.getInt(mConversations.getColumnIndex("thread_id"));
				//Log.d(TAG, "Conv ID " + currConversation.id);
				//Log.d(TAG, "Get conversation");
				Cursor cur = querySMSConversationId(currConversation.id);
				cur.moveToFirst();
				
				//Log.d(TAG, "Search for corresponding contact");
				currConversation.contact = new YogSMSContact();
				currConversation.contact.address = cur.getString(cur.getColumnIndex("address"));
				Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(currConversation.contact.address));
				Cursor lookup = ((Activity)mContext).managedQuery(uri, null,null,null, null);
				currConversation.contact.id = 0 ;
				if(lookup.getCount() > 0)
				{
					lookup.moveToFirst();
					currConversation.contact.id = lookup.getInt(lookup.getColumnIndex(PhoneLookup._ID));
					currConversation.contact.address = lookup.getString(lookup.getColumnIndex(PhoneLookup.NUMBER));
				}
				
				//Log.d(TAG, "Manage UI data");
				currConversation.contact.displayname = currConversation.contact.address;
				boolean hasphoto  = false ;
				currConversation.smscount= mConversations.getInt(mConversations.getColumnIndex("msg_count")) ;
				currConversation.snippet= mConversations.getString(mConversations.getColumnIndex("snippet")) ;
				//Log.d(TAG, "snip: " +currConversation.snippet);
				if(lookup.getCount() > 0)
				{
					if(lookup.getString(lookup.getColumnIndex(PhoneLookup.PHOTO_ID)) != null)
					{
						hasphoto = true;
					}
					currConversation.contact.displayname = lookup.getString(lookup.getColumnIndex(PhoneLookup.DISPLAY_NAME));
				}
				//Log.d(TAG, "Get photo");
				if(hasphoto)
				{
					currConversation.contact.bm = ContactList.loadContactPhoto(mContext.getContentResolver(), currConversation.contact.id);
				}
			}
			else
			{
				currConversation = convs[position];
			}

			
			TextView tv = (TextView) convertView.findViewById(R.id.displayname);
			tv.setText(currConversation.contact.displayname);
			TextView un = (TextView) convertView.findViewById(R.id.nbunreadSMS);
			un.setText("("+ currConversation.smscount +")");			

			TextView sn = (TextView) convertView.findViewById(R.id.snippet);
			sn.setText(currConversation.snippet);			
			
			ImageView iv = (ImageView) convertView.findViewById(R.id.photo);

			if (currConversation.contact.bm != null)
				iv.setImageBitmap(currConversation.contact.bm);
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