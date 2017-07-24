/**
 * 
 */
package gameengine.util;

import far.math.vec.Vec3f;
import gameengine.components.PhysicComponent;
import gameengine.objects.Game;

public class CollisionUtils {
	public static final int NO_COLLISION = 0;
	public static final int EDGE_LEFT = 1;
	public static final int EDGE_RIGHT = 2;
	public static final int EDGE_TOP = 3;
	public static final int EDGE_BOTTOM = 4;
	public static final int EDGE_TOP_LEFT = 5;
	public static final int EDGE_TOP_RIGHT = 6;
	public static final int EDGE_BOTTOM_LEFT = 7;
	public static final int EDGE_BOTTOM_RIGHT = 8;
	public static final int OTHER_PLAYER = 9;
	public static final int STATIC = 10;
	public static final int ENEMY = 11;

	public static boolean CanCollide(PhysicComponent a, PhysicComponent b) {
		if (a.OwnCollisionTypes.contains(NO_COLLISION) || b.OwnCollisionTypes.contains(NO_COLLISION))
			return false;

		// TODO
		if (a.OwnCollisionTypes.contains(OTHER_PLAYER) && b.OwnCollisionTypes.contains(STATIC))
			return true;

		for (int type : a.OwnCollisionTypes)
			if (b.OwnCollisionTypes.contains(type))
				return true;

		return false;
	}

	public static boolean CanCollide(PhysicComponent a, int collisionType) {
		if (a.OwnCollisionTypes.contains(NO_COLLISION) || collisionType == NO_COLLISION)
			return false;

		return a.OwnCollisionTypes.contains(collisionType);
	}

	public static int getCollidingEdge(PhysicComponent ph) {
		return getCollidingEdgeWithVelocity(ph, new Vec3f(0, 0, 0));
	}

	public static int willCollideWithEdge(PhysicComponent ph) {
		Vec3f velocity = Vec3f.div(ph.getVelocity(), 10);
		if (velocity.x == 0 && velocity.y == 0)
			return CollisionUtils.NO_COLLISION;

		return getCollidingEdgeWithVelocity(ph, velocity);
	}

	public static int getCollidingEdgeWithVelocity(PhysicComponent ph, Vec3f vel) {
		float mxr = ph.mxr;
		float myr = ph.myr;
		float velX = vel.x;
		float velY = vel.y;
		float posX = ph.getTransform().getPosition().x + velX;
		float posY = ph.getTransform().getPosition().y + velY;
		int type = CollisionUtils.NO_COLLISION;

		if (posX >= Game.camera.x + mxr && velX >= 0) {
			type = CollisionUtils.EDGE_RIGHT;
		} else if (posX <= Game.camera.x - mxr && velX <= 0) {
			type = CollisionUtils.EDGE_LEFT;
		}

		if (posY >= Game.camera.y + myr && velY >= 0) {
			if (type == CollisionUtils.NO_COLLISION)
				return CollisionUtils.EDGE_TOP;

			return type == CollisionUtils.EDGE_RIGHT ? CollisionUtils.EDGE_TOP_RIGHT : CollisionUtils.EDGE_TOP_LEFT;
		} else if (posY <= Game.camera.y - myr && velY <= 0) {
			if (type == CollisionUtils.NO_COLLISION)
				return CollisionUtils.EDGE_BOTTOM;

			return type == CollisionUtils.EDGE_RIGHT ? CollisionUtils.EDGE_BOTTOM_RIGHT
					: CollisionUtils.EDGE_BOTTOM_LEFT;
		}

		return type;
	}

	// Returns the velocity you should be using we hitting the map edge
	public static Vec3f getVelocityFor(int collisionType, PhysicComponent ph) {
		Vec3f mov = new Vec3f(ph.getVelocity());

		switch (collisionType) {
		case CollisionUtils.EDGE_RIGHT:
			mov.x = 0;
			break;
		case CollisionUtils.EDGE_LEFT:
			mov.x = 0;
			break;
		case CollisionUtils.EDGE_TOP:
			mov.y = 0;
			break;
		case CollisionUtils.EDGE_BOTTOM:
			mov.y = 0;
			break;
		case CollisionUtils.EDGE_TOP_LEFT:
			mov.x = 0;
			mov.y = 0;
			break;
		case CollisionUtils.EDGE_TOP_RIGHT:
			mov.x = 0;
			mov.y = 0;
			break;
		case CollisionUtils.EDGE_BOTTOM_LEFT:
			mov.x = 0;
			mov.y = 0;
			break;
		case CollisionUtils.EDGE_BOTTOM_RIGHT:
			mov.x = 0;
			mov.y = 0;
			break;
		case CollisionUtils.NO_COLLISION:
		default:
			// Nothing happens for now
		}

		return mov;
	}
}
