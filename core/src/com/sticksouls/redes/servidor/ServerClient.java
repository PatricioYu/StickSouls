package com.sticksouls.redes.servidor;

import java.net.InetAddress;

public class ServerClient {
	
	public InetAddress ip;
	public int port;
	
	public ServerClient(InetAddress ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	
}
