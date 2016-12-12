import java.awt.Graphics;
import java.awt.Rectangle;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

View for oval; draws the oval

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class DOval extends DShape 
{
	/**
	 Constructor for DRect
	 */
	public DOval() 
	{
		super();
	}
	
	/**
	 Constructor for DOval
	 
	 @param shapeModel DShapeModel model to be set to the oval
	 */
	public DOval(DShapeModel shapeModel)
	{
		super(shapeModel);
	}
	
	/**
	 Draws the oval with the given graphics object
	 
	 @param g Graphics used to draw the oval
	 */
	public void draw(Graphics g) 
	{
		g.setColor(getColor());
		Rectangle rect = super.getModel().getRectangle();
		g.fillOval(rect.x, rect.y, rect.width, rect.height);
		super.draw(g);
	}
}
