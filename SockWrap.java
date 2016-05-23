package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SockWrap {

	private static final int PORT_NUM_START = 28788;

	private Socket sock;
	private ServerSocket gameSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	private int port_num;

	public SockWrap() {
	}

	public int initHost() throws IOException {
		port_num = PORT_NUM_START;
		while (true) {
			try {
				gameSocket = new ServerSocket(port_num, 10);
				System.out.println("Connected on port " + port_num);
				break;
			} catch (java.net.BindException e) {
				port_num++;
				continue;
			}
		}
		// System.out.println("Waiting for join...");
		/*
		sock = gameSocket.accept();
		out = new ObjectOutputStream(sock.getOutputStream());
		out.flush();
		in = new ObjectInputStream(sock.getInputStream());
		// host = true;
		*/
		return port_num;
	}
	
	public SockWrap hostConnect() throws IOException {
		
		SockWrap sw = new SockWrap();
		sw.sock = gameSocket.accept();
		sw.out = new ObjectOutputStream(sw.sock.getOutputStream());
		sw.out.flush();
		sw.in = new ObjectInputStream(sw.sock.getInputStream());
		
		return sw;
	}

	public void initClient(String ip) throws UnknownHostException, IOException {
		port_num = PORT_NUM_START;
		while (true) {
			try {
				sock = new Socket(ip, port_num);
				System.out.println("Connected on port " + port_num);
				break;
			} catch (java.net.ConnectException e) {
				System.out.println("Failed to connect on port " + port_num);
				port_num++;

				continue;
			}
		}
		out = new ObjectOutputStream(sock.getOutputStream());
		out.flush();
		in = new ObjectInputStream(sock.getInputStream());
		// host = false;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}
}
