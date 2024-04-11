package org.dieschnittstelle.mobile.android.networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketObserver implements Runnable {

	private BufferedReader input;

	public SocketObserver(Socket socket) {
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			PushServer.logger.error("got exception trying to obtain input stream from socket: " + e, e);
		}
	}

	public void run() {
		String line;

		try {
			while ((line = input.readLine()) != null) {
				PushServer.logger.info("got input: " + line);
			}
		} catch (IOException t) {
			String err = "got exception on observer run";
			PushServer.logger.warn(err, t);
			try {
				input.close();
			} catch (IOException e) {
				PushServer.logger.warn("caught exception", e);
			}
		}
	}

}