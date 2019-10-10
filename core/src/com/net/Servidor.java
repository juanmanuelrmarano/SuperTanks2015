package com.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Servidor extends Thread
{
	public InetAddress clienteIP;
	private int puerto;
	private DatagramSocket socket;
	public String mensajeCliente = "";
	
	public Servidor()
	{
		try 
		{
			this.socket = new DatagramSocket(1331);
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(true)
		{
			byte[] data = new byte[1024];
		    DatagramPacket paqueteCliente = new DatagramPacket(data, data.length);
			try 
			{
				socket.receive(paqueteCliente);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			mensajeCliente = new String(paqueteCliente.getData());
			clienteIP = paqueteCliente.getAddress();
			puerto = paqueteCliente.getPort();
			//System.out.println("CLIENTE > " + mensajeCliente);
		}
	}
	
	public void mandarData(byte[] data)
	{
		DatagramPacket paqueteMandar = new DatagramPacket(data, data.length, clienteIP, puerto);
		try 
		{
			this.socket.send(paqueteMandar);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
