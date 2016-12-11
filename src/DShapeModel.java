import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

public class DShapeModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9106410012841468338L;
	/**
	 * 
	 */
	private Rectangle rect;
	
	ArrayList<ModelListener> listeners;

	public DShapeModel(){
		setRect(new Rectangle(0, 0, 10, 10));
		listeners = new ArrayList<ModelListener>();
		notifyListeners();
	}
	
	public DShapeModel(int x, int y, int width, int height) {
		setRect(new Rectangle(x, y, width, height));
		listeners = new ArrayList<ModelListener>();
		notifyListeners();
	}

	public void translate(int dx, int dy) {
		getRect().translate(dx, dy);
		notifyListeners();
	}

	public Rectangle getRectangle() {
		return (Rectangle) getRect().clone();
	}
	public ArrayList<ModelListener> getListeners(){
		return listeners;
	}
	public void setListeners(ArrayList<ModelListener> listen){
		listeners = listen;
	}
	public void setRectangle(Rectangle r) {
		setRect(r);
		notifyListeners();
	}

	public void addListener(ModelListener listen) {
		listeners.add(listen);
		notifyListeners();
	}

	public void removeListener(ModelListener listen) {
		int i = listeners.indexOf(listen);
		if (i != -1) {
			listeners.remove(i);
		}
	}

	
	// mouse and anchor points
	public void resize(int Xm, int Ym, int Xa, int Ya) {
		int x = Math.min(Xm, Xa);
		int y = Math.min(Ym, Ya);
		int width = (Math.abs(Xm - Xa));
		int height = Math.abs(Ym - Ya);

		getRect().setLocation(x, y);
		getRect().setSize(width, height);
		notifyListeners();
	}

	public void notifyListeners() {
		for (ModelListener listen : listeners) {
			listen.modelChanged(this);
		}
	}

	public void deleteModel() {
		notifyListeners();
		// for(ModelListener listen: listeners){
		// listen.deleteModel()
		// }
		// use this to prepare all the models to be deleted
		// do all the shit you would do in deleting the model
		// basically focus on notifying the listeners that depend
		// on this object that it was deleted
		// ljke the screen thing where
		// you see the coordinates and the width and the height
		// unattach that and notify it
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
}
