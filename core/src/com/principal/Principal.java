package com.principal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pantallas.PantallaMenu;

public class Principal extends Game 
{
	public SpriteBatch batch;
	public static final int ANCHO = 800;
	public static final int ALTO = 640;
	public static int nroClase = 0;
	public static final String TITULO = "Proyecto Prog. Sobre Redes - 3er trimestre (Rodriguez, Febles)";
	
	@Override
	public void create () 
	{
		batch = new SpriteBatch();
		setScreen(new PantallaMenu(this, batch));
	}

	@Override
	public void render () 
	{
		super.render();
	}
	
	@Override
	public void dispose()
	{
		  
	}
}
