import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.FontMetrics;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

View for text; draws the text

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class DText extends DShape 
{
	/**
	 Constructor for DText
	 */
	public DText() 
	{
		super();
	}

	/**
	 Constructor for DText
	 
	 @param shapeModel DShapeModel model to be set to the text
	 */
	public DText(DShapeModel shapeModel) 
	{
		super(shapeModel);
	}

	/**
	 Draws the text with the given graphics object
	 
	 @param g Graphics used to draw the text
	 */
	public void draw(Graphics g) 
	{
		g.setColor(getColor());
		Rectangle rect = super.getModel().getRectangle();
		
		if(super.getModel() instanceof DTextModel)
		{
			DTextModel textMod = (DTextModel) super.getModel();
			Shape clip = g.getClip();
			Font font = new Font(textMod.getType(), Font.PLAIN, (int)computeFont(g, textMod.getType(), textMod));
			g.setFont(font);
			g.setClip(clip.getBounds().createIntersection(getBounds()));
			g.drawString(textMod.getText(), rect.x, rect.y + (int)(rect.height * .75));
			g.setClip(clip);
			super.draw(g);
		}
	}
	
	/**
	 Computes the appropriate font size based on the font type and the size of the rectangle that houses the text
	 
	 @param g Graphics used to draw the text
	 @param fontType String type of font
	 @param textMod model of the text 
	 @return size of the font
	 */
	private double computeFont(Graphics g, String fontType, DTextModel textMod)
	{
		double size = 1.0;
		Font font = new Font(fontType, Font.PLAIN, (int)size);
		FontMetrics fMetric = g.getFontMetrics(font);
		
		while(fMetric.getHeight() <  textMod.getRectangle().height)
		{
			size = (size * 1.10) + 1;
			if(fMetric.getHeight() > textMod.getRectangle().height)
			{
				return (size - 1) / 1.10;
			}
			else if(fMetric.getHeight() == textMod.getRectangle().height)
			{
				return size;
			}
			else
			{
				fMetric = g.getFontMetrics(new Font(fontType, Font.PLAIN, (int) size));
			}
		}
		return size;
	}
}
