import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.GraphicsEnvironment;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Whiteboard extends JFrame {
	Canvas draw;
	private static ShapeTableModel shapeInfoModel;
	
	public Whiteboard() throws HeadlessException 
		{
		super("Whiteboard");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		draw = new Canvas();
		draw.setVisible(true);
		super.setLayout(new BorderLayout());
		super.add(draw, BorderLayout.CENTER);
		super.add(addButtons(), BorderLayout.WEST);

		this.pack();
		setVisible(true);
		// setResizable( false );
	}

	private JPanel addButtons() {
		JPanel vertPanel = new JPanel();
		vertPanel.setLayout(new BoxLayout(vertPanel, BoxLayout.PAGE_AXIS));
		vertPanel.setPreferredSize(new Dimension(400, 0));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

		buttonPanel.add(new JLabel("Add: "));

		JButton addCircle = new JButton("Circle");
		addCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewCircle();

			}
		});
		buttonPanel.add(addCircle);
		JButton addRectangle = new JButton("Rectangle");
		addRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewRectangle();

			}

		});
		buttonPanel.add(addRectangle);
		JButton addLine = new JButton("Line");
		addLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewLine();

			}

		});
		buttonPanel.add(addLine);
		vertPanel.add(buttonPanel);

		JComboBox<String> fontControl = new JComboBox<String>(
				GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		fontControl.setSelectedItem("Dialog");
		fontControl.setMaximumSize(new Dimension(300, 150));

		fontControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				draw.changeTextType((String)fontControl.getSelectedItem());
			}
		});

		JTextField textString = new JTextField("Whiteboard");
		textString.setMaximumSize(new Dimension(200, 150));
		textString.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			}

			public void removeUpdate(DocumentEvent e) {
			}

			public void insertUpdate(DocumentEvent e) {
				((Canvas) draw).changeText(textString.getText());
			}
		});

		JButton addTextButton = new JButton("Text");

		addTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewText(textString.getText(), (String) fontControl.getSelectedItem());
			}

		});

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

		textPanel.add(textString);
		textPanel.add(fontControl);
		vertPanel.add(textPanel);

		buttonPanel.add(addTextButton);
		JButton setColor = new JButton("setColor");
		setColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeColor();
			}

		});

		vertPanel.add(setColor);

		JPanel thirdPannel = new JPanel();

		JButton RemoveShape = new JButton("Remove Shape");
		RemoveShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				draw.RemoveShape();

			}
		});

		thirdPannel.add(RemoveShape);
		JButton moveToFront = new JButton("Move to Front");
		moveToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				draw.moveToFront(); 

			}
		});

		thirdPannel.add(moveToFront);

		JButton moveToBack = new JButton("Move to Back");
		moveToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				draw.moveToBack();

			}
		});

		thirdPannel.add(moveToBack);
		vertPanel.add(thirdPannel);
		for (Component comp : vertPanel.getComponents()) {
			((JComponent) comp).setAlignmentX(Box.LEFT_ALIGNMENT);
		}
		
		shapeInfoModel = new ShapeTableModel(new String[] {"X","Y","Width","Height"});
		JTable table = new JTable(shapeInfoModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JScrollPane tablePane = new JScrollPane(table);
		tablePane.setMaximumSize(vertPanel.getMaximumSize());
		vertPanel.add(tablePane);

		JPanel saveAndOpen = new JPanel();
		saveAndOpen.setLayout(new BoxLayout(saveAndOpen, BoxLayout.X_AXIS));
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeSave();

			}

		});
		saveAndOpen.add(saveButton);
		JButton openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executOpen();

			}

		});
		saveAndOpen.add(openButton);
		JButton saveImageButton = new JButton("Save PNG");
		saveImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveImage();

			}

		});
		saveAndOpen.add(saveImageButton);
		vertPanel.add(saveAndOpen);

		return vertPanel;
	}

	protected void executOpen() {

		JFileChooser chooser = new JFileChooser();
		
		int retrival = chooser.showOpenDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			XMLDecoder d = null;
			try {

				d = new XMLDecoder(new BufferedInputStream(new FileInputStream(chooser.getSelectedFile())));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			Object result = d.readObject();
			draw.loadShapeModels((DShapeModel[]) result);
			d.close();
		}
	}
	
	private void saveImage() {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/home/me/Documents"));
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			
			try {
				ImageIO.write(draw.createBufferedImage(), "PNG", new File(chooser.getSelectedFile()+".PNG"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}

	}
	private void executeSave() {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/home/me/Documents"));
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try {
				XMLEncoder e = new XMLEncoder(
						new BufferedOutputStream(new FileOutputStream(chooser.getSelectedFile() + ".xml")));
				DShapeModel[] modelShapes = draw.getShapeModels();
				e.writeObject(modelShapes);/*
											 * for(int i =0; i
											 * <modelShapes.length; i++){
											 * e.writeObject(modelShapes[i]); }
											 */
				e.flush();
				e.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	private void addNewCircle() {
		DOvalModel ovl = new DOvalModel(10, 10, 20, 20);
		draw.addShape(ovl);
		shapeInfoModel.addRow(ovl);
		this.repaint();

	}

	private void addNewRectangle() {

		DRectModel rect = new DRectModel(10, 10, 20, 20);
		draw.addShape(rect);
		shapeInfoModel.addRow(rect);
		this.repaint();
	}
	
	private void addNewLine() {
		DLineModel line = new DLineModel(10, 10, 20, 20);
		draw.addShape(line);
		shapeInfoModel.addRow(line);
		this.repaint();
	}

	private void addNewText(String content, String fontFamily) {
		// TODO Auto-generated method stub
		DTextModel text = new DTextModel((int) 10, (int) (10), (int) (20), (int) (20), content, fontFamily);
		draw.addShape(text);
		shapeInfoModel.addRow(text);
		this.repaint();
	}

	private void changeColor() {
		draw.changeColor();

	}
	
	public static void main(String[] args) {
		Whiteboard w1 = new Whiteboard();

	}

}
