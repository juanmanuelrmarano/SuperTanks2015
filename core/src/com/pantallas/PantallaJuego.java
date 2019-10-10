package com.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bala.Bala;
import com.bala.BalaEnemigo;
import com.bala.BalaJugador;
import com.principal.Principal;
import com.tanques.Tanque;
import com.tanques.TanqueEnemigo;
import com.tanques.TanqueJugador;

public class PantallaJuego extends Game implements Screen
{
	Principal principal;
	
	private OrthographicCamera cam;
	private Viewport port;
	
	private TmxMapLoader cargadorMapa;
	private TiledMap mapa;
	private OrthogonalTiledMapRenderer render;
	
	private Tanque tank, tank2, tank3;
	private Bala[] bala, bala2, bala3;
	private int disparo = 0, disparo2 = 0, disparo3 = 0;
	
	private ShapeRenderer shapeRender;

	private BitmapFont fontTexto;

	private boolean perdiste = false, gameOver = false;
	
	public PantallaJuego(SpriteBatch sb, Principal principal, String tanque, String canon, String map)
	{
		this.principal = principal;
		cam = new OrthographicCamera();
		port = new FitViewport(Principal.ANCHO, Principal.ALTO, cam);
		
		cargadorMapa = new TmxMapLoader();
		mapa = cargadorMapa.load(map);
		render = new OrthogonalTiledMapRenderer(mapa,1/10f);
		cam.position.set(0, 0, 0);
		fontTexto = new BitmapFont(Gdx.files.internal("fontTexto.fnt"), false);
		
		tank = new TanqueJugador(165,20,tanque,canon);
		tank2 = new TanqueEnemigo(400,200);
		tank3 = new TanqueEnemigo(165,400);
		bala = new BalaJugador[5];
		bala2 = new BalaEnemigo[5];
		bala3 = new BalaEnemigo[5];
		for(int i=0;i<bala.length;i++)
		{
			bala[i] = new BalaJugador();
			bala2[i] = new BalaEnemigo();
			bala3[i] = new BalaEnemigo();
		}
		
		shapeRender = new ShapeRenderer();
	}

	@Override
	public void show() 
	{
		
	}
	
	public int chequearColisionesMapaTanque()
	{
		MapLayer collisionObjectLayer = mapa.getLayers().get("Colisiones");
		MapObjects objects = collisionObjectLayer.getObjects();

		for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) 
		{
			Rectangle rectangle = rectangleObject.getRectangle();
		    if (Intersector.overlaps(rectangle, tank.getRectanguloColision()))
		    {
		        return 1;
		    }
		}
		return 0;
	}
	
	public void chequearColisionesMapaBala()
	{
		MapLayer collisionObjectLayer = mapa.getLayers().get("Colisiones");
		MapObjects objects = collisionObjectLayer.getObjects();

		for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) 
		{
			Rectangle rectangle = rectangleObject.getRectangle();
			for(int i=0;i<bala.length;i++)
			{
			    if (Intersector.overlaps(rectangle, bala[i].getRectanguloColision()))
			    {
			        bala[i].balaDisparada = false;
			    }
			    if (Intersector.overlaps(rectangle, bala2[i].getRectanguloColision()))
			    {
			        bala2[i].balaDisparada = false;
			    }
			    if (Intersector.overlaps(rectangle, bala3[i].getRectanguloColision()))
			    {
			        bala3[i].balaDisparada = false;
			    }
			}
		}
	}
	
	public void captarInput(float dt)
	{
		if(chequearColisionesMapaTanque() != 1)
		{
			tank.captarInputTanque(false, false);
			tank2.captarInputTanque(false, false);
			tank3.captarInputTanque(false, false);
		}
		else
		{
			if(tank.atras)
			{
				tank.captarInputTanque(true, false);
				tank2.captarInputTanque(true, false);
				tank3.captarInputTanque(true, false);
			}
			else if(tank.adelante)
			{
				tank.captarInputTanque(false, true);
				tank2.captarInputTanque(false, true);
				tank3.captarInputTanque(false, true);
			}
		}
		if(tank.vida > 0)
		{
			bala[disparo].captarInputBala(tank.tanqueCanon.getRotation(), tank.tanque.getX(), tank.tanque.getY());
		}
		if(tank2.vida > 0)
		{
			bala2[disparo2].captarInputBala(tank2.tanqueCanon.getRotation(), tank2.tanque.getX(), tank2.tanque.getY());
		}
		if(tank3.vida > 0)
		{
			bala3[disparo3].captarInputBala(tank3.tanqueCanon.getRotation(), tank3.tanque.getX(), tank3.tanque.getY());
		}
		if(bala2[disparo2].balaDisparada)
		{
			disparo2++;
			if(disparo2 == 5)
			{
				disparo2 = 0;
			}
		}
		if(bala3[disparo3].balaDisparada)
		{
			disparo3++;
			if(disparo3 == 5)
			{
				disparo3 = 0;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			disparo++;
			if(disparo == 5)
			{
				disparo = 0;
			}
		}
	}
	
	public void update(float dt)
	{
		chequearColisionesMapaBala();
		captarInput(dt);
		for(int i=0;i<bala.length;i++)
		{
			bala[i].colisiona(tank2);
			bala[i].colisiona(tank3);
			bala2[i].colisiona(tank);
			bala3[i].colisiona(tank);
		}
		
		cam.update();
		render.setView(cam);
	}

	@Override
	public void render(float delta) 
	{
		update(delta);
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		render.render();
		
		principal.batch.begin();
		
		for(int i=0;i<bala.length;i++)
		{
			bala[i].dibujarBala(principal.batch);
			bala2[i].dibujarBala(principal.batch);
			bala3[i].dibujarBala(principal.batch);
		}
		tank.dibujarTanque(principal.batch);
		tank2.dibujarTanque(principal.batch);
		tank3.dibujarTanque(principal.batch);
		
		if(perdiste && gameOver)
		{
			fontTexto.draw(principal.batch, "PERDISTE", 10, 630);
		}
		else if(!perdiste && gameOver)
		{
			fontTexto.draw(principal.batch, "GANASTE", 10, 630);
		}
		
		principal.batch.end();
		
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(Color.BLACK);
		shapeRender.end();
	}

	@Override
	public void resize(int width, int height)
	{
		cam.setToOrtho(false, width/10,height/10);
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
		mapa.dispose();
		render.dispose();
	}

	@Override
	public void create() 
	{
	}

}
