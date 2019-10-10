package com.bala;

public class BalaMP extends Bala
{
	@Override
	public void captarInputBala(float rotacionCanon, float tanqueX, float tanqueY)
	{
			balaDisparada = true;
			disparo.setPosition(tanqueX, tanqueY);
			balaX = tanqueX;
			balaY = tanqueY;
			disparo.setRotation(-rotacionCanon + 90f);
	}
}
