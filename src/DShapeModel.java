import java.awt.Color;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

Model for shapes; stores information for a shape

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class DShapeModel implements Serializable
{
	private static final long serialVersionUID = 9106410012841468338L;
	private Rectangle rect;
	Color col;
	transient ArrayList<ModelListener> listeners;
	ArrayList<ModelListener> clientListeners;
	transient ArrayList<dataTransmitter> dataTrans;
	int ID;

	/**
	 Constructor for DShapeModel
	 */
	public DShapeModel()
	{
		setRect(new Rectangle(0, 0, 10, 10));
		listeners = new ArrayList<ModelListener>();
		dataTrans = new ArrayList<dataTransmitter>();
		clientListeners = new ArrayList<ModelListener>();
		notifyListeners();
		col = Color.gray;
	}
	
	/**
	 Constructor for DShapeModel
	 
	 @param x int top left x-coordinate for shape
	 @param y int top left y-coordinate for shape
	 @param width int width of the shape
	 @param height int height of the shape
	 */
	public DShapeModel(int x, int y, int width, int height) 
	{
		setRect(new Rectangle(x, y, width, height));
		listeners = new ArrayList<ModelListener>();
		dataTrans = new ArrayList<dataTransmitter>();
		clientListeners = new ArrayList<ModelListener>();

		col = Color.gray;
		notifyListeners();
	}
	
	/**
	 Translates the shape to the given coordinates
	 
	 @param dx int x-coordinate to be moved to
	 @param dy int y-coordinate to be moved to
	 */
	public void translate(int dx, int dy) 
	{
		getRect().translate(dx, dy);
		notifyListeners();
	}
	
	/**
	 Gets the ID of the Shape object
	 
	 @return int ID of the Shape
	 */
	public int getID()
	{
		return ID;
	}
	
	/**
	 Sets the ID of the Shape object
	 
	 @param id int ID to be set to
	 */
	public void setID(int id)
	{
		ID =  id;
	}
	
	/**
	 Gets the rectangle housing the shape
	 
	 @return Rectangle rectangle housing the shape
	 */
	public Rectangle getRectangle() 
	{
		return (Rectangle) getRect().clone();
	}
	
	/**
	 Gets the listeners for this shape
	 
	 @return ArrayList<ModelListener> list of listeners
	 */
	public ArrayList<ModelListener> getListeners()
	{
		return listeners;
	}
	
	/**
	 Sets the listeners to the given ArrayList for this shape
	 
	 @param listen ArrayList<ModelListener> list of listeners to be set to
	 */
	public void setListeners(ArrayList<ModelListener> listen)
	{
		listeners = listen;
	}
	
	/**
	 Sets the rectangle of this shape to the given rectangle
	 
	 @param r Rectangle rectangle to be set to
	 */
	public void setRectangle(Rectangle r)
	{
		setRect(r);
		notifyListeners();
	}
	
	public void addClientListener(ModelListener listen){
		clientListeners.add(listen);
	}
	

	/**
	 Adds the given listener to its list of listeners
	 
	 @param listen ModelListener listener to be added 
	 */
	public void addListener(ModelListener listen) 
	{
		if(listeners == null)
		{
			listeners = new ArrayList<ModelListener>();
		}
		listeners.add(listen);
		notifyListeners();
	}

	/**
	 Removes the given listener from its list of listeners
	 
	 @param listen ModelListener listener to be removed
	 */
	public void removeListener(ModelListener listen)
	{
		int i = listeners.indexOf(listen);
		if (i != -1) 
		{
			listeners.remove(i);
		}
	}

	/**
	 Resizes the shape based on the user input
	 
	 @param Xm int mouse point x-coordinate
	 @param Ym int mouse point y-coordinate
	 @param Xa int anchor point x-coordinate
	 @param Ya int anchor point y-coordinate
	 */
	public void resize(int Xm, int Ym, int Xa, int Ya) 
	{
		int x = Math.min(Xm, Xa);
		int y = Math.min(Ym, Ya);
		int width = Math.abs(Xm - Xa);
		int height = Math.abs(Ym - Ya);

		getRect().setLocation(x, y);
		getRect().setSize(width, height);
		notifyListeners();
	}

	/**
	 Notifies listeners of the shape
	 */
	public void notifyListeners() 
	{
		if(listeners == null)
		{
			listeners = new ArrayList<ModelListener>();
		}
		if(dataTrans == null)
		{
			dataTrans = new ArrayList<dataTransmitter>();
		}
		for (ModelListener listen : listeners) 
		{
			listen.modelChanged(this);
		}

		for(dataTransmitter dtrans : dataTrans)
		{
			dtrans.modelChanged(this);
			
		}
		for(ModelListener listen: clientListeners){
			listen.modelChanged(this);
		}
	}
	public void clearListeners(){
		listeners.clear();
	}
	
	

	/**
	 Deletes the model by removing its listeners and datatransmitters
	 */
	public void deleteModel() 
	{
		for (ModelListener listen : listeners) 
		{
			listen.modelRemoved(this);
		}
		for(dataTransmitter dtrans: dataTrans)
		{
			dtrans.modelRemoved(this);
		}
		//notifyListeners();
	}

	/**
	 Returns the rectangle of the shape
	 
	 @return Rectangle that houses the Shape
	 */
	public Rectangle getRect() 
	{
		return rect;
	}

	/**
	 Sets the rectangle of the shape to the given rectangle
	 
	 @param rect Rectangle to be set to
	 */
	public void setRect(Rectangle rect) 
	{
		this.rect = rect;
	}

	/**
	 Returns the Color object of the shape
	 
	 @return Color that describes the Shape
	 */
	public Color getColor() 
	{
		return col;
	}

	/**
	 Sets the Color of the shape to the given Color
	 
	 @param col Color to be set to
	 */
	public void setColor(Color col) 
	{
		this.col = col;
	}
	
	/**
	 Adds the given data transmitter to its list of data transmitters and adds the model to the data transmitter
	 
	 @param d1 dataTransmitter data transmitter being added
	 */
	public void addDataTransmitter(dataTransmitter d1) 
	{
		dataTrans.add(d1);
		d1.modelAdded(this);
	}

	/**
	 Moves the shape to the front 
	 */
	public void movedToFront() 
	{
		for(dataTransmitter dtrans: dataTrans)
		{
			dtrans.movedToFront(this);
		}
	}
	
	/**
	 Moves the shape to the back 
	 */
	public void movedToBack()
	{
		for(dataTransmitter dtrans: dataTrans)
		{
			dtrans.movedToBack(this);
		}
	}
	
	/**
	 Returns the string of the object
	 
	  @return String that corresponds to ID
	 */
	public String toString()
	{
		return ID + " ";
	}


	public ArrayList<ModelListener> getClientListeners() {
		return clientListeners;
	}


	public void setClientListeners(ArrayList<ModelListener> clientListeners) {
		this.clientListeners = clientListeners;
	}
}
