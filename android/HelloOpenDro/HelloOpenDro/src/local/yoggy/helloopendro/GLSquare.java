package local.yoggy.helloopendro;

import javax.microedition.khronos.opengles.GL10;

public class GLSquare extends GLObject {

	private float mSize;
	private float mPositionx;
	private float mPositiony;

	public GLSquare(float positionx, float positiony, float size) {
		super();
		mPositionx = positionx;
		mPositiony = positiony;
		mSize = size;
	}

	@Override
	protected short[] onGetIndices() {
		short tmp[] = { 0, 1, 2, 0, 2, 3 };
		return tmp;
	}

	@Override
	protected float[] onGetVertices() {
		float tmp[] = { 0.0f, mSize, 0.0f, // 0, Top Left
						0.0f, 0.0f, 0.0f, // 1, Bottom Left
						mSize, 0.0f, 0.0f, // 2, Bottom Right
						mSize, mSize, 0.0f, // 3, Top Right
		};
		return tmp;
	}

	@Override
	protected void onDraw(GL10 gl) {
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
}