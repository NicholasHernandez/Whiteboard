import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class DShapeModel{
	private Rectangle rect;
	ArrayList<ModelListener> listeners;
	public DShapeModel(int x, int y, int width, int height){
		rect = new Rectangle(x, y, width, height);
		listeners = new ArrayList<ModelListener>();
	}
	
	public void translate(int dx, int dy){
		rect.translate(dx, dy);
		notifyListeners();
	}
	public Rectangle getRectangle(){
		return (Rectangle) rect.clone();
	}
	
	public void setRectangle(Rectangle r){
		rect = r;
		notifyListeners();

	}

	public void addListener(ModelListener listen){
		listeners.add(listen);
	}
	public void removeListener(ModelListener listen){
		int i = listeners.indexOf(listen);
		if(i !=-1){
			listeners.remove(i);
		}
	}
	//mouse and anchor points
	public void resize(int Xm, int Ym, int Xa, int Ya){
		int x= Math.min(Xm, Xa);
		int y = Math.min(Ym, Ya);
		int width= (Math.abs(Xm- Xa));
		int height = Math.abs(Ym-Ya);
		
		rect.setLocation(x, y);
		rect.setSize(width, height);
		notifyListeners();
	}
	public void notifyListeners() {
		for(ModelListener listen: listeners){
			listen.modelChanged(this);
		}
	}
	
	public void deleteModel(){
		notifyListeners();
		//for(ModelListener listen: listeners){
		//listen.deleteModel()
		//}
		//use this to prepare all the models to be deleted
		// do all the shit you would do in deleting the model
		// basically focus on notifying the listeners that depend 
		// on this object that it was deleted
		// ljke the screen thing where 
		// you see the cordinates and the width and the height
		//unattach that and notify it
		
	}
}
