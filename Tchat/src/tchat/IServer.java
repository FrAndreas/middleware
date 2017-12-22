package tchat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface IServer extends Remote {
	public boolean login(IClient login) throws RemoteException;

	public void publish(String publish) throws RemoteException;

	@SuppressWarnings("rawtypes")
	public Vector getConnected() throws RemoteException;
}