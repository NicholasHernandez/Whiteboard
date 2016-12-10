import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class DShape 
{
	DShapeModel model;
	Color color;
	boolean select;
	public DShape() {
		color = Color.GRAY;
		setModel(new DShapeModel(0, 0, 0, 0));
	}
	public void Selected(boolean s){	
		select = s;
	}
	public void translate(int dx, int dy){
		model.translate(dx, dy);
	}
	public DShape(DShapeModel mdl){
		color = Color.GRAY;
		model = mdl;
	}
	public void addListener(ModelListener listen){
		model.addListener(listen);
	}
	public void removeListener(ModelListener listen){
		model.removeListener(listen);
	}
	protected void setModel(DShapeModel mdl){
		model = mdl;
	}
	public DShapeModel getModel(){
		return model;
	}
	public Color getColor(){
		return color;
	}
	public void setColor(Color c){
		model.notifyListeners();
		color = c;
	}
	public void draw(Graphics g ){
		g.setColor(color);
		
		if(select){
			g.setColor(color.black);
			Rectangle[] rects = getKnobRectangles();
			for(int i = 0; i<rects.length;i++){
				g.fillRect((int)(rects[i].getX()), (int)(rects[i].getY()),(int)(rects[i].getWidth()),(int)(rects[i].getHeight()));
			}
		}
	}
	
	public void resize(int Xm, int Ym, int Xa, int Ya){
		model.resize(Xm, Ym, Xa, Ya);
	}
	public Rectangle[] getKnobRectangles(){
		Point2D[] knobs = getKnobs();
		Rectangle[] rects = new Rectangle[knobs.length];
		for(int i= 0; i<knobs.length;i++){
		
			rects[i] = new Rectangle((int)(knobs[i].getX()-4),(int)(knobs[i].getY()-4), 9, 9);
		}
		return rects;
	}
	public Rectangle getBounds(){
		return model.getRectangle();
	}
	public void deleteModel(){
		model.deleteModel();
		model = null;
	
	}
	public Point2D[] getKnobs(){
		Rectangle rect = model.getRectangle();
		Point2D[] points = new Point2D[4];
		points[0]= rect.getLocation();
		points[1]= new Point((int)(rect.getX() + rect.getWidth()),(int)( rect.getY()));
		points[2]= new Point((int)(rect.getX()),(int)( rect.getY()+rect.getHeight()));
		points[3]= new Point((int)(rect.getX() + rect.getWidth()),(int)(rect.getY()+rect.getHeight()));
		return points;
	}
	
	

}
