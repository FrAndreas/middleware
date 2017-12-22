package tchat;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ChatUI implements Remote {
	private Client client;
	private IServer server;
	String connexion = "Connexion";
	JTextArea tx;
	JTextField tf, ip, name;
	JButton connect, send, disconnect;
	JList<String> lst;
	JFrame frame;

	public void doConnect() {
		if (connect.getText().equals(connexion)) {
			if (name.getText().length() < 2) {
				JOptionPane.showMessageDialog(frame, "Veuillez écrire un nom");
				return;
			}
			if (ip.getText().length() < 2) {
				JOptionPane.showMessageDialog(frame, "Veuillez entrer une IP");
				return;
			}
			try {
				client = new Client(name.getText());
				client.setGUI(this);
				server = (IServer) Naming.lookup("rmi://" + ip.getText() + "/myabc");
				server.login(client);
				updateUsers(server.getConnected());
				connect.setText("Déconnexion");
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "ERREUR, Connexion impossible ...");
			}
		} else {
			updateUsers(null);
			connect.setText(connexion);

		}
	}

	public void disconnectServer() throws RemoteException {
		try {
			// Unregister ourself
			Naming.unbind("rmi://" + StartServer.adServer + "/myabc");
			JOptionPane.showMessageDialog(frame, "Serveur éteint");
			// Unexport; this will also remove us from the RMI runtime
			UnicastRemoteObject.unexportObject(this, true);

		} catch (Exception e) {
		}
	}

	public void sendText() {
		if (connect.getText().equals(connexion)) {
			JOptionPane.showMessageDialog(frame, "Veuillez vous connecter d'abord");
			return;
		}
		String st = tf.getText();
		st = "[" + name.getText() + "] " + st;
		tf.setText("");
		// Remove if you are going to implement for remote invocation
		try {
			server.publish(st);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeMsg(String st) {
		tx.setText(tx.getText() + "\n" + st);
	}

	public void updateUsers(@SuppressWarnings("rawtypes") Vector vector) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		if (vector != null)
			for (int i = 0; i < vector.size(); i++) {
				try {
					String tmp = ((IClient) vector.get(i)).getName();
					listModel.addElement(tmp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		lst.setModel(listModel);
	}

	public static void main(String[] args) {
		System.out.println("Hello World !");
		@SuppressWarnings("unused")
		ChatUI c = new ChatUI();
	}

	// Interface utilisateur
	public ChatUI() {

		// InetAddress monIP=InetAddress.getLocalHost();
		// String nosIp= monIP.getHostName();

		frame = new JFrame("Chat");
		JPanel main = new JPanel();
		JPanel top = new JPanel();
		JPanel cn = new JPanel();
		JPanel bottom = new JPanel();
		JPanel left = new JPanel();
		ip = new JTextField();
		tf = new JTextField();
		name = new JTextField();
		tx = new JTextArea();
		connect = new JButton("Connexion");
		send = new JButton("Envoyer");
		disconnect = new JButton("Eteindre Serveur");
		lst = new JList<String>();
		main.setLayout(new BorderLayout(5, 5));
		top.setLayout(new GridLayout(1, 0, 5, 5));
		cn.setLayout(new BorderLayout(5, 5));
		bottom.setLayout(new BorderLayout(5, 5));
		left.setLayout(new GridLayout(1, 0, 5, 5));

		top.add(new JLabel("Pseudo: "));
		top.add(name);
		top.add(new JLabel("Adresse Server: "));
		top.add(ip);
		top.add(connect);
		top.add(disconnect);

		// left.add(new JLabel(" serveur dispo :"));
		// left.add(adServer);

		cn.add(new JScrollPane(tx), BorderLayout.CENTER);
		cn.add(lst, BorderLayout.EAST);

		bottom.add(tf, BorderLayout.CENTER);
		bottom.add(send, BorderLayout.EAST);

		main.add(top, BorderLayout.NORTH);
		main.add(cn, BorderLayout.CENTER);
		main.add(bottom, BorderLayout.SOUTH);
		main.add(left, BorderLayout.WEST);
		main.setBorder(new EmptyBorder(10, 10, 10, 10));

		// Evenements
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doConnect();
			}
		});
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendText();
			}
		});
		tf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendText();
			}
		});
		disconnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					disconnectServer();
					System.out.println("Le server est éteint");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

			}
		});

		frame.setContentPane(main);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}

}