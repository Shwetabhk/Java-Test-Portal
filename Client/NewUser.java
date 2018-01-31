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
import java.sql.*;
public class NewUser
{
	String ip="";
	public void getStage(Stage s)
	{ 
		s.setTitle("Register");
		GridPane grid=new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		Text title=new Text("Welcome");
		title.setFont(Font.font("Calibri",FontWeight.NORMAL,20));
		grid.add(title,0,0,2,1);
		Label name=new Label("Name : ");
		TextField n=new TextField();
		Label enrollment=new Label("Enrollment no. : ");
		TextField e=new TextField();
		Label batch=new Label("Batch : ");
		TextField b1=new TextField();
		Label pass=new Label("Password : ");
		PasswordField p=new PasswordField();
		Label conpass=new Label("Confirm Password");
		PasswordField c=new PasswordField();
		Button b=new Button("Register");
		Button b2=new Button("Home");
		HBox hb=new HBox(10);
		hb.setAlignment(Pos.BOTTOM_RIGHT);
		hb.getChildren().add(b);
		hb.getChildren().add(b2);
		Text actionTarget=new Text();
		grid.add(name,0,1);
		grid.add(n,1,1);
		grid.add(enrollment,0,2);
		grid.add(e,1,2);
		grid.add(batch,0,3);
		grid.add(b1,1,3);
		grid.add(pass,0,4);
		grid.add(p,1,4);
		grid.add(conpass,0,5);
		grid.add(c,1,5);
		grid.add(hb,1,7);
		grid.add(actionTarget,1,9);
		b.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e1)
			{
				String nameG=n.getText();
				String enrollmentG=e.getText();
				String batchG=b1.getText();
				String passG=p.getText();
				String conpassG=c.getText();
				createAndCheck(enrollmentG,nameG,batchG,passG,conpassG,actionTarget);
			}
		});
		b2.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e5)
			{
				ClientGui lg=new ClientGui();
				lg.getHome(s);
				s.show();
			}
		});
		Scene s1=new Scene(grid,700,500);
		s.setScene(s1);
		s.show();
	}
	public void createAndCheck(String enrollment,String name,String batch,String password,String conpass,Text actionTarget)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://"+ip+":3306/Quiz?useSSL=false","Shwetabh","yb54780");
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from users where Enrollment="+enrollment);
			if(rs.isBeforeFirst())
			{
				actionTarget.setText("Already Exists please login");
			}
			else
			{	
				rs.next();
				String t="t"+enrollment;			
				if(password.equals(conpass))
				{
					createTable(t);
					String query="insert into users values(?,?,?,?)";
					PreparedStatement pst=con.prepareStatement(query);
					pst.setString(1,enrollment);
					pst.setString(2,name);
					pst.setString(3,batch);
					pst.setString(4,password);
					pst.execute();
					actionTarget.setText("New Record Created");
				}
				else
				{
					actionTarget.setText("Passwords did not match!");
				}
			}
		}
		catch(Exception o)
		{
			actionTarget.setText("IP not Found");
		}	
	}
	public void createTable(String enrollment)throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://"+ip+":3306/Quiz?useSSL=false","Shwetabh","yb54780");
		String query1="create table "+enrollment+"(Enrollment varchar(10),score int,time timestamp)";
		Statement st=con.createStatement();
		st.executeUpdate(query1);
	}	
}