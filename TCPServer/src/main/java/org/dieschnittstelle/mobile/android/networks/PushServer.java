package org.dieschnittstelle.mobile.android.networks;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Weitgehend unkommentierte Implementierung eines rudimentären Push Servers
 * 
 * @author N.N.
 * @author Martin Schafföner
 */
public class PushServer {

	protected static Logger logger = LoggerFactory.getLogger(PushServer.class);

	private static final int PORT = 1234;

	public static void main(String[] args) {
		PushServer client = new PushServer();
		client.doit();
	}

	private void doit() {
		try (ServerSocket serverSock = new ServerSocket(PORT)) {

			final PushSender pushSender = new PushSender();
			new Thread(pushSender).start();

			while (true) {
				logger.info("Waiting for a client...");
				// Achtung: sobald sich ein neuer Client mit dem Server
				// verbindet, werden Meldungen nur noch an diesen geschickt, da
				// der PushSender die Instanzvariable clientSocket für den
				Socket clientSocket = serverSock.accept();
				clientSocket.setSoTimeout(60000);
				pushSender.setClientSocket(clientSocket);
				// Versand von Nachrichten nutzt.
				new SocketObserver(clientSocket).run();
				clientSocket.close();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
