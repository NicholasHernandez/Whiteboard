import java.awt.Graphics;
import java.awt.geom.Point2D;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

View for line; draws the line

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class DLine extends DShape
{
	DLineModel model;
	
	/**
	 Constructor for DLine
	 */
	public DLine() 
	{
		super();
	}

	/**
	 Constructor for DShape
	 
	 @param shapeModel DShapeModel model to be set to the line
	 */
	public DLine(DShapeModel shapeModel) 
	{
		super(shapeModel);
		model = (DLineModel) shapeModel;
	}

	/**
	 Draws the line with the given graphics object
	 
	 @param g Graphics used to draw the line
	 */
	public void draw(Graphics g) 
	{
		super.draw(g);
		g.drawLine((int)model.getStart().getX(), (int)model.getStart().getY(), (int)model.getEnd().getX(), (int)model.getEnd().getY());
	}
	
	/**
	 Gets the knobs of the line
	 
	 @return Point2D[] array of coordinates of the knobs
	 */
	public Point2D[] getKnobs()
	{
		Point2D[] points = new Point2D[2];
		points[0]= model.getStart();
		points[1]= model.getEnd();
		return points;
	}
}
