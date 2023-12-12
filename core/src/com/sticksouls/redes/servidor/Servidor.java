package com.sticksouls.redes.servidor;

import com.sticksouls.redes.RedUtils;
import com.sticksouls.screens.GameScreen;

public class Servidor {
	
	
	public Servidor(ServerScreen serverScreen) {
		RedUtils.hiloServidor = new HiloServidor(serverScreen);
		RedUtils.hiloServidor.start();
		//RedUtils.hiloServidor.sendMessage("connect");
	}
	
	public void closeServer() {
		RedUtils.hiloServidor.end();
		RedUtils.hiloServidor.interrupt();
	}
}
