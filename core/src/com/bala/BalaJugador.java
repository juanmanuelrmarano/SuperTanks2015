package com.bala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class BalaJugador extends Bala
{

	public void captarInputBala(float rotacionCanon, float tanqueX, float tanqueY)
	{
		if(Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			balaDisparada = true;
			disparo.setPosition(tanqueX, tanqueY);
			balaX = tanqueX;
			balaY = tanqueY;
			disparo.setRotation(-rotacionCanon + 90f);
		}
	}

}
