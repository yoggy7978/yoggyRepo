package local.yoggy.helloopendro;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.PointF;

public class GLPoint extends GLObject {

	private PointF mPoint;
	
	public GLPoint(PointF point) {
		super();
		mPoint = point;
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
	
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDrawArrays(GL10.GL_POINTS, 0, 1);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE);
	}

	@Override
	protected short[] onGetIndices() {
		short tmp[] = { 0, 1 };
		return tmp;
	}

	@Override
	protected float[] onGetVertices() {
		float tmp[] = { mPoint.x , mPoint.y };
		return tmp;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "POINT";
	}
}
