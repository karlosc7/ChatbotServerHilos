package chatbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatBot {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner num = new Scanner(System.in);
		try {
			Socket clientSocket = new Socket();
			InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
			clientSocket.connect(addr);
			PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
// Enviando su identificador
			pw.println("ChatBot");
// Enviando un mensaje para el cliente 1
			pw.println("cliente1");
			pw.println("Elige entre una de estas opciones: 1.Saludo. 2.Bienvenida 3.Despedida 4.Casual 5.Jerga");
			int number = num.nextInt();

			if (number == 5) {
				System.out.println("Hola");
				if (number == 4) {
					System.out.println("Bienvenido");
					if (number == 3) {
						System.out.println("Hasta pronto");
						if (number == 2) {
							System.out.println("Cómo vas?");
						}
					}
				}
			} else {
				System.out.println("Qué pasa tronco!");
			}
			while (clientSocket.isConnected())
				System.out.println(br.readLine());
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
