import java.io.*;
import java.util.Scanner;
import java.net.*;
class ClientThread extends Thread
{
	private Socket socket=null;
	public ClientThread(Socket socket)
	{
		this.socket=socket;
	}
	public void run()
	{
		StartPortal sp=new StartPortal(socket);
	}
}