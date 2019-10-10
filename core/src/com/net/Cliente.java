package com.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Cliente extends Thread
{
	private InetAddress IP;
	private DatagramSocket socket;
	public String mensajeServidor = "";
	
	public Cliente(String IP)
	{
		try 
		{
			this.socket = new DatagramSocket();
			this.IP = InetAddress.getByName(IP);
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(true)
		{
			byte[] data = new byte[1024];
			DatagramPacket paquete = new DatagramPacket(data, data.length);
			try 
			{
				socket.receive(paquete);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			mensajeServidor = new String(paquete.getData());
			//System.out.println("SERVIDOR > " + mensajeServidor);
		}
	}
	
	public void mandarData(byte[] data)
	{
		DatagramPacket paquete = new DatagramPacket(data, data.length, IP, 1331);
		try 
		{
			socket.send(paquete);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
