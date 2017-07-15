/**
 * 
 */
package game;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.components.PhysicComponent;
import gameengine.components.Renderable;
import gameengine.components.Transform;
import gameengine.objects.Entity;
import gameengine.objects.Game;

/**
 *
 */
public class TestGame implements Game {

	Transform Player;
	Player p1;
	PhysicComponent PlPh;

	Transform Player2;
	Player2 p2;
	PhysicComponent PlPh2;

	public TestGame() {

	}

	@Override
	public void init() {
		Player = new Transform(new Vec3f(13f, 5f, 0f), new Vec2f(2f, 2f), new Vec3f(0, 0, 0));
		PlPh = new PhysicComponent(Player, 1);
		p1 = new Player(PlPh);

		Player2 = new Transform(new Vec3f(5f, 5f, 0f), new Vec2f(2f, 2f), new Vec3f(0, 0, 0));

		PlPh2 = new PhysicComponent(Player2, 1);
		p2 = new Player2(PlPh2);

		Entity e3 = new Entity();
		e3.add(new Renderable("Player.png", Player));
		e3.add(PlPh);
		e3.add(p1);

		Entity e = new Entity();
		e.add(new Renderable("grass.png", Player2));
		e.add(PlPh2);
		e.add(p2);

		Transform tt = new Transform(new Vec3f(-2.0f, 0.8f, 0.0f), new Vec2f(1.0f, 1.0f), new Vec3f(0, 0, 0));
		Text text = new Text("arial", "Test Text gg xD !", tt);
	}

	@Override
	public void update() {

	}

}
