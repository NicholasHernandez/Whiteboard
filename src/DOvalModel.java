import java.io.Serializable;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

Model for oval; stores information for a oval

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class DOvalModel extends DShapeModel implements Serializable
{
	private static final long serialVersionUID = -1903829379662382323L;
	
	/**
	 Constructor for DOvalModel
	 */
	public DOvalModel()
	{
		super();
	}
	
	/**
	 Constructor for DOvalModel
	 
	 @param _x int top left x-coordinate for oval
	 @param _y int top left y-coordinate for oval
	 @param _width int width of the oval
	 @param _height int height of the oval
	 */
	public DOvalModel(int _x, int _y, int _width, int _height) 
	{
		super(_x, _y, _width, _height);
	}
}
