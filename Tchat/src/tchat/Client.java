package tchat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements IClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ChatUI ui;

	public Client(String n) throws RemoteException {
		setName(n);
	}

	public void tell(String st) throws RemoteException {
		System.out.println(st);
		ui.writeMsg(st);
	}

	public String getName() throws RemoteException {
		return name;
	}

	public void setGUI(ChatUI t) {
		ui = t;
	}

	public void setName(String name) {
		this.name = name;
	}
}
