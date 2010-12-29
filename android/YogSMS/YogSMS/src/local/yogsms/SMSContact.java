package local.yogsms;

class SMSContact
{
	public boolean knownContact ;
	public boolean hasphoto;
	public String displayName;
	public String telephone;
	public int contactID;
	public int unread;
	public int smscount;
	SMSContact()
	{
		contactID = -1;
		unread = 0;
	}

}