package chatbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Servidor extends Thread {
	HashMap<String, Socket> tabla_clientes; // Para varios clientes
	Socket socket_cliente;

	public Servidor(HashMap<String, Socket> tabla_clientes, Socket socket_cliente) {
		this.tabla_clientes = tabla_clientes; // Mapeo tabla varios clientes
		this.socket_cliente = socket_cliente;
	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket_cliente.getInputStream()));
			String id_cliente = br.readLine();
			synchronized (tabla_clientes) {
				System.out.println("Conectando con un nuevo cliente: " + id_cliente);
				tabla_clientes.put(id_cliente, socket_cliente);
			}
			String id_destino = br.readLine();
			while (id_destino != null) { // mientras reciba respuesta del cliente
				String mensaje = br.readLine();
				synchronized (tabla_clientes) {
					Socket socket_destino = tabla_clientes.get(id_destino);
					if (socket_destino != null) {
						System.out.println("Retransmitiendo mensaje de " + id_cliente + " a " + id_destino);
						PrintWriter pw = new PrintWriter(socket_destino.getOutputStream(), true);
						pw.println(id_cliente + ": " + mensaje);
					}
				}
				id_destino = br.readLine();
			}
			synchronized (tabla_clientes) { // Cuando un cliente se desconecta
				System.out.println("El cliente " + id_cliente + " se ha desconectado");
				tabla_clientes.remove(id_cliente);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(); // Creación del socket para activar servidor
			InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
			serverSocket.bind(addr); // Bind para la escucha por ese puerto
			HashMap<String, Socket> tabla_clientes = new HashMap<String, Socket>();
			while (true) {
				Socket newSocket = serverSocket.accept();
				Servidor nuevo_thread = new Servidor(tabla_clientes, newSocket);
				nuevo_thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
