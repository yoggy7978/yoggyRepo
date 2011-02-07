package local.yoggy.helloopendro;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;

public class OpenDroSurfaceView extends GLSurfaceView {

	private MyGestureDetector mDetector;
	private ScaleGestureDetector mScaleDetector;
	private OpenDroRenderer mRenderer;

	public OpenDroSurfaceView(Context context) {
		super(context);

		mRenderer = new OpenDroRenderer();
		setRenderer(mRenderer);

		requestFocusFromTouch();
		setFocusableInTouchMode(true);

		//mDetector = new MyGestureDetector(this);
		mScaleDetector = new ScaleGestureDetector(context, null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// Feed info to our helper class:
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) (getHeight() - event.getY());

		Log.v(Main.TAG, "onTouchEvent event " + event.toString());
		
		mScaleDetector.onTouchEvent(event);

		if (event.getPointerCount() == 1) {
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				break;

			case MotionEvent.ACTION_UP:
				break;

			case MotionEvent.ACTION_CANCEL:
				break;

			case MotionEvent.ACTION_MOVE:
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}

class MyGestureDetector extends SimpleOnGestureListener {
    /*@Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Toast.makeText(SelectFilterActivity.this, "Left Swipe", Toast.LENGTH_SHORT`enter code here`).show();
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Toast.makeText(SelectFilterActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // nothing
        }
        return false;
    }*/
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		Log.v(Main.TAG, "onFling e1 " + e1.toString());
		Log.v(Main.TAG, "onFling e2 " + e2.toString());
		Log.v(Main.TAG, "onFling velocityX " + velocityX);
		Log.v(Main.TAG, "onFling velocityY " + velocityY);
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}
