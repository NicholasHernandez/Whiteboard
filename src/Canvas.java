import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class Canvas extends JPanel implements MouseInputListener, ModelListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<DShape> shapes;

	ArrayList<dataTransmitter>DataTrans;
	transient	DShape selected;

	transient Point2D mouseClick;
	transient Boolean moving, resizing;
	transient Point2D resizeAnchorPoint;
	transient ShapeTableModel model;
	int shapeCount = 0;
	boolean isClient = false;
	
	public Canvas(ShapeTableModel mod) {
		super();
		setPreferredSize(new Dimension(400, 400));
		shapes = new ArrayList<DShape>();
		model = mod;
		model.setShapeList(shapes);
		this.addMouseListener(this);
		
		
		
		this.addMouseMotionListener(this);
		
		DataTrans = new ArrayList<dataTransmitter>();

	}

	public void paint(Graphics g) {
		super.paint(g);
		setOpaque(true);
		setBackground(Color.WHITE);
		g.setColor(Color.WHITE);
		//g.fillRect(0, 0, 400, 400);		
		for (DShape shape : shapes) {
			shape.draw(g);
		}

	}
	public void addDataTransmitter(dataTransmitter d1){
		for(int i =0; i <shapes.size(); i++){
			shapes.get(i).addDataTransmitter(d1);
		}
		DataTrans.add(d1);
		
	}
	
	void setClient(){
		this.removeMouseMotionListener(this);
		isClient = true;
	}
	public void addShapeClient(DShapeModel shapeModel) {
		
		if (shapeModel instanceof DRectModel) {
			shapes.add(new DRect(shapeModel));
			shapes.get(shapes.size()-1).addClientListener(model);
			shapes.get(shapes.size()-1).addClientListener(this);
		} else if (shapeModel instanceof DOvalModel) {
			shapes.add(new DOval(shapeModel));
			shapes.get(shapes.size()-1).addClientListener(model);
			shapes.get(shapes.size()-1).addClientListener(this);
		} else if (shapeModel instanceof DLineModel) {
			shapes.add(new DLine(shapeModel));
			shapes.get(shapes.size()-1).addClientListener(model);
			shapes.get(shapes.size()-1).addClientListener(this);
		} else if (shapeModel instanceof DTextModel) {
			shapes.add(new DText(shapeModel));
			shapes.get(shapes.size()-1).addClientListener(model);
			shapes.get(shapes.size()-1).addClientListener(this);
		} else {
			System.out.println("none of the above");
		}
		for(dataTransmitter d1: DataTrans){
			shapes.get(shapes.size()-1).addDataTransmitter(d1);
		}
	
		this.repaint();
	}
	public void addShape(DShapeModel shapeModel) {
		if(!isClient){
			shapeModel.setID(shapeCount);
			shapeCount++;
		}
		if (shapeModel instanceof DRectModel) {
			shapes.add(new DRect(shapeModel));
			shapes.get(shapes.size()-1).addListener(model);
			shapes.get(shapes.size()-1).addListener(this);
		} else if (shapeModel instanceof DOvalModel) {
			shapes.add(new DOval(shapeModel));
			shapes.get(shapes.size()-1).addListener(model);
			shapes.get(shapes.size()-1).addListener(this);
		} else if (shapeModel instanceof DLineModel) {
			shapes.add(new DLine(shapeModel));
			shapes.get(shapes.size()-1).addListener(model);
			shapes.get(shapes.size()-1).addListener(this);
		} else if (shapeModel instanceof DTextModel) {
			shapes.add(new DText(shapeModel));
			shapes.get(shapes.size()-1).addListener(model);
			shapes.get(shapes.size()-1).addListener(this);
		} else {
			System.out.println("none of the above");
		}
		for(dataTransmitter d1: DataTrans){
			shapes.get(shapes.size()-1).addDataTransmitter(d1);
		}
	
		this.repaint();
	}

	public DShape SelectByID(int ID){
		for(int i =0; i< shapes.size(); i++){
			if(shapes.get(i).getID() == ID){
				return shapes.get(i);
			}
		}
		return null;
	}

	
	public void selectShape(DShapeModel shape)
	{
		for(int i = shapes.size() - 1; i >= 0; i--)
		{
			if(shapes.get(i).getModel() == shape)
			{
				if (selected != null) {
					selected.Selected(false);
				}
				selected = shapes.get(i);
				shapeSelected();
				repaint();
			}
		}
	}
	

	public void selectShape(int x, int y) {
		
		Point2D pnt = new Point(x, y);
		for (int i = shapes.size() - 1; i >= 0; i--) {
			if (shapes.get(i).getBounds().contains(pnt)) {
				if (selected != null) {
					selected.Selected(false);
				}
				selected = shapes.get(i);
				shapeSelected();
				repaint();
				return;
			}
		}
		
		if (selected != null) {
			selected.Selected(false);
			
		}
		selected = null;
		repaint();
	}

	private void shapeSelected() {
		if (selected != null) {
		
			selected.Selected(true);
		}
	}
	
	private void moveSelected(int x, int y) {
	
		if (selected != null) {
			selected.translate((int)(x-mouseClick.getX()), (int)(y -mouseClick.getY()));
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	public DShapeModel[] getShapeModels(){
		DShapeModel[] models = new DShapeModel[shapes.size()];
		for(int i = 0; i <shapes.size(); i++ ){
			models[i] = (DShapeModel) shapes.get(i).getModel();
			

		}
		
		return models;
		
	}
	
	public void loadShapeModels(DShapeModel[] models){
		for(int i = 0; i <shapes.size(); i++ ){
			shapes.get(i).deleteModel();
		}
		selected = null;
		shapes.clear();
		for(int i = 0; i <models.length; i++ ){
			 this.addShape(models[i]);
			 
		}
		model.setShapeList(shapes);
	}
	
	
	public BufferedImage createBufferedImage(){
		 BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

	      Graphics graph = bi.getGraphics();
	      
	     selected.Selected(false);
	     paint(graph);
	     selected.Selected(true);
	      return bi;
	      

	}
	@Override
	public void mousePressed(MouseEvent e) {
		mouseClick = e.getPoint();
		int rectIndex =-1;
		if(selected!= null){
			rectIndex= checkSelectedRectangles(e.getPoint());
		
		}
		
		if(rectIndex==-1){
			selectShape(e.getX(), e.getY());
			moving = true;
		}else{
			int knobIndex= -1;
			resizing = true;
			if(selected instanceof DLine){
				// here youre gonna handle the anchorpoint issue with the 
				//line then we will be bool
				
				if(rectIndex ==0 ){
					knobIndex =1;
				}else if(rectIndex == 1){
					knobIndex = 0;
				}
			}else{
				
				if(rectIndex ==0 ){
					knobIndex =3;
				}else if(rectIndex == 1){
					knobIndex = 2;
				}else if(rectIndex == 2){
					knobIndex = 1;
				}else{
					knobIndex= 0 ;
				}
			}
			 resizeAnchorPoint = (Point2D) selected.getKnobs()[knobIndex].clone();
		}
		
		
	}

	
	private int checkSelectedRectangles(Point2D pnt) {
		Rectangle[] rects = selected.getKnobRectangles();
		for(int i =0; i< rects.length; i++){
			if(rects[i].contains(pnt)){
				return i;
			}
		}
		return -1;
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		if (selected != null) {
			if(moving){
				moveSelected(e.getX() , e.getY());
			}else if(resizing){
				selected.resize(e.getX(), e.getY(), (int)resizeAnchorPoint.getX(), (int)resizeAnchorPoint.getY()); 
			}
		}
		mouseClick = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		moving = false;
		resizing = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	public void changeText(String newText) 
	{
		if(selected != null && (selected instanceof DText))
		{
			DTextModel text = (DTextModel)selected.getModel();
			text.setString(newText);
		}
	}

	public void changeColor() {
		if (selected != null) {
			Color selectedColor = JColorChooser.showDialog(this, "Choose Color", Color.white);
			if (selectedColor != null) {
				selected.setColor(selectedColor);
			}
		}
	}

	@Override
	public void modelChanged(DShapeModel model) {
		repaint();
	}

	public void RemoveShape() {
		if(selected != null){
			selected.deleteModel();
			shapes.remove(shapes.indexOf(selected));
			selected = null;
		}
		
	}

	public void moveToFront() {
		if(selected != null){
			
			shapes.remove(shapes.indexOf(selected));
			shapes.add(selected);
			selected.movedToFront();
			repaint();
		}
	}
	public void moveToBack() {
		if(selected != null){
			shapes.remove(shapes.indexOf(selected));
			shapes.add(0, selected);
			selected.movedToBack();
			repaint();
		}
	}

	public void changeTextType(String newType)
	{
		if(selected != null && (selected instanceof DText))
		{
			DTextModel text = (DTextModel)selected.getModel();
			text.setType(newType);
		}
	}
	public void changeSelectedModel(DShapeModel mdl){
		if(selected!= null){
			
			selected.setColor(mdl.col);
			selected.model.setRect(mdl.getRect());

			
		}
		selected.notifyListeners();
	}
	
	
	public void modelRemoved(DShapeModel model)
	{
		
		repaint();
		
	}

	
}
