package game;

import java.util.ArrayList;
import java.util.Random;
import org.lwjgl.glfw.GLFW;
import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.components.AudioComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Renderable;
import gameengine.components.Transform;
import gameengine.objects.Entity;
import gameengine.objects.Game;
import gameengine.util.CollisionUtils;
import gameengine.util.StandardGravity;

public class TestGame implements Game {

	private ArrayList<Entity> platforms = new ArrayList<Entity>();
	private Renderable playerRender;
	private StandardGravity gravity = new StandardGravity(4f / 90);
	private Transform playerTransform;

	AudioComponent explosion;
	Player playerAction;
	boolean playerDied;
	Text text, score;
	int ticks, platformLocation = 0;
	Random random = new Random();

	public TestGame() {
	}

	@Override
	public void init() {
		Entity player = new Entity();

		playerTransform = new Transform(new Vec3f(7f, 0f, 0f), new Vec2f(2f, 2f), new Vec3f(0, 0, 270));
		PhysicComponent playerPhysics = new PhysicComponent(playerTransform);
		playerAction = new Player(player);
		AudioComponent audio = new AudioComponent("bounce.wav");
		AudioComponent shoot = new AudioComponent("laser.wav");
		explosion = new AudioComponent("blast.wav");
		AudioComponent explosion2 = new AudioComponent("explosion.wav");
		playerAction.shootSound = shoot;
		playerAction.explosionSound = explosion2;

		playerRender = new Renderable("Player.png", playerTransform);

		playerPhysics.standardInitialise(playerAction);
		playerPhysics.addGravity(gravity);
		playerPhysics.allowJumping(2.25f, 90, GLFW.GLFW_KEY_UP);
		playerPhysics.setScreenCollision(true);
		playerPhysics.setJumpingSound(audio);

		player.add(playerTransform);
		player.add(playerRender);
		player.add(playerPhysics);
		player.add(playerAction);
		player.add(audio);
		player.add(shoot);
		player.add(explosion);
		player.add(explosion2);

		for (; platformLocation < 10; platformLocation++)
			platforms.add(createPlatform(-15f + (platformLocation * 7.5f)));

		Transform tt = new Transform(new Vec3f(-3.0f, 1.5f, 0.0f), new Vec2f(2.0f, 2.0f), new Vec3f(0, 0, 0));
		text = new Text("calibri", "Use the left and right arrow keys to move.", tt, new Vec3f(0.5f, 1.0f, 0.5f));

		Transform st = new Transform(new Vec3f(-3.9f, 2f, 0.0f), new Vec2f(1f, 1f), new Vec3f(0, 0, 0));
		score = new Text("calibri", "Distance traveled: 0m", st, new Vec3f(0.5f, 0f, 1f));	
	}

	@Override
	public void update() {
		ticks++;

		if (ticks == 120)
			text.update("Use the 'up' arrow key to jump.");

		if (ticks == 240)
			text.update("Press 'space' to shoot.");

		if (ticks == 360)
			text.update("Make your way to the right...");

		if (ticks == 440) {
			text.update("... but don't fall off the map!");
			text.updateColor(new Vec3f(1.0f, 0.2f, 0.0f));
		}

		if (ticks == 520)
			text.delete();

		ArrayList<Entity> toRemove = new ArrayList<Entity>();
		for (Entity e : platforms)
			if (e.pysics.getTransform().getPosition().x < Game.camera.x - 2 * e.pysics.mxr)
				toRemove.add(e);

		for (Entity e : toRemove) {
			platforms.remove(e);
			e.delete();

			float r = 5 * random.nextFloat();
			float positionX = r - 15f + (++platformLocation * 7.5f);
			platforms.add(createPlatform(positionX));

			if (random.nextFloat() > 0.5f)
				new Enemy(new Entity(), new Vec3f(positionX, -3f, 0), explosion);
		}

		score.update("Distance traveled: " + playerAction.travelDistance + "m");
		if (playerAction.over != null && !playerDied) {
			playerDied = true;
			playerRender.updateTexture("explosion.png");
		}
	}

	private Entity createPlatform(float x) {
		Entity g = new Entity();

		Transform gTransform = new Transform(new Vec3f(x, -5f, 0f), new Vec2f(2.5f, 0.5f),
				new Vec3f(0, 0, 0));
		PhysicComponent gPhysics = new PhysicComponent(gTransform);

		gPhysics.OwnCollisionTypes.add(CollisionUtils.STATIC);

		g.add(gTransform);
		g.add(new Renderable("Grass.png", gTransform));
		g.add(gPhysics);

		return g;
	}
}
