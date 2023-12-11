package com.sticksouls.redes.cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.badlogic.gdx.math.Vector2;
import com.sticksouls.enums.Directions;
import com.sticksouls.redes.RedUtils;
import com.sticksouls.redes.cliente.juego.GameScreenClient;

public class HiloCliente extends Thread {
	
	private GameScreenClient gameScreen;
	private ClientScreen clientScreen;
	private boolean end = false;
	private boolean connected = false;

	private DatagramSocket socket;
	private InetAddress ipServer;
	private int port = RedUtils.port;
	
	public HiloCliente(ClientScreen clientScreen) {
		this.clientScreen = clientScreen;

		try {
			socket = new DatagramSocket();
			ipServer = InetAddress.getByName("255.255.255.255");//Broadcast

		} catch (SocketException | UnknownHostException e) {
			end = true;
			System.out.println("fallo al conectar con el servidor");
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
				System.out.println("fallo al recibir el mensaje");
			}
		}
		socket.close();
	}

	private void readMessage(DatagramPacket dp) {
		String msg = new String(dp.getData()).trim(); // trim() delete spaces
		String[] message = msg.split("#");
		
		switch(message[0]) {
		case "connected":
			connected = true;
			ipServer = dp.getAddress(); // Server ip
			
			System.out.println("Cliente conectado");
			
			break;
		case "ready":
			
			clientScreen.ready(Boolean.parseBoolean(message[1]));

			break;
		case "serverDisconnected": 
			end();
			break;
			
		case "movement":
			Vector2 linearVelocity = new Vector2(Float.parseFloat(message[1]), Float.parseFloat(message[2]));
			gameScreen.player2.movement(linearVelocity);
			break;
			
		case "attack":
			gameScreen.player2.attack(Directions.valueOf(message[1]));
			break;
			
			
		default:
			System.out.println("Mensaje[0] mal mandado: " + message[0]);
			break;
	
		}
	}
	
	public void sendMessage(String msg) {
		byte[] message = msg.getBytes();
		
		try {
			DatagramPacket dp = new DatagramPacket(message, message.length, ipServer, port);
			socket.send(dp);
		}catch(IOException e) {
			System.out.println("fallo al mandar el mensaje");
		}
	}
	
	public void end() {
		end = true;
		socket.close();
	}
	
	public void setGame(GameScreenClient gameScreen) {
		this.gameScreen = gameScreen;
	}

}
