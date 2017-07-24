/**
 * 
 */
package game;

import org.lwjgl.glfw.GLFW;
import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.Engine;
import gameengine.components.ActionComponent;
import gameengine.components.AudioComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Renderable;
import gameengine.components.Transform;
import gameengine.objects.Component;
import gameengine.objects.Entity;
import gameengine.objects.Game;
import gameengine.util.CollisionUtils;
import gameengine.util.StandardGravity;

/**
 *
 */
public class TestGame implements Game {

	private Transform playerTransform;
	private Renderable playerRender;
	private StandardGravity gravity = new StandardGravity(4f / 90);

	public TestGame() {
	}

	@Override
	public void init() {
		Entity player = new Entity();

		playerTransform = new Transform(new Vec3f(7f, 0f, 0f), new Vec2f(2f, 2f), new Vec3f(0, 0, 270));
		PhysicComponent playerPhysics = new PhysicComponent(playerTransform);
		ActionComponent playerAction = new Player(player);
		AudioComponent audio = new AudioComponent("bounce.wav");

		playerRender = new Renderable("Player.png", playerTransform);

		playerPhysics.standardInitialise(playerAction);
		playerPhysics.addGravity(gravity);
		playerPhysics.allowJumping(2.25f, 90, GLFW.GLFW_KEY_UP);

		player.add(playerTransform);
		player.add(playerRender);
		player.add(playerPhysics);
		player.add(playerAction);
		player.add(audio);

		Entity e = new Entity();

		Transform player2Transform = new Transform(new Vec3f(-8f, 0.0f, 0f), new Vec2f(1.0f, 1.0f), new Vec3f(0, 0, 0));
		PhysicComponent player2Physics = new PhysicComponent(player2Transform);
		ActionComponent player2Action = new Player2(e);
		player2Physics.standardInitialise(player2Action);

		e.add(player2Transform);
		e.add(new Renderable("Grass.png", player2Transform));
		e.add(player2Physics);
		e.add(player2Action);

		Entity g1 = new Entity();

		Transform g1Transform = new Transform(new Vec3f(0, -0f, 0f), new Vec2f(2.5f, 2.5f), new Vec3f(0, 0, 0));
		PhysicComponent g1Physics = new PhysicComponent(g1Transform);

		g1Physics.OwnCollisionTypes.add(CollisionUtils.STATIC);

		g1.add(g1Transform);
		g1.add(new Renderable("Grass.png", g1Transform));
		g1.add(g1Physics);

		for (int i = 0; i < 5; i++) {
			Entity g = new Entity();

			Transform gTransform = new Transform(new Vec3f(-15f + (i * 7.5f), -5f, 0f), new Vec2f(2.5f, 0.5f),
					new Vec3f(0, 0, 0));
			PhysicComponent gPhysics = new PhysicComponent(gTransform);

			gPhysics.OwnCollisionTypes.add(CollisionUtils.STATIC);

			g.add(gTransform);
			g.add(new Renderable("Grass.png", gTransform));
			g.add(gPhysics);
		}

		Transform tt = new Transform(new Vec3f(-3.0f, 1.5f, 0.0f), new Vec2f(2.0f, 2.0f), new Vec3f(0, 0, 0));
		text = new Text("calibri", "Test Text gg xD !", tt, new Vec3f(0.5f, 1.0f, 0.5f));
	}

	Text text;
	int i = 0;
	int k = 0;

	@Override
	public void update() {
		i++;
		if (Engine.keyboard.isDown(GLFW.GLFW_KEY_K)) {
			camera.add(new Vec3f(0.0f, 0.1f, 0));
		}

		if (i == 180) {
			text.update("2. Text :)");
			text.updateColor(new Vec3f(0.2f, 1.0f, 0.0f));

			i = 0;
		}
	}
}
