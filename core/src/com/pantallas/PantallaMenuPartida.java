package com.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.principal.Principal;

public class PantallaMenuPartida implements Screen
{
	private OrthographicCamera cam;
	private Viewport port;
	public Stage sta;
	Principal principal;
	SpriteBatch sb;
	
	TextButtonStyle estilo;
	BitmapFont font;
	BitmapFont fontTexto;
	Skin skinBotones;
	
	Texture tankRojo, tankAmarillo, tankAzul, tankVerde;
	Texture canonRojo, canonAmarillo, canonAzul, canonVerde;
	Texture choc, desierto, islandia, jardin, leningrado, prueba, stalingrado;
	int numeroTank = 0, numeroCanon = 0, numeroMapa = 0;
	String tanque, canon, map;
	
	TextButton btnSeleccionTankIzq;
	TextButton btnSeleccionTankDer;
	TextButton btnSeleccionCanonIzq;
	TextButton btnSeleccionCanonDer;
	TextButton btnSeleccionMapaDer;
	TextButton btnSeleccionMapaIzq;
	TextButton btnComenzar;
	
	public PantallaMenuPartida(final SpriteBatch sb, final Principal principal, final boolean multiplayer)
	{
		this.sb = sb;
		this.principal = principal;
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		choc = new Texture("Choc.png");
		desierto = new Texture("Desierto.png");
		islandia = new Texture("Islandia.png");
		jardin = new Texture("Jardin.png");
		leningrado = new Texture("Leningrado.png");
		prueba = new Texture("Prueba.png");
		stalingrado = new Texture("Stalingrado.png");
		
		canonRojo = new Texture("tank_rojo_canon.png");
		canonAmarillo = new Texture("tank_amarillo_canon.png");
		canonAzul = new Texture("tank_azul_canon.png");
		canonVerde = new Texture("tank_verde_canon.png");
		
		tankRojo = new Texture("tank_rojo.png");
		tankAzul = new Texture("tank_azul.png");
		tankAmarillo = new Texture("tank_amarillo.png");
		tankVerde = new Texture("tank_verde.png");
		
		cam = new OrthographicCamera();
		font = new BitmapFont(Gdx.files.internal("buttonsfont.fnt"), false);
		fontTexto = new BitmapFont(Gdx.files.internal("fontTexto.fnt"), false);
		port = new FitViewport(Principal.ANCHO,Principal.ALTO,cam);
		sta = new Stage(port,sb);
		estilo = new TextButtonStyle();
		
		skinBotones = new Skin();
		skinBotones.add("skin", new Texture("BotonSkin.png"));
		
		estilo.up = skinBotones.getDrawable("skin");
		estilo.font = font;
		
		btnSeleccionTankDer = new TextButton(">", estilo);
		btnSeleccionTankIzq = new TextButton("<", estilo);
		btnSeleccionCanonDer = new TextButton(">", estilo);
		btnSeleccionCanonIzq = new TextButton("<", estilo);
		btnSeleccionMapaDer = new TextButton(">", estilo);
		btnSeleccionMapaIzq = new TextButton("<", estilo);
		btnComenzar = new TextButton("Comenzar", estilo);
		
		btnComenzar.addListener(new ClickListener() 
		{
            public void clicked (InputEvent event, float x, float y) 
            {
            	if(multiplayer)
            	{
            		principal.setScreen(new PantallaJuegoMultiplayer(sb, principal,tanque,canon,map));
            	}
            	else
            	{
            		principal.setScreen(new PantallaJuego(sb, principal,tanque,canon,map));
            	}
            }
        });
		
		btnSeleccionTankDer.addListener(new ClickListener() 
		{
            public void clicked (InputEvent event, float x, float y) 
            {
            	if(numeroTank >= 0 && numeroTank < 3)
            	{
            		numeroTank++;
            	}
            }
        });
		
		btnSeleccionTankIzq.addListener(new ClickListener() 
		{
            public void clicked (InputEvent event, float x, float y) 
            {
            	if(numeroTank > 0 && numeroTank <= 3)
            	{
            		numeroTank--;
            	}
            }
        });
		
		btnSeleccionMapaDer.addListener(new ClickListener() 
		{
            public void clicked (InputEvent event, float x, float y) 
            {
            	if(numeroMapa >= 0 && numeroMapa < 6)
            	{
            		numeroMapa++;
            	}
            }
        });
		
		btnSeleccionMapaIzq.addListener(new ClickListener() 
		{
            public void clicked (InputEvent event, float x, float y) 
            {
            	if(numeroMapa > 0 && numeroMapa <= 6)
            	{
            		numeroMapa--;
            	}
            }
        });
		
		btnSeleccionCanonDer.addListener(new ClickListener() 
		{
            public void clicked (InputEvent event, float x, float y) 
            {
            	if(numeroCanon >= 0 && numeroCanon < 3)
            	{
            		numeroCanon++;
            	}
            }
        });
		
		btnSeleccionCanonIzq.addListener(new ClickListener() 
		{
            public void clicked (InputEvent event, float x, float y) 
            {
            	if(numeroCanon > 0 && numeroCanon <= 3)
            	{
            		numeroCanon--;
            	}
            }
        });
		
		sta.addActor(btnSeleccionTankDer);
		sta.addActor(btnSeleccionTankIzq);
		sta.addActor(btnSeleccionCanonDer);
		sta.addActor(btnSeleccionCanonIzq);
		sta.addActor(btnSeleccionMapaDer);
		sta.addActor(btnSeleccionMapaIzq);
		sta.addActor(btnComenzar);
		
		btnSeleccionTankDer.setSize(50,50);
		btnSeleccionTankIzq.setSize(50,50);
		btnSeleccionTankDer.setPosition(400, 440);
		btnSeleccionTankIzq.setPosition(300, 440);
		
		btnSeleccionCanonDer.setSize(50,50);
		btnSeleccionCanonIzq.setSize(50,50);
		btnSeleccionCanonDer.setPosition(400,240);
		btnSeleccionCanonIzq.setPosition(300,240);
		
		btnSeleccionMapaDer.setSize(50,50);
		btnSeleccionMapaIzq.setSize(50,50);
		btnSeleccionMapaDer.setPosition(400, 10);
		btnSeleccionMapaIzq.setPosition(300, 10);
		
		btnComenzar.setPosition(650,10);
		
	}
	
