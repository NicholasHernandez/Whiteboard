import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ShapeTableModel extends AbstractTableModel implements ModelListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<DShapeModel> shapes = new ArrayList<DShapeModel>();
	private String[] colNames = {"X", "Y", "Width", "Height"};
	
	public ShapeTableModel(){
		
	}
	
	public void setShapeList(ArrayList<DShape> shapeList)
	{
		for(int i = 0; i < shapeList.size(); i++)
		{
			shapes.add(shapeList.get(i).getModel());
		}
		fireTableDataChanged();
	}
	
	public String[] getColNames(){
		return colNames;
	}
	public void setColNames(String[] col){
		colNames = col;
	}
	public ArrayList<DShapeModel> getShapes(){
		return shapes;
	}
	public void setShapes(ArrayList<DShapeModel> shps){
		shapes = shps;
	}
	public ShapeTableModel(String[] columnNames)
	{
	}
	
	public String getColumnName(int col)
	{
		return colNames[col];
	}
	
	public int getColumnCount(){
		return 4;	// come back here yo
	}
	public int getRowCount() {
		return shapes.size();
	}
	
	public void addRow(DShapeModel newShape)
	{
		shapes.add(newShape);
		fireTableDataChanged();
	}
	
	public void deleteRow(DShapeModel shape)
	{
		shapes.remove(shape);
		fireTableDataChanged();
	}
	
	public DShapeModel getModelAt(int row)
	{
		return shapes.get(row);
	}

	public Object getValueAt(int row, int column){
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
	
	@Override
	public void modelChanged(DShapeModel model)
	{
		//System.out.println("Table rows being updated");
		fireTableRowsUpdated(shapes.indexOf(model), shapes.indexOf(model));
	}
	
	@Override
	public void modelRemoved(DShapeModel model)
	{
		deleteRow(model);
	}
}
