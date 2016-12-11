import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

public class DRect extends DShape implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -631826623994135578L;

	/**
	 * 
	 */

	public DRect() {
		super();
	}
	public DRect(DShapeModel shapeModel){
		super(shapeModel);
	}
	
	public void draw(Graphics g) {
		
		g.setColor(getColor());
 		Rectangle rect = super.getModel().getRectangle();
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		super.draw(g);
		
	}
}
