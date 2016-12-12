import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

Whiteboard GUI 

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class Whiteboard extends JFrame 
{
	private static final long serialVersionUID = 5552655831418640926L;
	Canvas draw;
	ArrayList<JComponent> buttons;
	private ShapeTableModel shapeInfoModel = new ShapeTableModel(new String[] { "X", "Y", "Width", "Height" });
	int transmitterIndex = 0;

	/**
	 Constructor for Whiteboard
	 */
	public Whiteboard() throws HeadlessException 
	{
		super("Whiteboard");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		draw = new Canvas(shapeInfoModel);
		draw.setVisible(true);
		try {
			UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
			// UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.setLayout(new BorderLayout());
		super.add(draw, BorderLayout.CENTER);
		buttons = new ArrayList<JComponent>();
		super.add(addButtons(), BorderLayout.WEST);
		this.pack();
		setVisible(true);
	}

	/**
	 Adds button to the Panel, and returns the JPanel containing the whiteboard
	 
	 @return JPanel whiteboard panel
	 */
	private JPanel addButtons() 
	{
		// vertPanel contains all the controls for the whiteboard
		JPanel vertPanel = new JPanel();
		vertPanel.setLayout(new BoxLayout(vertPanel, BoxLayout.PAGE_AXIS));
		vertPanel.setPreferredSize(new Dimension(400, 0));

		// buttonPanel contains all the add buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

		buttonPanel.add(new JLabel("Add: "));

		// addCircle gives the button that allows for circles to be added
		JButton addCircle = new JButton("Circle");
		addCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				addNewCircle();
			}
		});
		buttons.add(addCircle);
		buttonPanel.add(addCircle);
		
		// addRectangle gives the button that allows for rectangles to be added
		JButton addRectangle = new JButton("Rectangle");
		addRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				addNewRectangle();
			}
		});
		buttons.add(addRectangle);
		buttonPanel.add(addRectangle);
		
		// addLine gives the button that allows for lines to be added
		JButton addLine = new JButton("Line");
		addLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				addNewLine();
			}
		});
		buttons.add(addLine);
		buttonPanel.add(addLine);
		
		vertPanel.add(buttonPanel);

		// fontControl gives the ability to choose a type of font
		JComboBox<String> fontControl = new JComboBox<String>(
				GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		fontControl.setSelectedItem("Dialog");
		fontControl.setMaximumSize(new Dimension(300, 150));
		fontControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				draw.changeTextType((String) fontControl.getSelectedItem());
			}
		});

		// textString gives the ability to change the content of a text object
		JTextField textString = new JTextField("Whiteboard");
		textString.setMaximumSize(new Dimension(200, 150));
		textString.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) 
			{
			}

			public void removeUpdate(DocumentEvent e)
			{
				((Canvas) draw).changeText(textString.getText());
			}

			public void insertUpdate(DocumentEvent e) 
			{
				((Canvas) draw).changeText(textString.getText());
			}
		});

		// addText gives the ability to add text 
		JButton addTextButton = new JButton("Text");
		addTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				addNewText(textString.getText(), (String) fontControl.getSelectedItem());
			}
		});
		buttonPanel.add(addTextButton);

		// textPanel contains all the text information
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		
		buttons.add(textString);
		buttons.add(fontControl);
		buttons.add(textPanel);
		buttons.add(addTextButton);
		
		textPanel.add(textString);
		textPanel.add(fontControl);
		vertPanel.add(textPanel);
		
		// setColor gives the ability to change the color of selected object
		JButton setColor = new JButton("setColor");
		setColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				changeColor();
			}
		});
		buttons.add(setColor);
		vertPanel.add(setColor);

		// thirdPannel contains add/remove buttons
		JPanel thirdPannel = new JPanel();
		thirdPannel.setLayout(new BoxLayout(thirdPannel, BoxLayout.LINE_AXIS));
		
		// removeShape gives the ability to remove a shape from the board
		JButton RemoveShape = new JButton("Remove Shape");
		RemoveShape.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				draw.RemoveShape();
			}
		});
		buttons.add(RemoveShape);
		thirdPannel.add(RemoveShape);
		
		// moveToFront gives the ability to change the ordering of objects (move to the front)
		JButton moveToFront = new JButton("Move to Front");
		moveToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				draw.moveToFront();
			}
		});
		buttons.add(moveToFront);
		thirdPannel.add(moveToFront);

		// moveToBack gives the ability to change the ordering of objects (move to the back)
		JButton moveToBack = new JButton("Move to Back");
		moveToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				draw.moveToBack();
			}
		});
		buttons.add(moveToBack);
		thirdPannel.add(moveToBack);
		
		thirdPannel.setAlignmentX(LEFT_ALIGNMENT);
		vertPanel.add(thirdPannel);
		
		// panels containing the save/open functionalities
		JPanel saveAndOpen = new JPanel();
		saveAndOpen.setLayout(new BoxLayout(saveAndOpen, BoxLayout.X_AXIS));
		
		// saveButton gives the ability to save the file
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				executeSave();
			}
		});
		buttons.add(saveButton);
		saveAndOpen.add(saveButton);
		
		// openButton gives the ability to open an existing file
		JButton openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				executOpen();
			}
		});
		buttons.add(openButton);
		saveAndOpen.add(openButton);
		
		// saveImageButton gives the ability to save the file as an image
		JButton saveImageButton = new JButton("Save PNG");
		saveImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				saveImage();
			}
		});
		buttons.add(saveImageButton);
		saveAndOpen.add(saveImageButton);
		
		vertPanel.add(saveAndOpen);

		// table shows the information about the current objects on the table
		JTable table = new JTable(shapeInfoModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e)
			{
				if(!e.getValueIsAdjusting() && table.getSelectedRow() >= 0)
				{
					draw.selectShape(((ShapeTableModel)table.getModel()).getModelAt(table.getSelectedRow()));
				}
			}
		});

		// tablePane contains the table
		JScrollPane tablePane = new JScrollPane(table);
		tablePane.setMaximumSize(vertPanel.getMaximumSize());
		vertPanel.add(tablePane);

		// clientServer contains the server/client buttons
		JPanel clientServer = new JPanel();
		clientServer.setLayout(new BoxLayout(clientServer, BoxLayout.X_AXIS));
		
		// clientStartButton gives the ability to start the current window as a client
		JButton clientStartButton = new JButton("Start Client!");
		clientStartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				startClient();
			}
		});
		clientServer.add(clientStartButton);

		// serverStartButton gives the ability to start the current window as a server
		JButton serverStartButton = new JButton("Start Server!");
		serverStartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				startServer();
			}
		});
		clientServer.add(serverStartButton);
		
		vertPanel.add(clientServer);

		for (Component comp : vertPanel.getComponents()) 
		{
			((JComponent)comp).setAlignmentX(Box.LEFT_ALIGNMENT);
		}
		return vertPanel;
	}

	/**
	 Starts the server
	 */
	private void startServer() 
	{
		JTextField Port = new JTextField();
		Port.setText("47000");

		final JComponent[] inputs = new JComponent[] { new JLabel("Please Enter Port Number"), Port };
		int result = JOptionPane.showConfirmDialog(null, inputs, "Server", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.OK_OPTION, new ImageIcon("Images/url.png"));
		if (result == JOptionPane.OK_OPTION) 
		{
			int portnum;
			try {
				portnum = Integer.parseInt(Port.getText());
				if (!(portnum >= 0 && portnum <= 65535)) 
				{
					portnum = invalidEntry();
				}
			} catch (Exception e) {
				portnum = invalidEntry();
			}
			new ServerThread(portnum).start();
		} 
		else 
		{
			return;
		}
	}

	/**
	 Checks to see if the port number entry is valid
	 
	 @return int port number
	 */
	private int invalidEntry() 
	{
		JTextField Port = new JTextField();
		Port.setText("47000");
		int portNum = 47000;
		final JComponent[] inputs = new JComponent[] {
				new JLabel("Enter a number between 0 and 65535 or a port will be assigned for you"), Port };
		int result = JOptionPane.showConfirmDialog(null, inputs, "Invalid Entry", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.OK_OPTION, new ImageIcon("Images/url.png"));
		if (result == JOptionPane.OK_OPTION) 
		{
			try {
				portNum = Integer.parseInt(Port.getText());
			} catch (Exception e) {
			}
		}
		return portNum;
	}

	/**
	 Starts the client
	 */
	private void startClient() 
	{
		JTextField Port = new JTextField();
		Port.setText("47000");

		final JComponent[] inputs = new JComponent[] { new JLabel("Please Enter Port Number"), Port };

		int result = JOptionPane.showConfirmDialog(null, inputs, "Client", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.OK_OPTION, new ImageIcon("Images/url.png"));
		if (result == JOptionPane.OK_OPTION) 
		{
			int portnum;
			try {
				portnum = Integer.parseInt(Port.getText());
				if (!(portnum >= 0 && portnum <= 65535)) 
				{
					portnum = invalidEntry();
				}
			} catch (Exception e) {
				portnum = invalidEntry();
			}
			disableButtons();
			draw.setClient();
			new ClientThread(portnum).start();
		} 
		else 
		{
			return;
		}
	}

	/**
	 Disables the buttons
	 */
	private void disableButtons() 
	{
		for (JComponent comp : buttons) 
		{
			((JComponent) comp).setEnabled(false);
		}
	}

	/**
	 Opens the chosen file
	 */
	protected void executOpen() 
	{
		JFileChooser chooser = new JFileChooser();
		int retrival = chooser.showOpenDialog(null);
		
		if (retrival == JFileChooser.APPROVE_OPTION) 
		{
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

	/**
	 Saves the file as an image
	 */
	private void saveImage() 
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/home/me/Documents"));
		int retrival = chooser.showSaveDialog(null);
		
		if (retrival == JFileChooser.APPROVE_OPTION) 
		{
			try {
				ImageIO.write(draw.createBufferedImage(), "PNG", new File(chooser.getSelectedFile() + ".PNG"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 Saves the current file
	 */
	private void executeSave()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/home/me/Documents"));
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) 
		{
			try {
				XMLEncoder e = new XMLEncoder(
						new BufferedOutputStream(new FileOutputStream(chooser.getSelectedFile() + ".xml")));
				DShapeModel[] modelShapes = draw.getShapeModels();
				e.writeObject(modelShapes);
				e.flush();
				e.close();
			} catch (Exception ex) {
				// ex.printStackTrace();
			}
		}
	}

	/**
	 Adds new circle to the whiteboard, updating appropriate tables, arraylists
	 */
	private void addNewCircle() 
	{
		DOvalModel ovl = new DOvalModel(10, 10, 20, 20);
		draw.addShape(ovl);
		shapeInfoModel.addRow(ovl);
		this.repaint();
	}

	/**
	 Adds new rectangle to the whiteboard, updating appropriate tables, arraylists
	 */
	private void addNewRectangle() 
	{
		DRectModel rect = new DRectModel(10, 10, 20, 20);
		draw.addShape(rect);
		shapeInfoModel.addRow(rect);
		this.repaint();
	}

	/**
	 Adds new line to the whiteboard, updating appropriate tables, arraylists
	 */
	private void addNewLine() 
	{
		DLineModel line = new DLineModel(10, 10, 20, 20);
		draw.addShape(line);
		shapeInfoModel.addRow(line);
		this.repaint();
	}

	/**
	 Adds new text to the whiteboard, updating appropriate tables, arraylists
	 */
	private void addNewText(String content, String fontFamily) 
	{
		// TODO Auto-generated method stub
		DTextModel text = new DTextModel((int) 10, (int) (10), (int) (20), (int) (20), content, fontFamily);
		draw.addShape(text);
		shapeInfoModel.addRow(text);
		this.repaint();
	}

	/**
	 Changes the color of the selected shape
	 */
	private void changeColor()
	{
		draw.changeColor();
	}

	public class ServerThread extends Thread {

		boolean listening = true;
		int port;

		public ServerThread(int p) {
			this.port = p;
		}

		public void run() {
			ServerSocket serverSocket = null;

			try {
				serverSocket = new ServerSocket(port);

			} catch (IOException e) {
				System.err.println("Could not listen on port: " + port + "..." + e);
				System.exit(-1);
			}
			if (listening) {
				System.out.println("Server Successfully Created! :D:D");
				try {
					sleep(1000);
				} catch (InterruptedException e) {
				}
				System.out.println("Searching for Connection!");
			}

			// j1.add(new Client(num, Name, j1, window));

			while (listening) {
				try {
					draw.addDataTransmitter(new dataTransmitter(serverSocket.accept(), transmitterIndex++));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class ClientThread extends Thread {
		Socket kkSocket;
		ObjectInputStream in;
		int timeout = 9000;

		public ClientThread(int port) {

			try {

				SocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), port);
				kkSocket = new Socket();
				kkSocket.connect(address, timeout);
				in = new ObjectInputStream((kkSocket.getInputStream()));

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				while (true) {
					String verb = (String) in.readObject();
					String xmlString = (String) in.readObject();                      
					XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));                      
					DShapeModel shapeObj = (DShapeModel) decoder.readObject();
					decoder.close();
					
					System.out.println(shapeObj.getRect().x);
					
					if (verb.equals("add")) {
						shapeObj.clearListeners();
						draw.addShape(shapeObj);
						draw.model.setShapeList(draw.shapes);
					} else if (verb.equals("remove")) {
						DShape temp = draw.selected;
						draw.selected = draw.SelectByID(shapeObj.ID);
						draw.RemoveShape();
						draw.selected = temp;
					} else if (verb.equals("front")) {
						DShape temp = draw.selected;
						draw.selected = draw.SelectByID(shapeObj.ID);
						draw.moveToFront();
						draw.selected = temp;
					} else if (verb.equals("back")) {
						DShape temp = draw.selected;
						draw.selected = draw.SelectByID(shapeObj.ID);
						draw.moveToFront();
						draw.selected = temp;
					} else if (verb.equals("changed")) {
						DShape temp = draw.selected;
						draw.selected = draw.SelectByID(shapeObj.ID);
						draw.changeSelectedModel(shapeObj);
						draw.selected = temp;
					}
				}

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {
		Whiteboard w1 = new Whiteboard();

	}

}
