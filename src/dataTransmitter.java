import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class dataTransmitter extends Thread implements ModelListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Socket socket;
	int ID;
	ObjectOutputStream out;
	
	public dataTransmitter(Socket s1, int num ) {
		socket = s1;
		ID = num;
		
	
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
			encodeAndSend(model);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void movedToFront(DShapeModel model){
		try {
			out.writeObject("front");
			encodeAndSend(model);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void movedToBack(DShapeModel model){
		try {
			out.writeObject("back");
			encodeAndSend(model);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void modelChanged(DShapeModel model) {
		try {
			out.writeObject("changed");
			//System.out.println(model.getRect().x);
			encodeAndSend(model);
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void encodeAndSend(DShapeModel model) throws IOException{
        OutputStream memStream = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(memStream); 
		encoder.writeObject(model);     
		encoder.close();      
		String xmlString = memStream.toString();
		out.writeObject(xmlString);
	}
	public void modelRemoved(DShapeModel model) {
		try {
			out.writeObject("remove");
			encodeAndSend(model);
		} catch (IOException e) {
			 e.printStackTrace();
		}
	}

}