	@Override
	public void show() 
	{
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.input.setInputProcessor(sta);
		
		principal.batch.setProjectionMatrix(cam.combined);
		sta.draw();
		
		principal.batch.begin();
		fontTexto.draw(principal.batch, "Seleccionar color del tanque", 50, 600);
		switch(numeroTank)
		{
			case 0:
				principal.batch.draw(tankRojo, 360, 500);
				tanque = "tank_rojo.png";
				break;
			case 1:
				principal.batch.draw(tankAmarillo, 360, 500);
				tanque = "tank_amarillo.png";
				break;
			case 2:
				principal.batch.draw(tankAzul, 360, 500);
				tanque = "tank_azul.png";
				break;
			case 3:
				principal.batch.draw(tankVerde, 360, 500);
				tanque = "tank_verde.png";
				break;
		}
		
		fontTexto.draw(principal.batch, "Seleccionar color del canon", 50, 400);
		switch(numeroCanon)
		{
			case 0:
				principal.batch.draw(canonRojo, 360, 300);
				canon = "tank_rojo_canon.png";
				break;
			case 1:
				principal.batch.draw(canonAmarillo, 360, 300);
				canon = "tank_amarillo_canon.png";
				break;
			case 2:
				principal.batch.draw(canonAzul, 360, 300);
				canon = "tank_azul_canon.png";
				break;
			case 3:
				principal.batch.draw(canonVerde, 360, 300);
				canon = "tank_verde_canon.png";
				break;
		}
		
		fontTexto.draw(principal.batch, "Seleccionar mapa", 50, 210);
		switch(numeroMapa)
		{
			case 0:
				principal.batch.draw(choc, 325, 70, 100, 100);
				map = "Choc.tmx";
				break;
			case 1:
				principal.batch.draw(desierto, 325, 70, 100, 100);
				map = "Desierto.tmx";
				break;
			case 2:
				principal.batch.draw(islandia, 325, 70, 100, 100);
				map = "Islandia.tmx";
				break;
			case 3:
				principal.batch.draw(jardin, 325, 70, 100, 100);
				map = "Jardin.tmx";
				break;
			case 4:
				principal.batch.draw(leningrado, 325, 70, 100, 100);
				map = "Leningrado.tmx";
				break;
			case 5:
				principal.batch.draw(prueba, 325, 70, 100, 100);
				map = "mapaPrueba.tmx";
				break;
			case 6:
				principal.batch.draw(stalingrado, 325, 70, 100, 100);
				map = "Stalingrado.tmx";
				break;
		}
		
		principal.batch.end();
	}

	@Override
	public void resize(int width, int height)
	{
		port.update(width,height);
		sta.getViewport().update(width, height, true);
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume() 
	{
	}

	@Override
	public void hide() 
	{
	}

	@Override
	public void dispose() 
	{
	}

}
