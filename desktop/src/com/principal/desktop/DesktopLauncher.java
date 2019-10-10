package com.principal.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.principal.Principal;

public class DesktopLauncher {
	public static void main (String[] arg)	
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Principal.TITULO;
		config.width = Principal.ANCHO;
		config.height = Principal.ALTO; 
		new LwjglApplication(new Principal(), config);
	}
}
