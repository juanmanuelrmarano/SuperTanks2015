package com.tanques;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.graphics.Texture;

public class TanqueEnemigo extends Tanque
{
	Timer segMov;
	private int accion = 0, moverTanque = 0, rotarTanque = 0, rotarCanon = 0;
	private Random ran;

	public TanqueEnemigo(float x, float y) 
	{
		super(x, y);
		segMov = new Timer(true);
		ran = new Random();
		tanqueText = new Texture("tank_rojo.png");
		tanqueCanonText = new Texture("tank_rojo_canon.png");
		this.tanque.setTexture(tanqueText);
		this.tanqueCanon.setTexture(tanqueCanonText);
	}
	
	private void moverAlAzar()
	{
		segMov.scheduleAtFixedRate(
		new TimerTask() {
			public void run() 
			{
				try 
				{
					Thread.sleep(1000);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
				moverTanque = 0;
				rotarTanque = 0;
				rotarCanon = 0;
				accion = ran.nextInt(3);
				switch(accion)
				{
				case 0:
					moverTanque = ran.nextInt(2);
					break;
				case 1:
					rotarTanque = ran.nextInt(3);
					break;
				case 2:
					rotarCanon = ran.nextInt(3);
					break;
				}
		    }
			}, 0, 100);
	}

	@Override
	public void captarInputTanque(boolean destrabeAtras, boolean destrabeAdelante) 
	{
		float aumentoX = 0, aumentoY = 0;
		
		if(vida > 0)
		{
			moverAlAzar();
			if(rotarTanque == 2)
			{
				tanqueCanon.rotate(-velocidadRotacion);
				tanque.rotate(-velocidadRotacion);
				rotacion += velocidadRotacion;
			}
			if(rotarTanque == 1)
			{
				tanqueCanon.rotate(velocidadRotacion);
				tanque.rotate(velocidadRotacion);
				rotacion -= velocidadRotacion;
			}
			if(tanque.getX() + 2 <= 765)
			{
				if(tanque.getX() + 2 >= 7)
				{
					if(tanque.getY() + 2 <= 605)
					{
						if(tanque.getY() + 2 >= 7)
						{
							if(moverTanque == 0 || destrabeAtras)
							{
								aumentoX = - (float) (Math.cos(Math.toRadians(rotacion)) * velocidad);
								aumentoY = (float) (Math.sin(Math.toRadians(rotacion)) * velocidad);
								x += aumentoX;
								y += aumentoY;
								if(!destrabeAtras)
								{
									adelante = true;
									atras = false;
								}
							}
							if(moverTanque == 1 || destrabeAdelante)
							{
								aumentoX = (float) (Math.cos(Math.toRadians(rotacion)) * velocidad);			
								aumentoY = - (float) (Math.sin(Math.toRadians(rotacion)) * velocidad);
								x += aumentoX;
								y += aumentoY;
								if(!destrabeAdelante)
								{
									adelante = false;
									atras = true;
								}
							}
						}
						else
						{
							y = y + 1;
						}
					}
					else
					{
						y = y - 1;
					}
				}
				else
				{
					x = x + 1;
				}
			}
			else
			{
				x = x - 1;
			}
			if(rotarCanon == 2)
			{
				tanqueCanon.rotate(-4);
			}
			if(rotarCanon == 1)
			{
				tanqueCanon.rotate(4);
			}
			tanque.setPosition(x, y);
			tanqueCanon.setPosition(x, y);
			rectanguloColision.set(x+1,y+2,28f,28f);
		}
	}

}
