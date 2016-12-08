
public class DTextModel extends DShapeModel 
{
	private String content;
	private String type;
	
	public DTextModel(int _x, int _y, int _width, int _height, String textContent, String fontType) 
	{
		super(_x, _y, _width, _height);
		// TODO Auto-generated constructor stub
		content = textContent;
		type = fontType;
	}
	
	public String getText() { return content; }
	
	public String getType() { return type; } 

}
