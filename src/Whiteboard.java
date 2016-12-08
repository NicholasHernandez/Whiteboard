import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.GraphicsEnvironment;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Whiteboard extends JFrame {
	Canvas draw;
	
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
		setResizable( false );
	}

	private JPanel addButtons()
	{
		JPanel vertPanel = new JPanel();
		
		vertPanel.setLayout(new BoxLayout(vertPanel, BoxLayout.PAGE_AXIS));
		
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
		
		JComboBox<String> fontControl = new JComboBox<String>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		fontControl.setSelectedItem("Dialog");
		
		fontControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JTextField textString = new JTextField("Whiteboard");
		JButton addTextButton = new JButton("Text");
		addTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewText(textString.getText(), (String)fontControl.getSelectedItem());
				
			}

			
		});
		
		buttonPanel.add(addTextButton);
		vertPanel.add(buttonPanel);
		JButton setColor = new JButton("setColor");
		setColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeColor();
				
			}

			
		});
		
		vertPanel.add(setColor);

		for (Component comp : vertPanel.getComponents()) { 
			((JComponent) comp).setAlignmentX(Box.LEFT_ALIGNMENT);
		}
		
		// Text Editing
		JPanel textPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		
		textPanel.add(textString);
		textPanel.add(fontControl);
		buttonPanel.add(textPanel);
		return vertPanel;
	}
	
	
	private void addNewCircle() {
		DOvalModel ovl = new DOvalModel((int)(Math.random()*200), (int)(Math.random()*200),(int)(Math.random()*200), (int)(Math.random()*200));
		draw.addShape(ovl);
		this.repaint();

	}
	private void addNewRectangle() {

		DRectModel rect = new DRectModel((int)(Math.random()*200), (int)(Math.random()*200),(int)(Math.random()*200), (int)(Math.random()*200));
		draw.addShape(rect);
		this.repaint();

	}
	private void addNewLine() {
		// TODO Auto-generated method stub
		
	}
	private void addNewText(String content, String fontFamily) 
	{
		// TODO Auto-generated method stub
		DTextModel text = new DTextModel((int)(Math.random()*200), (int)(Math.random()*200),(int)(Math.random()*200), (int)(Math.random()*200), content, fontFamily);
		draw.addShape(text);
		this.repaint();
	}
	
	private void changeColor() {
		draw.changeColor();
	}
	public static void main(String[] args) {
		Whiteboard w1 = new Whiteboard();

	}

}
