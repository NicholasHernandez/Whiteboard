import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class DLineModel extends DShapeModel implements Serializable{
	
	
	
	/*
	 * 
	 */
	private static final long serialVersionUID = -6423425722275019948L;
	Point2D start, end;
	public DLineModel(){
		super();
		Rectangle rect = super.getRect();
		start = new Point(rect.x, rect.y);
		end = new Point(rect.x+rect.width, rect.y+rect.height);
	}
	public DLineModel(int _x, int _y, int _width, int _height) {
		super(_x, _y, _width, _height);
		start = new Point(_x,_y);
		end = new Point(_x+_width,_y+_height);
	}
	
	
	public void resize(int Xm, int Ym, int Xa, int Ya) {
		int x,y; 
		if(Xm>Xa){
			x=Xa;
		}else{
			x = Xm;
		}
		if(Ym>Ya){
			y=Ya;
		}else{
			y = Ym;
		}
		start.setLocation(Xm, Ym);
		end.setLocation(Xa, Ya);
		int width = (Math.abs(Xm - Xa));
		int height = Math.abs(Ym - Ya);

		getRect().setLocation(x, y);
		getRect().setSize(width, height);
		notifyListeners();
	}
	public void translate(int dx, int dy) {
		start.setLocation(start.getX()+dx, start.getY()+dy);
		end.setLocation(end.getX()+dx, end.getY()+dy);
		
		super.translate(dx, dy);
	}
	public Point2D getStart() {
		return start;
	}
	public void setStart(Point2D start) {
		this.start = start;
	}
	public Point2D getEnd() {
		return end;
	}
	public void setEnd(Point2D end) {
		this.end = end;
	}
	

}
