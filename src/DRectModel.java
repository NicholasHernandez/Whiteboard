import java.io.Serializable;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

Model for rectangle; stores information for a rectangle

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class DRectModel extends DShapeModel implements Serializable
{
	private static final long serialVersionUID = 257380007310295710L;
	
	/**
	 Constructor for DRectModel
	 */
	public DRectModel()
	{
		super();
	}
	
	/**
	 Constructor for DOvalModel
	 
	 @param _x int top left x-coordinate for rectangle
	 @param _y int top left y-coordinate for rectangle
	 @param _width int width of the rectangle
	 @param _height int height of the rectangle
	 */
	public DRectModel(int _x, int _y, int _width, int _height)
	{
		super(_x, _y, _width, _height);
	}
}
