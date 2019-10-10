package com.bala;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.tanques.Tanque;

public abstract class Bala 
{
	private Texture disparoText;
	public Sprite disparo;
	public float balaX = 0, balaY = 0;
	public float velocidadBala = 4f;
	public boolean balaDisparada = false;
	protected Rectangle rectanguloColision;
	
	public Bala()
	{
		disparoText = new Texture("shot.png");
		disparo = new Sprite(disparoText);
		rectanguloColision = new Rectangle();
		disparo.setRotation(0);
	}
	
	public abstract void captarInputBala(float rotacionCanon, float tanqueX, float tanqueY);
	
	public void dibujarBala(SpriteBatch batch)
	{
		float aumentoX = 0, aumentoY = 0;
		
		if(balaDisparada == true)
		{
			disparo.draw(batch);
			aumentoX = - (float) (Math.cos(Math.toRadians(disparo.getRotation())) * velocidadBala);			
			aumentoY = (float) (Math.sin(Math.toRadians(disparo.getRotation())) * velocidadBala);
			balaX += aumentoX;
			balaY += aumentoY;
			disparo.setPosition(balaX, balaY);
			rectanguloColision.set(balaX,balaY,2f,2f);
		}
	}
	
    public Rectangle getRectanguloColision() 
    {
        return rectanguloColision;
    }
    
    public void colisiona(Tanque tank)
    {
    	if(Intersector.overlaps(getRectanguloColision(), tank.getRectanguloColision()))
    	{
    		tank.vida -= 1;
    	}
    }
}
