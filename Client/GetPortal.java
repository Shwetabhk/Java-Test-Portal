import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import java.io.PrintWriter;
class GetPortal
{
	String questions[]=new String[200];
	int i=0;
	int flag[]=new int[100];
	int score=0;
	Socket socket;
	public void questions(Socket socket)throws Exception
	{
		this.socket=socket;
		BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while((questions[i]=br.readLine())!=null)
		{
			i++;
		}
		i=0;
	}
	public void start(Stage stage)
	{
		String s1[]=questions[i].split(",");
		String s2[]=s1[0].split("<br>");
		String ques=s2[0];
		for(int x=1;x<s2.length;x++)
		{
			ques=ques+"\n"+s2[x];
		}
		stage.setTitle("Question "+(i+1));
		GridPane grid=new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		Text q=new Text(ques);
		q.setFont(Font.font("Calibri",FontWeight.BOLD,20));
		Button b1=new Button("<Back");
		Button b2=new Button("Next>");
		Button b3=new Button("Submit");
		Button b4=new Button("End");
		HBox h=new HBox(10);
		h.setAlignment(Pos.BOTTOM_RIGHT);
		if(i!=0)
		h.getChildren().add(b1);
		h.getChildren().add(b3);
		ToggleGroup group=new ToggleGroup();
		RadioButton r1=new RadioButton(s1[1]);
		r1.setUserData(s1[1]);
		r1.setToggleGroup(group);
		RadioButton r2=new RadioButton(s1[2]);
		r2.setUserData(s1[2]);
		r2.setToggleGroup(group);
		RadioButton r3=new RadioButton(s1[3]);
		r3.setUserData(s1[3]);
		r3.setToggleGroup(group);
		RadioButton r4=new RadioButton(s1[4]);
		Text actionTarget=new Text();
		r4.setUserData(s1[4]);
		r4.setToggleGroup(group);
		if(questions[i+1]!=null)
		{
			h.getChildren().add(b2);
		}
		if(questions[i+1]==null)
		{
			h.getChildren().add(b4);
		}
		grid.add(q,0,0,3,1);
		grid.add(r1,0,2);
		grid.add(r2,2,2);
		grid.add(r3,0,4);
		grid.add(r4,2,4);
		grid.add(h,1,6);
		grid.add(actionTarget,1,8);
		b1.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent n)
			{
				i--;
				start(stage);
				stage.show();
			}
		});
		b2.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent m)
			{
				i++;
				start(stage);
				stage.show();
			}
		});
		b3.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent k)
			{
				String answer=group.getSelectedToggle().getUserData().toString();
				actionTarget.setText("Submitted");
				if(answer.equalsIgnoreCase(s1[5]))
				{
					if(flag[i]==0)
					{
						flag[i]=1;
						score=score+3;		
					}
					else if(flag[i]==2)
					{
						score+=3;
						flag[i]=1;
					}
				}
				else
				{
					if(flag[i]==1)
					{
						score=score-3;
					}
					flag[i]=2;
				}
			}
		});
		b4.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent yo)
			{
				try
				{
					PrintWriter out=new PrintWriter(socket.getOutputStream());
					out.println(score);
				}
				catch(Exception po)
				{
					po.printStackTrace();
				}
				stage.setTitle("Final Score");
				GridPane grid1=new GridPane();
				grid1.setAlignment(Pos.CENTER);
				grid1.setHgap(10);
				grid1.setVgap(10);
				grid1.setPadding(new Insets(25,25,25,25));
				Text q1=new Text("Your Score is "+score);
				q1.setFont(Font.font("Calibri",FontWeight.BOLD,20));
				grid1.add(q1,0,0,3,1);
				stage.setScene(new Scene(grid1,700,500));
				stage.show();
			}
		});
		Scene scene=new Scene(grid,700,500);
		stage.setScene(scene);
	}
}