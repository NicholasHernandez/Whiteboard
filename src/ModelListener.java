import java.io.Serializable;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

Interface to listen for shape change notifications. The modelChanged() notification includes a pointer to the model that changed. 
There are no details about what the exact change was.

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public interface ModelListener extends Serializable
{
	/**
	 Called when model is changed
	 
	 @param model DShapeModel model that is changed
	 */
	public void modelChanged(DShapeModel model);
	
	/**
	 Called when model is removed
	 
	 @param model DShapeModel model that is removed
	 */
	public void modelRemoved(DShapeModel model);
}