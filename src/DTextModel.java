import java.io.Serializable;

public class DTextModel extends DShapeModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1938078507710805967L;
	private String content;
	private String type;
	
	public DTextModel(){
		super();
		content = "";
		type = "";
		
	}
	
	public DTextModel(int _x, int _y, int _width, int _height, String textContent, String fontType) 
	{
		super(_x, _y, _width, _height);
		// TODO Auto-generated constructor stub
		content = textContent;
		type = fontType;
	}
	
	public void setContent(String s) { 
		content = s; 
	}
	public void setString(String s) { 
		content = s; 
		notifyListeners();
	}
	public void setType(String t){
		type = t;
		notifyListeners();
	}
	
	public String getContent(){
		return content;
	}
	public String getText() { 
		return content; }
	
	public String getType() { return type; } 

}
