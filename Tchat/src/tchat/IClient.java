package tchat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
	public void tell(String name) throws RemoteException;

	public String getName() throws RemoteException;

}