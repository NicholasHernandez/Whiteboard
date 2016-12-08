import java.awt.Graphics;
import java.awt.Rectangle;

public class DRect extends DShape {
	
	public DRect() {
		super();
	}
	public DRect(DShapeModel shapeModel){
		super(shapeModel);
	}
	
	public void draw(Graphics g) {
		
		g.setColor(color);
 		Rectangle rect = super.getModel().getRectangle();
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		super.draw(g);
		
	}
}
