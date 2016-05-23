package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Stack;

public class NetworkController implements Runnable {

	private static final int PORT_NUM = 28788;

	private Socket sock;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean host;
	private int stepTime;
	private boolean block;

	private Object toSend;
	//private Stack<Object> received;
	private Object received;
	
	public NetworkController(int stepTime) {
		this.stepTime = stepTime;
		this.received = new Stack<Object>();
	}

	public void initHost() throws IOException {
		ServerSocket gameSocket = new ServerSocket(PORT_NUM, 10);
		// System.out.println("Waiting for join...");
		sock = gameSocket.accept();
		out = new ObjectOutputStream(sock.getOutputStream());
		out.flush();
		in = new ObjectInputStream(sock.getInputStream());
		host = true;
	}

	public void initJoin(String ip) throws UnknownHostException, IOException {
		sock = new Socket(ip, PORT_NUM);
		out = new ObjectOutputStream(sock.getOutputStream());
		out.flush();
		in = new ObjectInputStream(sock.getInputStream());
		host = false;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(stepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sync();
		}
	}

	private void sync() {
		unblock();
		try {
			sendHero();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			getHero();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendHero() throws IOException {
		while (isBlocked()) {
		}
		block();
		out.reset();
		out.writeObject(toSend);
		out.flush();
		unblock();

	}

	private void getHero() throws ClassNotFoundException, IOException {
		while (isBlocked()) {
		}
		block();
		received = in.readObject();
		//out.writeObject(new Integer(1111));
		unblock();

	}

	public void send(Object o) {
		toSend = o;
	}

	public Object receive() {
		return received;
	}

	private void block() {
		block = true;
	}

	private void unblock() {
		block = false;
	}

	private boolean isBlocked() {
		return block;
	}
}
