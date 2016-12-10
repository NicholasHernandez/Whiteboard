import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.FontMetrics;

public class DText extends DShape 
{
	public DText() {
		super();
	}

	public DText(DShapeModel shapeModel) {
		super(shapeModel);
	}

	public void draw(Graphics g) 
	{
		g.setColor(color);
		Rectangle rect = super.getModel().getRectangle();
		
		if(super.getModel() instanceof DTextModel)
		{
			DTextModel textMod = (DTextModel) super.getModel();
			Shape clip = g.getClip();
			Font font = new Font(textMod.getType(), Font.PLAIN, (int)computeFont(g, textMod.getType(), textMod));
			g.setFont(font);
			g.setClip(clip.getBounds().createIntersection(getBounds()));
			g.drawString(textMod.getText(), rect.x, rect.y+(int)(rect.height*.75));
			// make sure to figure out what to do with where to place the text
			g.setClip(clip);
			super.draw(g);
		}
	}
	
	
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
