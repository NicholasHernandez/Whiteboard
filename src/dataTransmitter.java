import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class dataTransmitter extends Thread implements ModelListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Socket socket;
	int ID;
	Canvas canvas;
	ObjectOutputStream out;
	public dataTransmitter(Socket s1, int num, Canvas c) {
		socket = s1;
		ID = num;
		
		canvas = c;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void modelAdded(DShapeModel model){
		try {
			out.writeObject("add");
			out.writeObject(model);
		} catch (IOException e) {
						e.printStackTrace();
		}
		
	}
	
	public void movedToFront(DShapeModel model){
		try {
			out.writeObject("front");
			out.writeObject(model);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void movedToBack(DShapeModel model){
		try {
			out.writeObject("Back");
			out.writeObject(model);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void modelChanged(DShapeModel model) {
		try {
			out.writeObject("changed");
			out.writeObject(model);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	
	public void modelRemoved(DShapeModel model) {
		try {
			out.writeObject("remove");
			out.writeObject(model);
		} catch (IOException e) {
			 e.printStackTrace();
		}
	}

}
