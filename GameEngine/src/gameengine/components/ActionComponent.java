package gameengine.components;

import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.objects.Entity;
import gameengine.util.ICollisionListener;

/**
 * @author Team
 *
 */
public abstract class ActionComponent extends Component implements ICollisionListener {

	Entity e;
	protected ActionComponent(Entity e) {
		super(ComponentType.ACTION);
		this.e = e;
	}

	public abstract void action();

	public void move(float speed, int keyLeft, int keyRight, int keyUp, int keyDown) {

		float xmov = 0.0f;
		float ymov = 0.0f;

		if (Engine.keyboard.isDown(keyRight) && keyRight != 0) {
			xmov += 1f;
		}
		if (Engine.keyboard.isDown(keyLeft) && keyLeft != 0) {
			xmov -= 1f;
		}
		if (Engine.keyboard.isDown(keyUp) && keyDown != 0) {
			ymov += 1f;
		}
		if (Engine.keyboard.isDown(keyDown) && keyUp != 0) {
			ymov -= 1f;
		}

		if (e.pysics != null) {
			e.pysics.setVelocity(Vec3f.mul(speed, Vec3f.normalize(new Vec3f(xmov, ymov, 0))));
		}
	}
}
