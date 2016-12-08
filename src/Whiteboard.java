import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Whiteboard extends JFrame {
	Canvas draw;
	
	public Whiteboard() throws HeadlessException {
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

	private JPanel addButtons(){
		JPanel vertPanel = new JPanel();
		
		vertPanel.setLayout( new BoxLayout(vertPanel, BoxLayout.Y_AXIS));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout( new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		buttonPanel.add(new  JLabel("Add: "));
		
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
		JButton addTextButton = new JButton("Text");
		addTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewText("String");
				
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
		
		
		JPanel textPanel = new JPanel();
		buttonPanel.setLayout( new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		JTextField textString = new JTextField("Whiteboard");
		textPanel.add(textString);
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
	private void addNewText(String string) {
		// TODO Auto-generated method stub
		
	}
	private void changeColor() {
		draw.changeColor();
	}
	public static void main(String[] args) {
		Whiteboard w1 = new Whiteboard();

	}

}
