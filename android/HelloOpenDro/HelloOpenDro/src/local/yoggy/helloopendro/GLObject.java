package local.yoggy.helloopendro;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import javax.microedition.khronos.opengles.GL10;

public abstract class GLObject {
	// Our vertices.
	private float[] vertices;

	// The order we like to connect them.
	private short[] indices;

	// Our vertex buffer.
	protected FloatBuffer vertexBuffer;

	// Our index buffer.
	protected ShortBuffer indexBuffer;

	public GLObject() {

	}

	private ReentrantLock mutex = new ReentrantLock();
	private ArrayList<GLObject> list = new ArrayList<GLObject>();
	
	public void add(GLObject object)
	{
		mutex.lock();
		object.load();
		list.add(object);
		mutex.unlock();			
	}		
	
	final public void load() {
		vertices = onGetVertices();
		indices = onGetIndices();

		if(vertices != null)
		{
			// a float is 4 bytes, therefore we multiply the number if
			// vertices with 4.
			ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer = vbb.asFloatBuffer();
			vertexBuffer.put(vertices);
			vertexBuffer.position(0);
		}

		if(indices != null)
		{
			// short is 2 bytes, therefore we multiply the number if
			// vertices with 2.
			ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
			ibb.order(ByteOrder.nativeOrder());
			indexBuffer = ibb.asShortBuffer();
			indexBuffer.put(indices);
			indexBuffer.position(0);
		}
	}

	final public void draw(GL10 gl) {
		mutex.lock();
		for(GLObject obj : list)
		{
			obj.draw(gl);
		}		
		mutex.unlock();		
		onDraw(gl);
	}

	protected void onDraw(GL10 gl) { };

	protected float[] onGetVertices() {return null;}

	protected short[] onGetIndices() { return null;}
	
	final protected short[] getIndices(){
		return indices;
	}
	
	final protected float[] getVertices(){
		return vertices;
	}	
}
