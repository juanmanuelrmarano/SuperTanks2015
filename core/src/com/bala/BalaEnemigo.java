package com.bala;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BalaEnemigo extends Bala
{
	Timer segMov;
	Random ran;
	private boolean disparar = false;
	
	public BalaEnemigo()
	{
		segMov = new Timer();
		ran = new Random();
	}
	
	private void dispararAlAzar()
	{
		segMov.scheduleAtFixedRate(
		new TimerTask() {
			public void run() 
			{
				try 
				{
					Thread.sleep(500);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
				disparar = ran.nextBoolean();
		    }
			}, 0, 100);
	}

	
	@Override
	public void captarInputBala(float rotacionCanon, float tanqueX, float tanqueY)
	{
		dispararAlAzar();
		if(disparar)
		{
			balaDisparada = true;
			disparo.setPosition(tanqueX, tanqueY);
			balaX = tanqueX;
			balaY = tanqueY;
			disparo.setRotation(-rotacionCanon + 90f);
		}
	}
}
