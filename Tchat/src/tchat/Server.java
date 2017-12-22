package tchat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class Server extends UnicastRemoteObject implements IServer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<IClient> vector = new Vector<IClient>();

	public Server() throws RemoteException {
	}

	public boolean login(IClient login) throws RemoteException {
		System.out.println(login.getName() + " s'est connecté(e)....");
		login.tell("Connection réussie");
		publish(login.getName() + " vient de se connecter");
		vector.add(login);
		return true;
	}

	public void publish(String publish) throws RemoteException {
		System.out.println(publish);
		for (int i = 0; i < vector.size(); i++) {
			try {
				IClient tmp = (IClient) vector.get(i);
				tmp.tell(publish);
			} catch (Exception e) {
				// problem with the client not connected.

			}
		}
	}

	public Vector<IClient> getConnected() throws RemoteException {
		return vector;
	}
}