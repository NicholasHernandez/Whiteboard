import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

Model for shapes table; stores information for a list of shapes

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class ShapeTableModel extends AbstractTableModel implements ModelListener
{
	private static final long serialVersionUID = 1L;
	ArrayList<DShapeModel> shapes = new ArrayList<DShapeModel>();
	private String[] colNames = {"X", "Y", "Width", "Height"};
	
	/**
	 Constructor for ShapeTableModel
	 */
	public ShapeTableModel() { }
	
	/**
	 Constructor for ShapeTableModel
	 
	 @param columnNames String[] column names to be set to
	 */
	public ShapeTableModel(String[] columnNames) { }
	
	/**
	 Sets the list of DShapeModel based on the given ArrayList of shapes; tells table to update itself
	 
	 @param shapeList ArrayList<DShape> list of shapes to correspond to the table
	 */
	public void setShapeList(ArrayList<DShape> shapeList)
	{
		shapes.clear();
		for(int i = 0; i < shapeList.size(); i++)
		{
			shapes.add(shapeList.get(i).getModel());
		}
		fireTableDataChanged();
	}
	
	/**
	 Returns list of column names
	 
	 @return String[] list of column names
	 */
	public String[] getColNames()
	{
		return colNames;
	}
	
	/**
	 Sets list of column names based on given column names
	 
	 @param col String[] list of column names
	 */
	public void setColNames(String[] col)
	{
		colNames = col;
	}
	
	/**
	 Returns list of DShapeModel
	 
	 @return ArrayList<DShapeModel> list of DShapeModel to be returned
	 */
	public ArrayList<DShapeModel> getShapes()
	{
		return shapes;
	}
	
	/**
	 Sets the list of DShapeModels to given list
	 
	 @param ArrayList<DShapeModel> list of DShapeModel to be set
	 */
	public void setShapes(ArrayList<DShapeModel> shps)
	{
		shapes = shps;
	}
	
	/**
	 Returns a column name based on given column
	 
	 @param col int column of interest
	 @return String column name of col
	 */
	public String getColumnName(int col)
	{
		return colNames[col];
	}
	
	/**
	 Returns a count of the total columns
	 
	 @return int number of columns
	 */
	public int getColumnCount()
	{
		return colNames.length;
	}
	
	/**
	 Returns a count of the total rows
	 
	 @return int number of rows
	 */
	public int getRowCount() 
	{
		return shapes.size();
	}
	
	/**
	 Adds a new shape to the table, tells table to update
	 
	 @param newShape DShapeModel new shape to be added
	 */
	public void addRow(DShapeModel newShape)
	{
		shapes.add(newShape);
		fireTableDataChanged();
	}
	
	/**
	 Delete a new shape from the table, tells table to update
	 
	 @param newShape DShapeModel shape to be deleted
	 */
	public void deleteRow(DShapeModel shape)
	{
		shapes.remove(shape);
		fireTableDataChanged();
	}
	
	/**
	 Gets the model at a certain row
	 
	 @param row int row of interest
	 @return DShapeModel model at the given row
	 */
	public DShapeModel getModelAt(int row)
	{
		return shapes.get(row);
	}

	/**
	 Gets the value at a given row and column
	 
	 @param row int row of interest
	 @param column int column of interest
	 @return Object value at given row and column
	 */
	public Object getValueAt(int row, int column)
	{
		DShapeModel shape = shapes.get(row);
		switch(column)
		{
		case 0: 
			return shape.getRect().getX();
		case 1:
			return shape.getRect().getY();
		case 2:
			return shape.getRect().getWidth();
		case 3:
			return shape.getRect().getHeight();
		default:
			return null;
		}
	}
	
	/**
	 Called when the model is changed, updates row
	 
	 @param model DShapeModel model being changed
	 */
	@Override
	public void modelChanged(DShapeModel model)
	{
		//System.out.println("Table rows being updated");
		fireTableRowsUpdated(shapes.indexOf(model), shapes.indexOf(model));
	}
	
	/**
	 Called when the model is removed, updates row
	 
	 @param model DShapeModel model being removed
	 */
	@Override
	public void modelRemoved(DShapeModel model)
	{
		deleteRow(model);
	}
}
