package com.tanques;

import com.badlogic.gdx.graphics.Texture;


public class TanqueMP extends Tanque {

	public TanqueMP(float x, float y) 
	{
		super(x, y);
		tanqueText = new Texture("tank_rojo.png");
		tanqueCanonText = new Texture("tank_rojo_canon.png");
		this.tanque.setTexture(tanqueText);
		this.tanqueCanon.setTexture(tanqueCanonText);
	}

	@Override
	public void captarInputTanque(boolean destrabeAdelante, boolean destrabeAtras) 
	{
		tanque.setPosition(x, y);
		tanqueCanon.setPosition(x, y);
		rectanguloColision.set(x,y,30f,30f);
	}
}
