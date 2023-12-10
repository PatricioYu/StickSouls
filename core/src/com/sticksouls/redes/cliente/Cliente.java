package com.sticksouls.redes.cliente;

import com.sticksouls.redes.RedUtils;
import com.sticksouls.screens.GameScreen;

public class Cliente {
	
	
	public Cliente(ClientScreen clientScreen) {
		RedUtils.hiloCliente = new HiloCliente(clientScreen);
		RedUtils.hiloCliente.start();
		RedUtils.hiloCliente.sendMessage("connect");
	}
	
	public void closeClient() {
		RedUtils.hiloCliente.end();
		RedUtils.hiloCliente.interrupt();
	}
	
}
