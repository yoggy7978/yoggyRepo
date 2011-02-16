package local.yoggy.helloopendro;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.opengl.GLUtils;

public class GLImage extends GLObject {

	
	private float mSize;
	private PointF mPosition;
	private float mPositionz;
	private Bitmap mImage;

	public GLImage(PointF position, float size, Bitmap image) {
		super();
		mPosition = position;
		mPositionz = 0.0f;
		mSize = size;
		mImage = image;
	}
	
	@Override
	protected void onDraw(GL10 gl) {
		
		
		//First setup the integer array to hold texture numbers which OpenGL generates
		int texture[] = new int[1];

		//Generate and bind to the texture (gl is my GL10 object)
		gl.glGenTextures(1, texture, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[0]);


		
		//Setup the texture co-ordinates

		//FloatBuffer texcoords = FloatBuffer.wrap(this.getVertices()); 
		gl.glTexCoordPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		//Bitmap wood = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.ic_contact_picture);

		//Setup optional texture parameters
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		//Set the texture image
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mImage, 0); 

		//Enable texture related flags (Important)
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glDrawElements(GL10.GL_TEXTURE, getIndices().length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY); 
		gl.glDisable(GL10.GL_TEXTURE_2D);		
	}

	@Override
	protected short[] onGetIndices() {
		short tmp[] = { 0, 1, 2, 0, 2, 3 };
		return tmp;
	}

	@Override
	protected float[] onGetVertices() {
		float mSize2 = mSize/2;
		float tmp[] = { mPosition.x-mSize2, mPosition.y-mSize2, mPositionz, // 0, Top Left
						mPosition.x-mSize2, mPosition.y+mSize2, mPositionz, // 1, Bottom Left
						mPosition.x+mSize2, mPosition.y+mSize2, mPositionz, // 2, Bottom Right
						mPosition.x+mSize2, mPosition.y-mSize2, mPositionz, // 3, Top Right
		};
		return tmp;
	}

}
