package local.yoggy.helloopendro;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class OpenDroRenderer implements Renderer {

	private ReentrantLock mutex = new ReentrantLock();
	private ArrayList<GLObject> list = new ArrayList<GLObject>();
	
	public void add(GLObject object)
	{
		mutex.lock();
		object.load();
		list.add(object);
		mutex.unlock();			
	}	
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		
		//gl.glOrthof(left, right, bottom, top, zNear, zFar)
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);  
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

		gl.glEnable (GL10.GL_LINE_SMOOTH);
		gl.glEnable (GL10.GL_BLEND);
		gl.glBlendFunc (GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint (GL10.GL_LINE_SMOOTH_HINT, GL10.GL_DONT_CARE);		
		
	}
	
	public void onDrawFrame(GL10 gl) {
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Translates 4 units into the screen.
	//	gl.glTranslatef(0, 0, -8); // OpenGL docs
	
		// Draw our square.
		mutex.lock();
		for(GLObject obj : list)
		{
			obj.draw(gl);
		}		
		mutex.unlock();
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		//GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
		gl.glOrthof(0.0f,width,height,0.0f,-1.0f,1.0f);	
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();
	}	
}
