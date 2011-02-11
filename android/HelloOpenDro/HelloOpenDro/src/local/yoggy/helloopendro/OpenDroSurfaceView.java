package local.yoggy.helloopendro;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;

public class OpenDroSurfaceView extends GLSurfaceView implements OnGestureListener, OnDoubleTapListener{

	private OpenDroRenderer mRenderer;
	private GestureDetector mDetector;

	public OpenDroSurfaceView(Context context) {
		super(context);
		
		mDetector = new GestureDetector(this);
		
		mRenderer = new OpenDroRenderer();
		setRenderer(mRenderer);		
		
		//mRenderer.add(new GLSquare(3.0f,3.0f, 1.0f));
		
		/*mRenderer.add(new GLLine(new PointF(0.0f, 0.0f), new PointF(10.0f, 0.0f)));*/
		mRenderer.add(new GLLine(new PointF(0.0f, 0.0f), new PointF(0.0f, 10.0f)));
		
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// Feed info to our helper class:
//		int action = event.getAction();
	//	int x = (int) event.getX();
		//int y = (int) (getHeight() - event.getY());

		//Log.v(Main.TAG, "onTouchEvent event " + event.toString());

		mDetector.onTouchEvent(event);
		
		/*if (event.getPointerCount() == 1) {
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				Log.v(Main.TAG, "action MotionEvent.ACTION_DOWN");
				break;

			case MotionEvent.ACTION_UP:
				Log.v(Main.TAG, "action MotionEvent.ACTION_UP");
				break;

			case MotionEvent.ACTION_CANCEL:
				Log.v(Main.TAG, "action MotionEvent.ACTION_CANCEL");
				break;

			case MotionEvent.ACTION_MOVE:
				Log.v(Main.TAG, "action MotionEvent.ACTION_MOVE");
				break;
			}
		}*/
		return true;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		//Log.v(Main.TAG, "onFling e1 " + e1.toString());
		//Log.v(Main.TAG, "onFling e2 " + e2.toString());
		//Log.v(Main.TAG, "onFling velocityX " + velocityX);
		//Log.v(Main.TAG, "onFling velocityY " + velocityY);
		return false;
	}

	public boolean onDown(MotionEvent e) {
		Log.v(Main.TAG, "onDown");
		lastPoint = new PointF(e.getX(), e.getY());
		return false;
	}

	public void onLongPress(MotionEvent e) {
		//Log.v(Main.TAG, "onLongPress");
		
		//GLObject tmp = new GLSquare(e.getX()/this.getWidth(), e.getY()/this.getHeight(), 1.0f);
	}

	PointF lastPoint = null;
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		//Log.v(Main.TAG, "onScroll e1 " + e1.toString());
		//Log.v(Main.TAG, "onScroll e2 " + e2.toString());
		//Log.v(Main.TAG, "onScroll distanceX " + distanceX);
		//Log.v(Main.TAG, "onScroll distanceY " + distanceY);
		//mRenderer.add(new GLLine());
		/*if(lastPoint == null)
		{
			lastPoint = new PointF(e1.getX(), e1.getY());
			mRenderer.add(new GLLine(lastPoint,lastPoint));
		}
		else
		{*/
			PointF newpoint = new PointF(e2.getX(), e2.getY());
			mRenderer.add(new GLLine(lastPoint, newpoint));
			lastPoint = newpoint;
		//}
		return false;
	}

	public void onShowPress(MotionEvent e) {
		//Log.v(Main.TAG, "onShowPress");		
	}

	public boolean onSingleTapUp(MotionEvent e) {
		//Log.v(Main.TAG, "onSingleTapUp");
		return false;
	}

	public boolean onDoubleTap(MotionEvent e) {
		//Log.v(Main.TAG, "onDoubleTap");
		return false;
	}

	public boolean onDoubleTapEvent(MotionEvent e) {
		//Log.v(Main.TAG, "onDoubleTapEvent");
		return false;
	}

	public boolean onSingleTapConfirmed(MotionEvent e) {
		//Log.v(Main.TAG, "onSingleTapConfirmed");
		return false;
	}
}