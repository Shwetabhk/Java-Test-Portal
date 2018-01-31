import java.net.*;
import java.io.*;
import java.util.Scanner;
class GetQuiz
{
	Socket socket=null;
	public GetQuiz(Socket socket)
	{
		this.socket=socket;
		try
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
			Scanner sc=new Scanner(System.in);
			String s="";
			Thread.sleep(2000);
			while((s=br.readLine())!=null)
			{
				String a1[]=s.split("<br>");
				if(a1.length>1)
				{
					for(int i=0;i<=a1.length-1;i++)
					{
						System.out.println(a1[i]);
					}
				}
				else
				{
					System.out.println(s);
				}				
				String options=br.readLine();
				System.out.println(options);
				System.out.println("Enter your Answer");
				String answer=sc.nextLine();
				out.println(answer);
				String result=br.readLine();
				System.out.println(result);
			}
			socket.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}