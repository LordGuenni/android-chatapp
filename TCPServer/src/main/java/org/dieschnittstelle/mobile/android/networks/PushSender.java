package org.dieschnittstelle.mobile.android.networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PushSender implements Runnable {

	private volatile Socket mClientSocket;

	public void run() {
		while (true) {
			String msg = getPushMessage();

			Socket clientSocket;
			synchronized (this) {
				clientSocket = mClientSocket;
			}

			if (clientSocket != null) {
				try {
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

					if (msg.isEmpty() || msg.equals("done")) {
						break;
					} else {
						PushServer.logger.info("sending message: " + msg + ". clientSocket is: " + clientSocket);
						out.println(msg);
					}

					// TCPServer.this.clientSocket.close();
				} catch (IOException t) {
					String err = "got exception: " + t;
					PushServer.logger.error(err, t);
				}
			}

		}

	}

	public synchronized void setClientSocket(Socket aClientSocket) {
		mClientSocket = aClientSocket;
	}

	public String getPushMessage() {
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Push message: ");
		String s = "";
		try {
			s = bufferRead.readLine();
		} catch (IOException e) {
			PushServer.logger.warn("while reading message", e);
		}
		return s;
	}
}