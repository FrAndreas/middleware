package tchat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;

public class StartServer {
	static String adServer;

	public static void AdressServer() throws UnknownHostException {
		InetAddress monIP = InetAddress.getLocalHost();
		adServer = monIP.getHostAddress();

		return;
	}

	public static void main(String[] args) throws UnknownHostException {

		try {

			java.rmi.registry.LocateRegistry.createRegistry(1099);
			AdressServer();
			IServer iserver = new Server();
			Naming.rebind("rmi://" + adServer + "/myabc", iserver);
			System.out.println("[System] Le server est prêt");
		} catch (Exception e) {
			System.out.println(" Server échec: " + e);
		}

		System.out.println("IP of my system is := " + adServer);
	}

}
