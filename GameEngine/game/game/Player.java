package game;

import org.lwjgl.glfw.GLFW;
import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.components.ActionComponent;
import gameengine.components.AudioComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Transform;
import gameengine.objects.Entity;
import gameengine.objects.Game;
import gameengine.util.CollisionUtils;

/**
 * @author Daniel
 * @author Amir
 *
 */
public class Player extends ActionComponent {

	public AudioComponent shootSound, explosionSound;
	public int travelDistance = 0;

	private Entity player;
	boolean removed;
	Text over;
	int fireCooldown;

	public Player(Entity player) {
		super(player);
		this.player = player;
	}

	@Override
	public void action() {
		if (over != null) {
			if (!removed) {
				player.pysics.removeAllListeners();
				removed = true;
			}

			return;
		}

		if (fireCooldown-- < 0 && Engine.keyboard.isDown(GLFW.GLFW_KEY_SPACE)) {
			if (shootSound != null)
				shootSound.play(0.5f);

			fireCooldown = 40;
			new Bullet(new Entity(), Vec3f.add(player.pysics.getTransform().getPosition(), new Vec3f(2f, 0, 0)));
		}

		updateMovement();
		updateRotation();

		float currentX = player.pysics.getTransform().getPosition().x;
		Game.camera.x = currentX + player.pysics.mxr / 2;

		int newDist = (int)Math.round(currentX - 7f);
		if (newDist > travelDistance)
			travelDistance = newDist;
	}

	private void updateRotation() {
		float rotvel = 0f;

		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_Q)) {
			rotvel = 1;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_E)) {
			rotvel = -1;
		}

		player.pysics.setRotVel(rotvel);
	}

	private void updateMovement() {
		float xmov = 0;

		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_RIGHT)) {
			xmov += 2f;
		}
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_LEFT)) {
			xmov -= 2f;
		}

		player.pysics.setVelocity(new Vec3f(xmov, 0, 0));
	}

	@Override
	public void onCollision(PhysicComponent self, PhysicComponent other, Vec2f mvt) {
		if (over != null)
			return;

		if (other != null) {
			if (other.OwnCollisionTypes.contains(CollisionUtils.ENEMY))
				GameOver();

			return;
		}

		int edge = CollisionUtils.getCollidingEdge(self);
		if (edge == CollisionUtils.EDGE_BOTTOM_LEFT || edge == CollisionUtils.EDGE_BOTTOM)
			GameOver();
	}

	private void GameOver() {
		Transform tt = new Transform(new Vec3f(-1f, 0f, 0f), new Vec2f(2.0f, 2.0f), new Vec3f(0, 0, 0));
		over = new Text("calibri", "Game Over!", tt, new Vec3f(1.0f, 0.2f, 0.0f));
		player.pysics.setVelocity(new Vec3f(0, 0, 0));

		if (explosionSound != null)
			explosionSound.play(0.5f);
	}
}