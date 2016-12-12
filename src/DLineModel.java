import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

Model for line; stores information for a line

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class DLineModel extends DShapeModel implements Serializable
{
	private static final long serialVersionUID = -6423425722275019948L;
	Point2D start, end;

	/**
	 Constructor for DLineModel
	 */
	public DLineModel()
	{
		super();
		Rectangle rect = super.getRect();
		start = new Point(rect.x, rect.y);
		end = new Point(rect.x + rect.width, rect.y + rect.height);
	}
	
	/**
	 Constructor for DLineModel
	 
	 @param _x int top left x-coordinate for line
	 @param _y int top left y-coordinate for line
	 @param _width int width of the line
	 @param _height int height of the line
	 */
	public DLineModel(int _x, int _y, int _width, int _height) 
	{
		super(_x, _y, _width, _height);
		start = new Point(_x,_y);
		end = new Point(_x+_width,_y+_height);
	}
	
	/**
	 Resizes the line based on user input
	 
	 @param Xm int mouse point x-coordinate
	 @param Ym int mouse point y-coordinate
	 @param Xa int anchor point x-coordinate
	 @param Ya int anchor point y-coordinate
	 */
	public void resize(int Xm, int Ym, int Xa, int Ya) 
	{
		int x, y; 
		
		if(Xm > Xa)
			x = Xa;
		else
			x = Xm;
		
		if(Ym > Ya)
			y = Ya;
		else
			y = Ym;
		
		start.setLocation(Xm, Ym);
		end.setLocation(Xa, Ya);
		int width = Math.abs(Xm - Xa);
		int height = Math.abs(Ym - Ya);

		getRect().setLocation(x, y);
		getRect().setSize(width, height);
		notifyListeners();
	}
	
	/**
	 Translates the line to new point
	 
	 @param dx int x-coordinate to be translated to
	 @param dy int y-coordinate to be translated to
	 */
	public void translate(int dx, int dy) 
	{
		start.setLocation(start.getX() + dx, start.getY() + dy);
		end.setLocation(end.getX() + dx, end.getY() + dy);
		super.translate(dx, dy);
	}
	
	/**
	 Returns the starting point for the line
	 
	 @return Point2D starting point for line
	 */
	public Point2D getStart() 
	{
		return start;
	}
	
	/**
	 Sets new starting point based on parameter
	 
	 @param start Point2D point to be set to
	 */
	public void setStart(Point2D start) 
	{
		this.start = start;
	}
	
	/**
	 Returns the ending point for the line
	 
	 @return Point2D ending point for line
	 */
	public Point2D getEnd() 
	{
		return end;
	}
	
	/**
	 Sets new ending point based on parameter
	 
	 @param end Point2D point to be set to
	 */
	public void setEnd(Point2D end) 
	{
		this.end = end;
	}
}
