package gameengine.components;

import gameengine.objects.Component;
import gameengine.objects.ComponentType;
import gameengine.objects.Entity;
import gameengine.systems.graphics.Mesh;
import gameengine.systems.graphics.gui.FontManager;

public class GUIText extends Component {

	private static final FontManager fontManager = new FontManager();

	public String text;
	public String fontName;
	private Transform transform;

	public GUIText(String text, String fontName, Transform transform, Entity entity) {
		super(ComponentType.GUITEXT);
		this.text = text;
		this.fontName = fontName;
		this.transform = transform;

		Mesh m = fontManager.loadFont(this);
		System.out.println("font: " + fontName + ".png, " + transform.getPosition());

		entity.add(transform);
		entity.add(new GUIRenderable(fontName + ".png", transform, m));

	}

	public Transform getTransform() {
		return transform;
	}

}
