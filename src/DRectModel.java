import java.io.Serializable;

public class DRectModel extends DShapeModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 257380007310295710L;
	public DRectModel(){
		super();
	}
	public DRectModel(int _x, int _y, int _width, int _height) {
		super(_x, _y, _width, _height);
	
	}

}
