package gameengine.util;

import org.lwjgl.glfw.GLFW;
import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.components.AudioComponent;
import gameengine.components.PhysicComponent;

public class JumpHelper implements IUpdateListener, ICollisionListener {

	public AudioComponent JumpSound;
	public int JumpKey = GLFW.GLFW_KEY_UP;

	boolean isJumping;
	int maxDuration, currentDuration;
	Vec3f force;

	public JumpHelper(float jumpForce, int duration) {
		force = new Vec3f(0, jumpForce, 0);
		maxDuration = duration;
	}

	public void setJumpForce(float newForce) {
		force = new Vec3f(0, newForce, 0);
	}

	public void addJumpForce(float addForce) {
		force = new Vec3f(0, force.y + addForce, 0);
	}

	public void setDuration(int newDuration) {
		maxDuration = currentDuration = newDuration;
	}

	public void addDuration(int addDuration) {
		maxDuration = currentDuration = maxDuration + addDuration;
	}

	@Override
	public void update(PhysicComponent self) {
		if (!isJumping) {
			if (currentDuration-- > 0 || !Engine.keyboard.isDown(JumpKey))
				return;

			isJumping = true;
			currentDuration = maxDuration;
			if (JumpSound != null)
				JumpSound.play(0.3f);
		}

		currentDuration--;
		self.addVelocity(force);
	}

	@Override
	public void afterUpdate(PhysicComponent self) { }

	@Override
	public void onCollision(PhysicComponent self, PhysicComponent other, Vec2f mvt) {
		if (mvt != null && mvt.x != 0)
			return;

		int edge = CollisionUtils.getCollidingEdge(self);
		if (edge == CollisionUtils.EDGE_LEFT || edge == CollisionUtils.EDGE_RIGHT) {
			isJumping = currentDuration > 0;
			return;
		}

		isJumping = false;
	}
}
