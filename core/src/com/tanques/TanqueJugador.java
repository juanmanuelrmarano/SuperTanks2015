package com.tanques;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

public class TanqueJugador extends Tanque
{

	public TanqueJugador(float x, float y, String tank, String canon) 
	{
		super(x, y);
		tanqueText = new Texture(tank);
		tanqueCanonText = new Texture(canon);
		this.tanque.setTexture(tanqueText);
		this.tanqueCanon.setTexture(tanqueCanonText);
	}

	@Override
	public void captarInputTanque(boolean destrabeAtras, boolean destrabeAdelante) 
	{
		float aumentoX = 0, aumentoY = 0;
		
		if(vida >0)
		{
			if(Gdx.input.isKeyPressed(Keys.D))
			{
				tanqueCanon.rotate(-velocidadRotacion);
				tanque.rotate(-velocidadRotacion);
				rotacion += velocidadRotacion;
			}
			if(Gdx.input.isKeyPressed(Keys.A))
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
							if(Gdx.input.isKeyPressed(Keys.W) || destrabeAtras)
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
							if(Gdx.input.isKeyPressed(Keys.S) || destrabeAdelante)
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
			if(Gdx.input.isKeyPressed(Keys.RIGHT))
			{
				tanqueCanon.rotate(-4);
			}
			if(Gdx.input.isKeyPressed(Keys.LEFT))
			{
				tanqueCanon.rotate(4);
			}
			tanque.setPosition(x, y);
			tanqueCanon.setPosition(x, y);
			rectanguloColision.set(x+1,y+2,28f,28f);
		}
	}	
}
