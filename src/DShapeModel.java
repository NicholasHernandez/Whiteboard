import java.awt.Color;
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
	Color col;
	transient ArrayList<ModelListener> listeners;
	transient ArrayList<dataTransmitter> dataTrans;
	int ID;

	public DShapeModel(){
		setRect(new Rectangle(0, 0, 10, 10));
		listeners = new ArrayList<ModelListener>();
		dataTrans = new ArrayList<dataTransmitter>();
		notifyListeners();
		col = Color.gray;
	}
	
	
	public DShapeModel(int x, int y, int width, int height) {
		setRect(new Rectangle(x, y, width, height));
		listeners = new ArrayList<ModelListener>();
		dataTrans = new ArrayList<dataTransmitter>();
		col = Color.gray;
		notifyListeners();
	}
	
	
	public void translate(int dx, int dy) {
		getRect().translate(dx, dy);
		notifyListeners();
	}
	public int getID(){
		return ID;
	}
	public void setID(int id){
		ID =  id;
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
		if(listeners ==null){
			listeners = new ArrayList<ModelListener>();
		}
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
		if(listeners == null){
			listeners = new ArrayList<ModelListener>();
		}if(dataTrans == null){
			dataTrans = new ArrayList<dataTransmitter>();
		}
		for (ModelListener listen : listeners) {
			listen.modelChanged(this);
		}
		for(dataTransmitter dtrans: dataTrans){
			dtrans.modelChanged(this);
		}
	}

	public void deleteModel() {
		for (ModelListener listen : listeners) {
			listen.modelRemoved(this);
		}
		for(dataTransmitter dtrans: dataTrans){
			dtrans.modelRemoved(this);
		}
		//notifyListeners();
		
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public Color getColor() {
		return col;
	}

	public void setColor(Color col) {
		this.col = col;
	}
	
	
	public void addDataTransmitter(dataTransmitter d1) {
		dataTrans.add(d1);
		d1.modelAdded(this);
	}


	public void movedToFront() {
		for(dataTransmitter dtrans: dataTrans){
			dtrans.movedToFront(this);
		}
	}
	public void movedToBack() {
		for(dataTransmitter dtrans: dataTrans){
			dtrans.movedToBack(this);
		}
		
	}
	public String toString(){
		return ID+ " ";
	}
}
