package gameengine.components;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import far.math.vec.Vec2f;
import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.systems.graphics.Mesh;
import gameengine.systems.graphics.Texture;

/**
 * @author Daniel
 *
 */
public class Renderable extends Component {

	private Transform transform;
	private Texture texture;
	private Mesh mesh;
	private String tex;

	public Renderable(String texture, Transform transform) {
		super(ComponentType.RENDERABLE);
		this.transform = transform;
		this.tex = texture;
		this.texture = new Texture(tex);

		mesh = Mesh.Quad();
	}

	public Renderable(String texture, Transform transform, Vec2f repeat) {
		super(ComponentType.RENDERABLE);
		this.transform = transform;
		this.tex = texture;
		this.texture = new Texture(tex);

		if (repeat != null) {
			mesh = Mesh.Quad(transform.getScale());
		} else {
			mesh = Mesh.Quad();
		}
	}

	public void updateTexture(String texture) {
		this.texture.textureID = Texture.loadTexture(texture);
	}

	public void render() {
		mesh.bind();
		texture.bind();
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		mesh.unbind();
	}

	public void destroy() {
		mesh.unbind();
		mesh.destroy();
		texture.destroy();
	}

	public Transform getTransform() {
		return transform;
	}

}
