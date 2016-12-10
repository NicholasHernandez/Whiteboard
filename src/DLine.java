import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class DLine extends DShape{
	DLineModel model;
	public DLine() {
		super();
	}

	public DLine(DShapeModel shapeModel) {
		super(shapeModel);
		model = (DLineModel) shapeModel;
	}

	public void draw(Graphics g) {
		super.draw(g);
		g.drawLine((int)model.getStart().getX(), (int)model.getStart().getY(), (int)model.getEnd().getX(),(int)model.getEnd().getY() );
		
		
		
	}
	public Point2D[] getKnobs(){
		Rectangle rect = model.getRectangle();
		Point2D[] points = new Point2D[2];
		points[0]= model.getStart();
		points[1]= model.getEnd();
		return points;
	}

}
