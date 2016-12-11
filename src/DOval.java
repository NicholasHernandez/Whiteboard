import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class DOval extends DShape {
	Ellipse2D elipse;
	public DOval() {
		super();
	}
	public DOval(DShapeModel shapeModel){
		super(shapeModel);
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		Rectangle rect = super.getModel().getRectangle();
		g.fillOval(rect.x, rect.y, rect.width, rect.height);
		super.draw(g);
	}
	
}
