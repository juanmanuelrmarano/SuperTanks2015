package com.tanques;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Tanque 
{
	protected Texture tanqueText, tanqueCanonText;
	public Sprite tanque, tanqueCanon;
	public float x;
	public float y;
	public float rotacion = 90f;
	public boolean adelante = false;
	public boolean atras = false;
	public int vida = 100;
	protected float velocidadRotacion = 4f;
	protected float velocidad = 1f;
	public Rectangle rectanguloColision;
	
	public abstract void captarInputTanque(boolean destrabeAtras, boolean destrabeAdelante);
	
	public Tanque(float x, float y)
	{
		this.x = x;
		this.y = y;
		tanqueText = new Texture("tank_verde.png");
		tanqueCanonText = new Texture("tank_verde_canon.png");
		tanque = new Sprite(tanqueText);
		tanqueCanon = new Sprite(tanqueCanonText);
		rectanguloColision = new Rectangle();
		tanque.setPosition(x, y);
		tanqueCanon.setPosition(x, y);
		rectanguloColision.set(x+1,y+2,28f,28f);
	}
	
	public void dibujarTanque(SpriteBatch batch)
	{
		if(vida > 0)
		{
			tanque.draw(batch);
			tanqueCanon.draw(batch);
		}
	}
	
	public void actualizarTanque()
	{
		
	}
	
    public Rectangle getRectanguloColision()
    {
        return rectanguloColision;
    }
    
    /*public boolean colisionaOtroTanqueJugador(Tanque tanque)
    {
    	if(Intersector.overlaps(getCirculoColision(), tanque.getCirculoColision()))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }*/
}
