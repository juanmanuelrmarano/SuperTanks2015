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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.principal.Principal;

public class PantallaMenu implements Screen
{
	private OrthographicCamera cam;
	private Viewport port;
	public Stage sta;
	
	Principal principal;
	TextButtonStyle estilo;
	BitmapFont font;
	BitmapFont fontTexto;
	Skin skinBotones;
	Table tabla;
	
	TextButton btnSP;
	TextButton btnMP;
	
	public PantallaMenu(final Principal principal, final SpriteBatch sb)
	{
		this.principal = principal;
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
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
		
		btnSP = new TextButton("Singleplayer", estilo);
		btnMP = new TextButton("Multiplayer", estilo);
		
		btnSP.addListener(new ClickListener() 
		{
            public void clicked (InputEvent event, float x, float y) 
            {
            	principal.setScreen(new PantallaMenuPartida(sb, principal,false));
            }
        });
		
		btnMP.addListener(new ClickListener() 
		{
            public void clicked (InputEvent event, float x, float y) 
            {
            	principal.setScreen(new PantallaMenuPartida(sb, principal,true));
            }
        });
		
		tabla = new Table();
		tabla.top();
		tabla.setFillParent(true);
		tabla.add(btnSP).size(200, 100).padTop(250);
		tabla.add(btnMP).size(200, 100).padTop(250).padLeft(100);
		
		sta.addActor(tabla);
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
