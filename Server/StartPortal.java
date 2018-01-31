import java.io.*;
import java.net.*;
import java.util.Scanner;
class StartPortal
{
	Socket socket=null;
	BufferedReader br;
	Scanner sc;
	PrintWriter out;
	File file;
	public StartPortal(Socket socket)
	{
		this.socket=socket;
		try
		{
			file=new File("quiz.txt");
			sc=new Scanner(new FileReader(file));
			out=new PrintWriter(socket.getOutputStream(),true);
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(sc.hasNextLine())
			{
				out.println(sc.nextLine());
			}
			socket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}