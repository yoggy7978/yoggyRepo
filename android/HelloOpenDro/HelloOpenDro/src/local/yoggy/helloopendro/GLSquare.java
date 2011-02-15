package local.yoggy.helloopendro;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.PointF;

public class GLSquare extends GLObject {

	private float mSize;
	private PointF mPosition;
	private float mPositionz;

	public GLSquare(PointF position, float size) {
		super();
		mPosition = position;
		mPositionz = 0.0f;
		mSize = size;
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

	@Override
	protected void onDraw(GL10 gl) {
		
		/*gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
		
		// Counter-clockwise winding.
		gl.glFrontFace(GL10.GL_CCW);
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE);
		// What faces to remove with the face culling.
		gl.glCullFace(GL10.GL_BACK);
	
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDrawArrays(GL10.GL_T, 0, 2);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE);*/		
		
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // 0xFFFFFFFF
		// Counter-clockwise winding.
		gl.glFrontFace(GL10.GL_CCW); 
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE); 
		// What faces to remove with the face culling.
		gl.glCullFace(GL10.GL_BACK); 
		// Enabled the vertices buffer for writing and to be used during rendering.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// Specifies the location and data format of an array of vertex coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, getIndices().length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); 
		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "SQUARE";
	}
	
}