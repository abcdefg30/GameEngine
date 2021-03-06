/**
 * 
 */
package gameengine.components;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.objects.Component;
import gameengine.objects.ComponentType;

/**
 * @author Florian Albrecht, Daniel
 *
 */
public class Transform extends Component {

	public static final Vec3f FACE_RIGHT = new Vec3f(0, 0, 0);
	public static final Vec3f FACE_LFET = new Vec3f(0, 180, 0);
	public static final Vec3f FACE_UP = new Vec3f(0, 0, 0);
	public static final Vec3f FACE_DOWN = new Vec3f(180, 0, 0);

	private Vec3f position;
	private Vec2f scale;
	private Vec3f rot;

	public Transform(Vec3f position, Vec2f scale, Vec3f rot) {
		super(ComponentType.TRANSFORM);
		this.position = position;
		this.scale = scale;
		this.rot = rot;
	}

	// some comfortable constructors

	public Transform(Vec3f position) {
		super(ComponentType.TRANSFORM);
		this.position = position;
		this.scale = new Vec2f(1, 1);
		this.rot = new Vec3f(0, 0, 0);
	}

	public Transform(Vec3f position, Vec2f scale) {
		super(ComponentType.TRANSFORM);
		this.position = position;
		this.scale = scale;
		this.rot = new Vec3f(0, 0, 0);
	}

	public Vec3f getPosition() {
		return position;
	}

	public void add(Vec3f vec) {
		position.add(vec);
	}

	/**
	 * @return the scale
	 */
	public Vec2f getScale() {
		return scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(Vec2f scale) {
		this.scale = scale;
	}

	/**
	 * @return the rot
	 */
	public Vec3f getRot() {
		return rot;
	}

	/**
	 * @param rot
	 *            the rot to set
	 */
	public void setRot(Vec3f rot) {
		this.rot = rot;
	}

}
