import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

View for rectangle; draws the rectangle

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class DRect extends DShape implements Serializable 
{
	private static final long serialVersionUID = -631826623994135578L;
	
	/**
	 Constructor for DRect
	 */
	public DRect() 
	{
		super();
	}
	
	/**
	 Constructor for DRect
	 
	 @param shapeModel DShapeModel model to be set to the rectangle
	 */
	public DRect(DShapeModel shapeModel)
	{
		super(shapeModel);
	}
	
	/**
	 Draws the rectangle with the given graphics object
	 
	 @param g Graphics used to draw the rectangle
	 */
	public void draw(Graphics g) 
	{	
		g.setColor(getColor());
 		Rectangle rect = super.getModel().getRectangle();
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		super.draw(g);
	}
}
