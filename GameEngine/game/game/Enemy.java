package game;

import java.util.HashSet;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.components.ActionComponent;
import gameengine.components.AudioComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Renderable;
import gameengine.components.Transform;
import gameengine.objects.Entity;
import gameengine.util.CollisionUtils;
import gameengine.util.StandardGravity;
import gameengine.util.StaticCollisionResponse;

public class Enemy extends ActionComponent {

	private AudioComponent explosionSound;
	private boolean died;
	private Entity entity;
	private int currentDuration = 0;
	private int dieTicks = 120;
	private PhysicComponent physics;
	private Renderable renderable;
	private Vec3f force;

	public Enemy(Entity nEntity, Vec3f start, AudioComponent explosionSound) {
		super(nEntity);
		this.entity = nEntity;
		this.explosionSound = explosionSound;

		Transform transform = new Transform(start, new Vec2f(2f, 2f), new Vec3f(0, 0, 0));
		physics = new PhysicComponent(transform);
		physics.addCollisionListener(this);
		physics.addCollisionListener(new StaticCollisionResponse());
		physics.OwnCollisionTypes.add(CollisionUtils.ENEMY);
		physics.OwnCollisionTypes.add(CollisionUtils.OTHER_PLAYER);
		physics.addGravity(new StandardGravity(4f / 90));
		renderable = new Renderable("Enemy_1.png", transform);

		entity.add(transform);
		entity.add(renderable);
		entity.add(physics);
		entity.add(this);

		force = new Vec3f(0, 4f, 0);
	}

	@Override
	public void action() {
		if (died) {
			if (dieTicks-- > 0)
				return;

			entity.delete();
			return;
		}
		
		if (currentDuration-- > 0)
			return;

		currentDuration = 10;
		physics.addVelocity(force);
	}

	@Override
	public void onCollision(PhysicComponent self, PhysicComponent other, Vec2f mvt) {
		if (died)
			return;

		if (other != null && other.OwnCollisionTypes.contains(CollisionUtils.ENEMY))
			die();
		else {
			int edge = CollisionUtils.getCollidingEdge(self);
			if (edge == CollisionUtils.EDGE_BOTTOM || edge == CollisionUtils.EDGE_LEFT)
				die();
		}
	}

	private void die() {
		died = true;
		physics.OwnCollisionTypes = new HashSet<Integer>();
		physics.removeAllListeners();
		physics.setVelocity(new Vec3f(0, -0.1f, 0));
		renderable.updateTexture("explosion.png");

		if (explosionSound != null)
			explosionSound.play(1f);
	}
}