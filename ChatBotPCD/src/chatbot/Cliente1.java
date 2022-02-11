package chatbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Cliente1 {
	public static void main(String[] args) {
		try {
			Socket clientSocket = new Socket(); // Estableciendo conexión con el servidor
			InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
			clientSocket.connect(addr);
			PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
// Enviando su identificador
			pw.println("cliente1");
			while (clientSocket.isConnected())
				System.out.println(br.readLine());
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
