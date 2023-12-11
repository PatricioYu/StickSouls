package com.sticksouls.redes.servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.badlogic.gdx.math.Vector2;
import com.sticksouls.enums.Directions;
import com.sticksouls.redes.RedUtils;

public class HiloServidor extends Thread {
	
	private GameScreenServer gameScreen;
	private ServerScreen serverScreen;
	private ConsolaDebug consola;
	private ServerClient[] clients;
	private DatagramSocket socket;
	
	private int contConnections = 0;
	private int maxConections = 2;
	private boolean end = false;
	
	public HiloServidor(ServerScreen serverScreen, ConsolaDebug consola) {
		
		clients = new ServerClient[maxConections];
		this.serverScreen = serverScreen;
		
		try {
			this.consola = consola;
			socket = new DatagramSocket(RedUtils.port);
			
			consola.agregarMensajes("Servidor iniciado");

		} catch (SocketException e) {
			end = true;
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(!end) {
			byte[] data = new byte[1024];
			DatagramPacket dp = new DatagramPacket(data, data.length);
		
			try {
				socket.receive(dp);
				readMessage(dp);
			}catch(IOException e) {
				System.out.println("Error al recibir el mensaje");
			}
		}

		if(!socket.isClosed()) {
			sendAllMessage("serverDisconected");			
		}
		socket.close();
	}

	private void readMessage(DatagramPacket dp) {
		String msg = new String(dp.getData()).trim();		
		String[] message = msg.split("#");
		
		switch(message[0]) {
		case "connect":
			if(contConnections < 2) {
				clients[contConnections] = new ServerClient(dp.getAddress(), dp.getPort());
				consola.agregarMensajes("Conectado: " + dp.getAddress()+" "+ dp.getPort());
				sendMessage("connected", clients[contConnections].ip, clients[contConnections].port);
				
				contConnections++;
			}
			else {
				consola.agregarMensajes("Sala llena");
				sendMessage("serverFull", dp.getAddress(), dp.getPort());
			}
			
			if(contConnections == 2) {
				for(int i = 0; i < clients.length; i++) {
					
					if(i == 0) {
						sendMessage("ready#true", clients[0].ip, clients[0].port);
					}else {
						sendMessage("ready#false", clients[1].ip, clients[1].port);						
					}
					
				}
				
				
				serverScreen.ready();
			}
			break;
			
		case "movement":

			Vector2 linearVelocity = new Vector2(Float.parseFloat(message[1]), Float.parseFloat(message[2]));
			
			//Para hacerlo escalable tendria que tener un for y almacenar a los personajes en un ArrayList
			
			
			if(Boolean.parseBoolean(message[3])) {
				gameScreen.player1.movement(linearVelocity);
				sendMessage("movement#" + linearVelocity.x + "#" + linearVelocity.y, clients[1].ip, clients[1].port);
			}
			else{
				gameScreen.player2.movement(linearVelocity);			
				sendMessage("movement#" + linearVelocity.x + "#" + linearVelocity.y, clients[0].ip, clients[0].port);
			}
			break;
			
			
		case "attack":
			if(Boolean.parseBoolean(message[2])) {
				gameScreen.player1.attack(Directions.valueOf(message[1]));
				sendMessage("attack#" + Directions.valueOf(message[1]), clients[1].ip, clients[1].port);
			}
			else{
				gameScreen.player2.attack(Directions.valueOf(message[1]));
				sendMessage("attack#" + Directions.valueOf(message[1]), clients[0].ip, clients[0].port);
			}
		break;
		
		default:
			System.out.println("Mensaje[0] mal mandado: " + message[0]);
			break;

		}
		
				
	}
		
	public void sendMessage(String msg, InetAddress destinationIp, int port) {	
		byte[] message = msg.getBytes();
		
		try {
			DatagramPacket dp = new DatagramPacket(message, message.length, destinationIp, port);
			socket.send(dp);
		} catch (IOException e) {
			System.out.println("error al mandar un mensaje");
			//e.printStackTrace();
		}
	}
	
	public void sendAllMessage(String msg) {
		byte[] mensaje = msg.getBytes();
		try {
			for(int i = 0; i < contConnections;i++) {				
				DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, clients[i].ip, clients[i].port);
				socket.send(dp);
			}
		} catch (IOException e) {
			System.out.println("error al mandar mensaje a todos");
			e.printStackTrace();
		}
	}
	
	public void end() {
		end = true;
		socket.close();
	}
	
	public void setGame(GameScreenServer gameScreen) {
		this.gameScreen = gameScreen;
	}
	
}
