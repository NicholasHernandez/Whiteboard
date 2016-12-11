import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ShapeTableModel extends AbstractTableModel
{
	ArrayList<DShapeModel> shapes;
	private String[] colNames = {"X", "Y", "Width", "Height"};
	
	public ShapeTableModel(String[] columnNames)
	{
		shapes = new ArrayList<DShapeModel>();
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
		
	}
		
	public Object getValueAt(int row, int column){
		return shapes.get(row);
	}
}
