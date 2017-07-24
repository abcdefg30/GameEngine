package game;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.components.ActionComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Renderable;
import gameengine.components.Transform;
import gameengine.objects.Entity;
import gameengine.util.CollisionUtils;
import gameengine.util.ICollisionListener;

public class Bullet extends ActionComponent implements ICollisionListener {
	private Entity entity;
	private PhysicComponent physics;

	public Bullet(Entity nEntity, Vec3f start) {
		super(nEntity);
		this.entity = nEntity;

		Transform transform = new Transform(start, new Vec2f(0.5f, 1.5f), new Vec3f(0, 0, 270));
		physics = new PhysicComponent(transform);
		physics.addCollisionListener(this);
		physics.addVelocity(new Vec3f(4f, 0, 0));
		physics.OwnCollisionTypes.add(CollisionUtils.ENEMY);

		entity.add(transform);
		entity.add(new Renderable("Schuss.png", transform));
		entity.add(physics);
		entity.add(this);
	}

	@Override
	public void action() {
		physics.addVelocity(new Vec3f(0.05f, 0, 0));
	}

	@Override
	public void onCollision(PhysicComponent self, PhysicComponent other, Vec2f mvt) {
		entity.delete();
	}
}
