package gameengine.util;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.components.PhysicComponent;

/**
 * @author Florian Albrecht
 *
 */
public class StaticCollisionResponse implements ICollisionListener {

	@Override
	public void onCollision(PhysicComponent physics, PhysicComponent otherPh, Vec2f mvt) {
		if (mvt == null || !otherPh.OwnCollisionTypes.contains(CollisionUtils.STATIC))
			return;

		Vec3f currentVelocity = physics.getVelocity();
		physics.setRotVel(0);
		physics.setVelocity(new Vec3f(mvt.x == 0 ? currentVelocity.x : 0, mvt.y == 0 ? currentVelocity.y : 0, 0));
	}
}
