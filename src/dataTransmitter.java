import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**

COPYRIGHT (C) 2016 Anna Chang, Nicholas Hernandez, Gwyneth Mina. All Rights Reserved.

Data transmitter; transmits data changes to client

Solves CS151 Final Project

@author Anna Chang, Nicholas Hernandez, Gwyneth Mina

@version 1.01 2016/12/11

*/

public class dataTransmitter extends Thread implements ModelListener 
{
	private static final long serialVersionUID = 1L;
	Socket socket;
	int ID;
	ObjectOutputStream out;
	
	/**
	 Constructor for dataTransmitter
	 
	 @param s1 Socket socket for connection to client
	 @param num int ID number 
	 */
	public dataTransmitter(Socket s1, int num) 
	{
		socket = s1;
		ID = num;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
	}
	
	/**
	 Updates client on added object/shape
	 
	 @param model DShapeModel model that is added
	 */
	public void modelAdded(DShapeModel model)
	{
		try {
			out.writeObject("add");
			encodeAndSend(model);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	/**
	 Updates client on shape moved to front
	 
	 @param model DShapeModel model that is moved to front
	 */
	public void movedToFront(DShapeModel model)
	{
		try {
			out.writeObject("front");
			encodeAndSend(model);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	/**
	 Updates client on shape moved to back
	 
	 @param model DShapeModel model that is moved to back
	 */
	public void movedToBack(DShapeModel model)
	{
		try {
			out.writeObject("back");
			encodeAndSend(model);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	/**
	 Updates client on shape changed
	 
	 @param model DShapeModel model that is changed
	 */
	@Override
	public void modelChanged(DShapeModel model) 
	{
		try {
			out.writeObject("changed");
			//System.out.println(model.getRect().x);
			encodeAndSend(model);
			out.flush();

		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	/**
	 Encodes and sends the model
	 
	 @param model DShapeModel model that is to be sent
	 */
	void encodeAndSend(DShapeModel model)
	{
        OutputStream memStream = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(memStream); 
		encoder.writeObject(model);     
		encoder.close();      
		String xmlString = memStream.toString();
		try {
			out.writeObject(xmlString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	/**
	 Updates client on model removed
	 
	 @param model DShapeModel model that is removed
	 */
	public void modelRemoved(DShapeModel model) 
	{
		try {
			out.writeObject("remove");
			encodeAndSend(model);
		} catch (IOException e) {
			 e.printStackTrace();
		}
	}
}
