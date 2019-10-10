package com.pantallas;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bala.Bala;
import com.bala.BalaJugador;
import com.bala.BalaMP;
import com.net.Cliente;
import com.net.Servidor;
import com.principal.Principal;
import com.tanques.Tanque;
import com.tanques.TanqueEnemigo;
import com.tanques.TanqueJugador;
import com.tanques.TanqueMP;

public class PantallaJuegoMultiplayer implements Screen
{
	Principal principal;
	
	private OrthographicCamera cam;
	private Viewport port;
	
	private TmxMapLoader cargadorMapa;
	private TiledMap mapa;
	private OrthogonalTiledMapRenderer render;
	private Tanque tank, tankMP;
	private Bala[] bala, balaMP;
	private int disparo = 0, disparoMP = 0;
	private Cliente cliente;
	private Servidor servidor;
	private boolean esServer = false, esCliente = false;
	private String tanque, canon, map;
	private String[] movs, movsBala, gover, diseno, mapaNombre;
	private boolean gameOver = false, perdiste = false, disenoAplicado = false, mapaAplicado = false;
	private Texture auxTanque, auxCanon;
	private BitmapFont fontTexto;
	
	public PantallaJuegoMultiplayer(SpriteBatch sb, Principal principal, String tanque, String canon, String map)
	{
		this.tanque = tanque;
		this.canon = canon;
		this.principal = principal;
		this.map = map;
		cam = new OrthographicCamera();
		port = new FitViewport(Principal.ANCHO, Principal.ALTO, cam);
		
		cargadorMapa = new TmxMapLoader();
		mapa = cargadorMapa.load(map);
		render = new OrthogonalTiledMapRenderer(mapa,1/10f);
		cam.position.set(0, 0, 0);
		fontTexto = new BitmapFont(Gdx.files.internal("fontTexto.fnt"), false);
		
		tank = new TanqueJugador(165,20,tanque,canon);
		tankMP = new TanqueMP(100,80);
				
		bala = new BalaJugador[5];
		balaMP = new BalaMP[5];
		for(int i=0;i<bala.length;i++)
		{
			bala[i] = new BalaJugador();
			balaMP[i] = new BalaMP();
		}
		
		if(JOptionPane.showConfirmDialog(null, "Queres correr el server?", "Server", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			servidor = new Servidor();
			servidor.start();
			esServer = true;
		}
		else
		{
			String nroIP = JOptionPane.showInputDialog("Introducir IP de host");
			cliente = new Cliente(nroIP);
			cliente.start();
			cliente.mandarData("ping".getBytes());
			esCliente = true;
			tank.x = 450;
			tank.y = 500;
		}
	}

	@Override
	public void show() 
	{
		
	}
	
	public void captarInput(float dt)
	{
		if(chequearColisionesMapaTanque() != 1)
		{
			tank.captarInputTanque(false, false);
		}
		else
		{
			if(tank.atras)
			{
				tank.captarInputTanque(true, false);
			}
			else if(tank.adelante)
			{
				tank.captarInputTanque(false, true);
			}
			else
			{
				tank.captarInputTanque(false, true);
			}
		}
		tankMP.captarInputTanque(false, false);
		bala[disparo].captarInputBala(tank.tanqueCanon.getRotation(), tank.tanque.getX(), tank.tanque.getY());
		if(Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			disparo++;
			if(disparo == 5)
			{
				disparo = 0;
			}
			if(esCliente)
			{
				if(bala[disparo] != null)
				{
					cliente.mandarData(("BALA" + "/" + "true").getBytes());
				}
			}
			else if(esServer)
			{
				if(servidor.clienteIP != null)
				{
					if(bala[disparo] != null)
					{
						servidor.mandarData(("BALA" + "/" + "true").getBytes());
					}
				}
			}
		}
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
			}
		}
	}
	
	public void chequearColisionesMapaBalaMP()
	{
		MapLayer collisionObjectLayer = mapa.getLayers().get("Colisiones");
		MapObjects objects = collisionObjectLayer.getObjects();

		for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) 
		{
			Rectangle rectangle = rectangleObject.getRectangle();
			for(int i=0;i<balaMP.length;i++)
			{
			    if (Intersector.overlaps(rectangle, balaMP[i].getRectanguloColision()))
			    {
			        balaMP[i].balaDisparada = false;
			    }
			}
		}
	}
	
	public void update(float dt)
	{
		chequearColisionesMapaBala();
		chequearColisionesMapaBalaMP();
		captarMandarPaquetes();
		if(tank.vida > 0 && tankMP.vida > 0)
		{
			captarInput(dt);
		}
		else
		{
			if(tank.vida <= 0)
			{
				perdiste = true;
				gameOver = true;
				if(esCliente)
				{
					cliente.mandarData("GOVER/false/true".getBytes());
				}
				else if(esServer)
				{
					if(servidor.clienteIP != null)
					{
						servidor.mandarData("GOVER/false/true".getBytes());
					}
				}
			}
			else if(tankMP.vida <= 0)
			{
				perdiste = false;
				gameOver = true;
				if(esCliente)
				{
					cliente.mandarData("GOVER/true/true".getBytes());
				}
				else if(esServer)
				{
					if(servidor.clienteIP != null)
					{
						servidor.mandarData("GOVER/true/true".getBytes());
					}
				}
			}
		}
		for(int i=0;i<bala.length;i++)
		{
			bala[i].colisiona(tankMP);
			balaMP[i].colisiona(tank);
		}
		
		cam.update();
		render.setView(cam);
	}

	private void captarMandarPaquetes() 
	{
		if(esCliente)
		{
			cliente.mandarData(("DISENO" + "/" + tanque + "/" + canon).getBytes());
			cliente.mandarData(("MOV" + "/" + tank.tanque.getX() + "/" + tank.tanque.getY() + "/" + tank.tanque.getRotation() + "/" + tank.tanqueCanon.getRotation()).getBytes());
			if(cliente.mensajeServidor != "")
			{
				if(cliente.mensajeServidor.contains("MOV"))
				{
					movs = cliente.mensajeServidor.split("/");
				}
				if(cliente.mensajeServidor.contains("BALA"))
				{
					movsBala = cliente.mensajeServidor.split("/");
				}
				if(cliente.mensajeServidor.contains("GOVER"))
				{
					gover = cliente.mensajeServidor.split("/");
				}
				if(cliente.mensajeServidor.contains("DISENO"))
				{
					diseno = cliente.mensajeServidor.split("/");
				}
				if(cliente.mensajeServidor.contains("MAP"))
				{
					mapaNombre = cliente.mensajeServidor.split("/");
				}
			}
			if(mapaNombre != null && !mapaAplicado)
			{
				mapa = cargadorMapa.load(mapaNombre[1].trim());
				render = new OrthogonalTiledMapRenderer(mapa,1/10f);
				mapaAplicado = true;
			}
			if(gover != null)
			{
				perdiste = Boolean.parseBoolean(gover[1]);
				gameOver = Boolean.parseBoolean(gover[2]);
			}
			if(diseno != null && !disenoAplicado)
			{
				auxTanque = new Texture(diseno[1]);
				auxCanon = new Texture(diseno[2].trim());
				tankMP.tanque.setTexture(auxTanque);
				tankMP.tanqueCanon.setTexture(auxCanon);
				disenoAplicado = true;
			}
			if(movs != null)
			{
				if(movs[0].equals("MOV"))
				{
					tankMP.x = Float.parseFloat(movs[1]);
					tankMP.y = Float.parseFloat(movs[2]);
					tankMP.tanque.setRotation(Float.parseFloat(movs[3]));
					tankMP.tanqueCanon.setRotation(Float.parseFloat(movs[4]));
				}
			}
			if(movsBala != null)
			{
				if(cliente.mensajeServidor.contains("true"))
				{
					balaMP[disparoMP].captarInputBala(tankMP.tanqueCanon.getRotation(), tankMP.tanque.getX(), tankMP.tanque.getY());
					disparoMP++;
					if(disparoMP == 5)
					{
						disparoMP = 0;
					}
				}
			}
		}
		else if(esServer)
		{
			if(servidor.clienteIP != null)
			{
				servidor.mandarData(("MAP/" + map).getBytes());
				servidor.mandarData(("DISENO/" + tanque + "/" + canon).getBytes());
				servidor.mandarData(("MOV" + "/" + tank.tanque.getX() + "/" + tank.tanque.getY() + "/" + tank.tanque.getRotation() + "/" + tank.tanqueCanon.getRotation()).getBytes());
				if(servidor.mensajeCliente != "")
				{
					if(servidor.mensajeCliente.contains("MOV"))
					{
						movs = servidor.mensajeCliente.split("/");
					}
					if(servidor.mensajeCliente.contains("BALA"))
					{
						movsBala = servidor.mensajeCliente.split("/");
					}
					if(servidor.mensajeCliente.contains("GOVER"))
					{
						gover = servidor.mensajeCliente.split("/");
					}
					if(servidor.mensajeCliente.contains("DISENO"))
					{
						diseno = servidor.mensajeCliente.split("/");
					}
				}
				if(gover != null)
				{
					perdiste = Boolean.parseBoolean(gover[1]);
					gameOver = Boolean.parseBoolean(gover[2]);
				}
				if(diseno != null && !disenoAplicado)
				{
					auxTanque = new Texture(diseno[1]);
					auxCanon = new Texture(diseno[2].trim());
					tankMP.tanque.setTexture(auxTanque);
					tankMP.tanqueCanon.setTexture(auxCanon);
					disenoAplicado = true;
				}
				if(movs != null)
				{
					if(movs[0].equals("MOV"))
					{
						tankMP.x = Float.parseFloat(movs[1]);
						tankMP.y = Float.parseFloat(movs[2]);
						tankMP.tanque.setRotation(Float.parseFloat(movs[3]));
						tankMP.tanqueCanon.setRotation(Float.parseFloat(movs[4]));
					}
				}
				if(movsBala != null)
				{
					if(servidor.mensajeCliente.contains("true"))
					{
						balaMP[disparoMP].captarInputBala(tankMP.tanqueCanon.getRotation(), tankMP.tanque.getX(), tankMP.tanque.getY());
						disparoMP++;
						if(disparoMP == 5)
						{
							disparoMP = 0;
						}
					}
				}
			}
		}
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
			balaMP[i].dibujarBala(principal.batch);
		}
		tank.dibujarTanque(principal.batch);
		tankMP.dibujarTanque(principal.batch);
		
		if(perdiste && gameOver)
		{
			fontTexto.draw(principal.batch, "PERDISTE", 10, 630);
		}
		else if(!perdiste && gameOver)
		{
			fontTexto.draw(principal.batch, "GANASTE", 10, 630);
		}
		
		principal.batch.end();
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

}
