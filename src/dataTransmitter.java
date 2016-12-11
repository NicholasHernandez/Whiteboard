import java.net.Socket;

public class dataTransmitter extends Thread implements ModelListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Socket socket;
	int ID;
	Canvas canvas;
	public dataTransmitter(Socket s1, int num, Canvas c) {
		socket = s1;
		ID = num;
		canvas = c;
	}
	
	@Override
	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modelRemoved(DShapeModel model) {
		// TODO Auto-generated method stub

	}

}
