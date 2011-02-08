package local.yoggy.helloopendro;

import android.app.Activity;
import android.os.Bundle;

public class Main extends Activity {
	
	public static String TAG = "HelloOpenDro";
	
	private OpenDroSurfaceView mView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	mView = new OpenDroSurfaceView(this);
   		setContentView(mView);
    }    
}