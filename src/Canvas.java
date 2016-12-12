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

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

Canvas that contains all the shapes 

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class Canvas extends JPanel implements MouseInputListener, ModelListener
{
	private static final long serialVersionUID = 1L;
	ArrayList<DShape> shapes;
	ArrayList<dataTransmitter> DataTrans;
	transient DShape selected;
	transient Point2D mouseClick;
	transient Boolean moving, resizing;
	transient Point2D resizeAnchorPoint;
	transient ShapeTableModel model;
	int shapeCount = 0;
	boolean isClient = false;
	
	/**
	 Constructor for Canvas
	 
	 @param mod ShapeTableModel model of shapes to be updated
	 */
	public Canvas(ShapeTableModel mod) 
	{
		super();
		setPreferredSize(new Dimension(400, 400));
		shapes = new ArrayList<DShape>();
		model = mod;
		model.setShapeList(shapes);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		DataTrans = new ArrayList<dataTransmitter>();
	}

	/**
	 Paints the canvas portion (to be able to differentiate between the controls and the canvas)
	 
	 @param g Graphics to be used to paint the canvas
	 */
	public void paint(Graphics g) 
	{
		super.paint(g);
		setOpaque(true);
		setBackground(Color.WHITE);
		g.setColor(Color.WHITE);
		for (DShape shape : shapes) 
		{
			shape.draw(g);
		}
	}
	
	/**
	 Adds the given data transmitter to the list of data transmitters
	 
	 @param d1 dataTransmitter to be added
	 */
	public void addDataTransmitter(dataTransmitter d1)
	{
		for(int i =0; i < shapes.size(); i++)
		{
			shapes.get(i).addDataTransmitter(d1);
		}
		DataTrans.add(d1);
	}
	
	/**
	 Sets the Canvas object to a client, not allowing any actions to change the canvas
	 */
	void setClient()
	{
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
	
	/**
	 Adds the shape to the canvas, and adds the appropriate listeners
	 
	 @param shapeModel DShapeModel to be added
	 */
	public void addShape(DShapeModel shapeModel) 
	{
		if(!isClient)
		{
			shapeModel.setID(shapeCount);
			shapeCount++;
		}
		if (shapeModel instanceof DRectModel) 
		{
			shapes.add(new DRect(shapeModel));
			shapes.get(shapes.size()-1).addListener(model);
			shapes.get(shapes.size()-1).addListener(this);
		} 
		else if (shapeModel instanceof DOvalModel) 
		{
			shapes.add(new DOval(shapeModel));
			shapes.get(shapes.size()-1).addListener(model);
			shapes.get(shapes.size()-1).addListener(this);
		} 
		else if (shapeModel instanceof DLineModel) 
		{
			shapes.add(new DLine(shapeModel));
			shapes.get(shapes.size()-1).addListener(model);
			shapes.get(shapes.size()-1).addListener(this);
		} 
		else if (shapeModel instanceof DTextModel) 
		{
			shapes.add(new DText(shapeModel));
			shapes.get(shapes.size()-1).addListener(model);
			shapes.get(shapes.size()-1).addListener(this);
		} 
		else 
		{
			System.out.println("none of the above");
		}
		
		for(dataTransmitter d1: DataTrans)
		{
			shapes.get(shapes.size() - 1).addDataTransmitter(d1);
		}
		this.repaint();
	}

	/**
	 Selects the Shape based on the given ID
	 
	 @param ID int ID of shape to be found
	 @return DShape shape given by the ID
	 */
	public DShape SelectByID(int ID)
	{
		for(int i = 0; i < shapes.size(); i++){
			if(shapes.get(i).getID() == ID)
			{
				return shapes.get(i);
			}
		}
		return null;
	}

	/**
	 Changes the selected Shape based on the given shape
	 
	 @param shape DShapeModel shape to be found
	 */
	public void selectShape(DShapeModel shape)
	{
		for(int i = shapes.size() - 1; i >= 0; i--)
		{
			if(shapes.get(i).getModel() == shape)
			{
				if (selected != null) 
				{
					selected.Selected(false);
				}
				selected = shapes.get(i);
				shapeSelected();
				repaint();
			}
		}
	}
	
	/**
	 Changes the selected Shape based on the given coordinates
	 
	 @param x int x-coordinate to find the shape in
	 @param y int y-coordinate to find the shape in
	 */
	public void selectShape(int x, int y) 
	{
		Point2D pnt = new Point(x, y);
		
		for (int i = shapes.size() - 1; i >= 0; i--) 
		{
			if (shapes.get(i).getBounds().contains(pnt))
			{
				if (selected != null) 
				{
					selected.Selected(false);
				}
				selected = shapes.get(i);
				shapeSelected();
				repaint();
				return;
			}
		}
		
		if (selected != null) 
		{
			selected.Selected(false);
		}
		selected = null;
		repaint();
	}

	/**
	 Ensures that the selected shape knows that it is selected
	 */
	private void shapeSelected() {
		if (selected != null) 
		{
			selected.Selected(true);
		}
	}
	
	/**
	 Moves the selected shape based on the x- and y-coordinates
	 
	 @param x int x-coordinate to be moved to
	 @param y int y-coordinate to be moved to
	 */
	private void moveSelected(int x, int y) 
	{
		if (selected != null)
		{
			selected.translate((int)(x-mouseClick.getX()), (int)(y -mouseClick.getY()));
		}
	}
	
	/**
	 Called when the mouse is clicked
	 
	 @param e MouseEvent that triggered the mouse click
	 */
	@Override
	public void mouseClicked(MouseEvent e) 
	{

	}

	/**
	 Gets a list of all the shape models currently in the canvas 
	 
	 @return DShapeModel[] list of shape models
	 */
	public DShapeModel[] getShapeModels()
	{
		DShapeModel[] models = new DShapeModel[shapes.size()];

		for(int i = 0; i < shapes.size(); i++)
		{
			models[i] = shapes.get(i).getModel();
		}
		return models;
	}
	

	/**
	 Loads all the shape models into the canvas (draws them)
	 
	 @param models DShapeModel[] list of models to be loaded 
	 */
	public void loadShapeModels(DShapeModel[] models)
	{
		for(int i = 0; i < shapes.size(); i++)
		{
			shapes.get(i).deleteModel();
		}
		selected = null;
		shapes.clear();
		for(int i = 0; i < models.length; i++)
		{
			 this.addShape(models[i]);
		}
		model.setShapeList(shapes);
	}
	
	/**
	 Creates a buffered image and returns that image
	 
	 @return BufferedImage
	 */
	public BufferedImage createBufferedImage()
	{
		BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics graph = bi.getGraphics();
	      
	    selected.Selected(false);
	    paint(graph);
	    selected.Selected(true);
	     return bi;
	}
	
	/**
	 Called when the mouse is pressed
	 
	 @param e MouseEvent that triggered the mouse press
	 */
	@Override
	public void mousePressed(MouseEvent e) 
	{
		mouseClick = e.getPoint();
		int rectIndex =-1;
		
		if(selected!= null)
		{
			rectIndex= checkSelectedRectangles(e.getPoint());
		}
		
		if(rectIndex==-1)
		{
			selectShape(e.getX(), e.getY());
			moving = true;
		}
		else
		{
			int knobIndex= -1;
			resizing = true;
			if(selected instanceof DLine)
			{
				if(rectIndex == 0)
				{
					knobIndex = 1;
				}
				else if(rectIndex == 1)
				{
					knobIndex = 0;
				}
			}
			else
			{
				if(rectIndex == 0 )
				{
					knobIndex = 3;
				}
				else if(rectIndex == 1)
				{
					knobIndex = 2;
				}
				else if(rectIndex == 2)
				{
					knobIndex = 1;
				}
				else
				{
					knobIndex = 0 ;
				}
			}
			 resizeAnchorPoint = (Point2D) selected.getKnobs()[knobIndex].clone();
		}
	}

	/**
	 Checks which rectangles are selected
	 
	 @param pnt Point2D selected point
	 @return int index of selected rectangle
	 */
	private int checkSelectedRectangles(Point2D pnt) 
	{
		Rectangle[] rects = selected.getKnobRectangles();
		for(int i = 0; i < rects.length; i++)
		{
			if(rects[i].contains(pnt))
			{
				return i;
			}
		}
		return -1;	
	}

	/**
	 Called when the mouse is dragged
	 
	 @param e MouseEvent that triggered the mouse drag
	 */
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		if (selected != null) 
		{
			if(moving)
			{
				moveSelected(e.getX() , e.getY());
			}
			else if(resizing)
			{
				selected.resize(e.getX(), e.getY(), (int)resizeAnchorPoint.getX(), (int)resizeAnchorPoint.getY()); 
			}
		}
		mouseClick = e.getPoint();
	}

	/**
	 Called when the mouse is moved
	 
	 @param e MouseEvent that triggered the mouse move
	 */
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		// TODO Auto-generated method stub
	}
	
	/**
	 Called when the mouse is released
	 
	 @param e MouseEvent that triggered the mouse release
	 */
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		moving = false;
		resizing = false;
	}

	/**
	 Called when the mouse is entered
	 
	 @param e MouseEvent that triggered the mouse enter
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}

	/**
	 Called when the mouse is exited
	 
	 @param e MouseEvent that triggered the mouse exit
	 */
	@Override
	public void mouseExited(MouseEvent e) {}
	
	/**
	 Changes the text content to the given text
	 
	 @param newText String text to be changed to
	 */
	public void changeText(String newText) 
	{
		if(selected != null && (selected instanceof DText))
		{
			DTextModel text = (DTextModel)selected.getModel();
			text.setString(newText);
		}
	}

	/**
	 Changes the color of the shape to the new color
	 */
	public void changeColor() 
	{
		if (selected != null) 
		{
			Color selectedColor = JColorChooser.showDialog(this, "Choose Color", Color.white);
			if (selectedColor != null) 
			{
				selected.setColor(selectedColor);
			}
		}
	}

	/**
	 Called when a model has been changed
	 
	 @param model DShapeModel changed model
	 */
	@Override
	public void modelChanged(DShapeModel model) 
	{
		repaint();
	}

	/**
	 Removes selected shape
	 */
	public void RemoveShape() 
	{
		if(selected != null)
		{
			selected.deleteModel();
			shapes.remove(shapes.indexOf(selected));
			selected = null;
		}
	}

	/**
	Moves selected shape to front
	 */
	public void moveToFront() 
	{
		if(selected != null)
		{	
			shapes.remove(shapes.indexOf(selected));
			shapes.add(selected);
			selected.movedToFront();
			repaint();
		}
	}
	
	/**
	Moves selected shape to back
	 */
	public void moveToBack() 
	{
		if(selected != null)
		{
			shapes.remove(shapes.indexOf(selected));
			shapes.add(0, selected);
			selected.movedToBack();
			repaint();
		}
	}

	/**
	 Changes the text type to the given text type
	 
	 @param newType String type to be changed to
	 */
	public void changeTextType(String newType)
	{
		if(selected != null && (selected instanceof DText))
		{
			DTextModel text = (DTextModel)selected.getModel();
			text.setType(newType);
		}
	}

	/**
	 Changes the selected model
	 
	 @param model DShapeModel model to be changed to
	 */
	public void changeSelectedModel(DShapeModel mdl){
		if(selected!= null)
		{
			selected.setColor(mdl.col);
			selected.model.setRect(mdl.getRect());
		}
		if(mdl instanceof DLineModel){
			((DLineModel)selected.getModel()).setStart((((DLineModel)mdl).getStart()));
			((DLineModel)selected.getModel()).setEnd((((DLineModel)mdl).getEnd()));		
			}
		if(mdl instanceof DTextModel){
			((DTextModel)selected.getModel()).setString(((DTextModel)mdl).getText());
			((DTextModel)selected.getModel()).setType(((DTextModel)mdl).getType());
		}
		selected.notifyListeners();
	}
	/**
	 Called when a model is removed
	 
	 @param model DShapeModel model to be removed
	 */
	public void modelRemoved(DShapeModel model)
	{
		
		repaint();
	}

	
}
