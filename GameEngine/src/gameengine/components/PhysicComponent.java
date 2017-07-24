package gameengine.components;

import java.util.HashSet;
import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.util.CollisionUtils;
import gameengine.util.ICollisionListener;
import gameengine.util.IUpdateListener;
import gameengine.util.JumpHelper;
import gameengine.util.StandardCollisionResponse;
import gameengine.util.StandardGravity;
import gameengine.util.StaticCollisionResponse;

/**
 * @author Florian Albrecht
 *
 */
public class PhysicComponent extends Component {

	private Transform transform;
	private Vec3f velocity;
	private float rotvel;

	public float mxr = 15.5f;
	public float myr = 8.5f;

	public boolean screenCollision = false;

	public HashSet<Integer> OwnCollisionTypes = new HashSet<Integer>();
	public HashSet<ICollisionListener> CollisionListeners = new HashSet<ICollisionListener>();
	HashSet<IUpdateListener> UpdateListeners = new HashSet<IUpdateListener>();

	public PhysicComponent(Transform transform) {
		super(ComponentType.PHYSIC);
		this.transform = transform;
		velocity = new Vec3f();
		rotvel = 0;
	}

	public void onCollision(PhysicComponent other, Vec2f mvt) {
		for (ICollisionListener i : CollisionListeners) {
			i.onCollision(this, other, mvt);
		}
	}

	public void update() {
		for (IUpdateListener u : UpdateListeners) {
			u.update(this);
		}
	}

	public void afterUpdate() {
		for (IUpdateListener u : UpdateListeners) {
			u.afterUpdate(this);
		}
	}

	/**
	 * @return the transform
	 */
	public Transform getTransform() {
		return transform;
	}

	/**
	 * @return the velocity
	 */
	public Vec3f getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity
	 *            the velocity to set
	 */
	public void addVelocity(Vec3f nvelocity) {
		this.velocity.add(nvelocity);
	}

	/**
	 * @param vec3f
	 */
	public void setVelocity(Vec3f nvelocity) {
		this.velocity = nvelocity;
	}

	public float getRotVel() {
		return rotvel;
	}

	public void setRotVel(float rotvel) {
		this.rotvel = rotvel;
	}

	public void addCollisionListener(ICollisionListener listener) {
		CollisionListeners.add(listener);
	}

	public void removeCollisionListener(ICollisionListener listener) {
		CollisionListeners.remove(listener);
	}

	public void addUpdateListener(IUpdateListener listener) {
		UpdateListeners.add(listener);
	}

	public void removeUpdateListener(IUpdateListener listener) {
		UpdateListeners.remove(listener);
	}

	public void standardInitialise(ActionComponent action) {
		OwnCollisionTypes.add(CollisionUtils.OTHER_PLAYER);
		addCollisionListener(new StaticCollisionResponse());
		addCollisionListener(new StandardCollisionResponse());
		addCollisionListener(action);
	}

	public void addGravity(StandardGravity g) {
		addUpdateListener(g);
		addCollisionListener(g);
	}

	JumpHelper helper;
	public void allowJumping(float jumpForce, int duration, int jumpKey) {
		helper = new JumpHelper(jumpForce, duration);
		helper.JumpKey = jumpKey;
		addUpdateListener(helper);
		addCollisionListener(helper);
	}

	public void setJumpingSound(AudioComponent audio) {
		helper.JumpSound = audio;
	}

	public void makeStatic() {
		OwnCollisionTypes.add(CollisionUtils.STATIC);
	}

	public void makeStatic(ActionComponent action) {
		OwnCollisionTypes.add(CollisionUtils.STATIC);
		addCollisionListener(action);
	}

	public void setScreenCollision(boolean bol) {
		this.screenCollision = bol;
	}

	public void removeAllListeners() {
		CollisionListeners = new HashSet<ICollisionListener>();
		UpdateListeners = new HashSet<IUpdateListener>();
	}
}
