import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

Superclass view for shapes; draws the shapes

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/
public class DShape 
{
	DShapeModel model;
	boolean select;
	
	/**
	 Constructor for DShape
	 */
	public DShape() 
	{
		setModel(new DShapeModel(0, 0, 0, 0));
	}
	
	/**
	 Constructor for DShape
	 
	 @param mdl DShapeModel model corresponding to shape
	 */
	public DShape(DShapeModel mdl)
	{
		model = mdl;
	}
	
	/**
	 Sets whether or not shape is selected
	 
	 @param s boolean true if selected
	 */
	public void Selected(boolean s)
	{	
		select = s;
	}
	
	/**
	 Calls model to translate the shape to a different coordinate
	 
	 @param dx int new x coordinate to be moved to
	 @param dy int new y coordinate to be moved to
	 */
	public void translate(int dx, int dy)
	{
		model.translate(dx, dy);
	}
	
	/**
	 Adds given listener to the model of the shape
	 
	 @param listen ModelListener listener to be added 
	 */
	public void addListener(ModelListener listen)
	{
		model.addListener(listen);
	}
	
	/**
	 Removes given listener to the model of the shape
	 
	 @param listen ModelListener listener to be removed
	 */
	public void removeListener(ModelListener listen)
	{
		model.removeListener(listen);
	}
	
	/**
	 Sets given model as the new model to the shape
	 
	 @param mdl DShapeModel model to be set to
	 */
	protected void setModel(DShapeModel mdl)
	{
		model = mdl;
	}
	
	/**
	 Returns the model of the shape
	 
	 @return DShapeModel model of the shape
	 */
	public DShapeModel getModel()
	{
		return model;
	}
	
	/**
	 Returns the Color object of the shape
	 
	 @return Color color of the shape
	 */
	public Color getColor()
	{
		return model.getColor();
	}
	
	/**
	 Adds DataTransmitter object to the model
	 
	 @param d1 dataTransmitter dataTransmitter to be set to
	 */
	public void addDataTransmitter(dataTransmitter d1)
	{
		model.addDataTransmitter(d1);
	}
	
	/**
	 Sets given Color object to the shape
	 
	 @param c Color to be changed to
	 */
	public void setColor(Color c)
	{
		model.setColor(c);
		model.notifyListeners();
	}
	
	/**
	 Draws the shape with given Graphics object
	 
	 @param g Graphics to be used to draw shape
	 */
	public void draw(Graphics g)
	{
		g.setColor(this.getColor());
		
		if(select)
		{
			g.setColor(Color.black);
			Rectangle[] rects = getKnobRectangles();
			for(int i = 0; i < rects.length; i++)
			{
				g.fillRect((int)(rects[i].getX()), (int)(rects[i].getY()), (int)(rects[i].getWidth()), (int)(rects[i].getHeight()));
			}
		}
	}
	
	/**
	 Resizes the shape based on given coordinates
	 
	 @param Xm int mouse point, x-coordinate 
	 @param Ym int mouse point, y-coordinate
	 @param Xa int anchor point, x-coordinate
	 @param Ya int anchor point, y-coordinate
	 */
	public void resize(int Xm, int Ym, int Xa, int Ya)
	{
		model.resize(Xm, Ym, Xa, Ya);
	}
	
	/**
	 Gets the rectangles that encapsulate the knobs
	 
	 @return Rectangle[] array of rectangles that contain the knobs
	 */
	public Rectangle[] getKnobRectangles()
	{
		Point2D[] knobs = getKnobs();
		Rectangle[] rects = new Rectangle[knobs.length];
		for(int i= 0; i < knobs.length; i++){
		
			rects[i] = new Rectangle((int)(knobs[i].getX() - 4), (int)(knobs[i].getY() - 4), 9, 9);
		}
		return rects;
	}
	
	/**
	 Gets the rectangle which houses the shape
	 
	 @return Rectangle that houses the shape
	 */
	public Rectangle getBounds()
	{
		return model.getRectangle();
	}
	
	/**
	 Deletes the model of the shape
	 */
	public void deleteModel()
	{
		model.deleteModel();
		model = null;
	}
	
	/**
	 Returns the knobs for the shape
	 
	 @return Point2D[] array of the coordinates of each knob
	 */
	public Point2D[] getKnobs()
	{
		Rectangle rect = model.getRectangle();
		Point2D[] points = new Point2D[4];
		points[0]= rect.getLocation();
		points[1]= new Point((int)(rect.getX() + rect.getWidth()), (int)(rect.getY()));
		points[2]= new Point((int)(rect.getX()), (int)(rect.getY() + rect.getHeight()));
		points[3]= new Point((int)(rect.getX() + rect.getWidth()),(int)(rect.getY() + rect.getHeight()));
		return points;
	}
	
	/**
	 Informs the model that the shape was moved to the front
	 */
	public void movedToFront() 
	{
		model.movedToFront();
	}
	
	/**
	 Informs the model that the shape was moved to the back
	 */
	public void movedToBack() 
	{
		model.movedToBack();
	}
}
