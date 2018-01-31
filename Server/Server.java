import java.io.*;
import java.util.Scanner;
import java.net.*;
class Server
{
	static Socket socket;
	public static void main(String args[])
	{
		try
		{
			ServerSocket server=new ServerSocket(8080);
			boolean stop=false;
			while(!stop)
			{
				socket=server.accept();
				ClientThread t=new ClientThread(socket);
				t.start();
			}
			server.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}