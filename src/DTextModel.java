import java.io.Serializable;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

Model for rectangle; stores information for a text

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class DTextModel extends DShapeModel implements Serializable
{
	private static final long serialVersionUID = -1938078507710805967L;
	private String content;
	private String type;
	
	/**
	 Constructor for DTextModel
	 */
	public DTextModel()
	{
		super();
		content = "";
		type = "";
	}
	
	/**
	 Constructor for DTextModel
	 
	 @param _x int top left x-coordinate for text
	 @param _y int top left y-coordinate for text
	 @param _width int width of the text
	 @param _height int height of the text
	 @param textContent String content of the text
	 @param fontType String font family of the text
	 */
	public DTextModel(int _x, int _y, int _width, int _height, String textContent, String fontType) 
	{
		super(_x, _y, _width, _height);
		content = textContent;
		type = fontType;
	}
	
	/**
	 Sets the text of the string to the given string
	 
	 @param s String string to be changed to
	 */
	public void setString(String s) 
	{ 
		content = s; 
		notifyListeners();
	}
	
	/**
	 Sets the font type of the string based on the given font name
	 
	 @param t String type to be changed to
	 */
	public void setType(String t)
	{
		type = t;
		notifyListeners();
	}
	
	/**
	 Returns the content of the string
	 
	 @return String content of string
	 */
	public String getText() { return content; }
	
	/**
	 Returns the font type of the string
	 
	 @return String name of font type of string
	 */
	public String getType() { return type; } 

}
