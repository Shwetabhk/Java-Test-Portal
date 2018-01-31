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
import java.net.Socket;
public class LoginGui
{
	String ip=null;
  int flag=0;
  public void getStage(Stage s1,Socket socket)
	{
		s1.setTitle("Mock");
		GridPane grid=new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		Text title=new Text("Welcome");
		title.setFont(Font.font("Calibri",FontWeight.NORMAL,20));
		grid.add(title,0,0,2,1);
		Label username=new Label("Enrolment number : ");
		grid.add(username,0,1);
		TextField user=new TextField();
		grid.add(user,1,1);
		Label password=new Label("Password : ");
		grid.add(password,0,2);
		PasswordField pass=new PasswordField();
		grid.add(pass,1,2);         
		Button b=new Button("Login");
    Button b2=new Button("Home");
		HBox hbutton=new HBox(10);
		hbutton.setAlignment(Pos.BOTTOM_RIGHT);
		hbutton.getChildren().add(b);
    hbutton.getChildren().add(b2);
		grid.add(hbutton,1,4);
		Text actiontarget=new Text();
		grid.add(actiontarget,1,6);
		b.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				String u=user.getText();
				String p=pass.getText();
        GetPortal gp=new GetPortal();
        login(u,p,actiontarget);
        try
        {
          if(flag==1)
            {
              gp.questions(socket);
              gp.start(s1);
            }
        }catch (Exception el){}
			}
		});
    b2.setOnAction(new EventHandler<ActionEvent>()
    {
      public void handle(ActionEvent e5)
      {
        ClientGui lg=new ClientGui();
        lg.getHome(s1);
        s1.show();
      }
    });
		Scene s=new Scene(grid,700,500);
		s1.setScene(s);
	}
  public void login(String enrollment,String pass,Text actionTarget)
  {
    Connection con=null;
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      con=DriverManager.getConnection("jdbc:mysql://"+ip+":3306/Quiz?useSSL=false","Shwetabh","yb54780");
    }
    catch(Exception k)
    {
      actionTarget.setText("IP not found");
    }
    try
    {
      Statement st=con.createStatement();
      ResultSet rs=st.executeQuery("select * from users where Enrollment="+enrollment);
      rs.next();
      String password=rs.getString(4);
      if(pass.equals(password))
      {
        flag=1;
      }
      else
      {
        actionTarget.setText("Try Again");
      }
    }
    catch(Exception ex)
    {
      actionTarget.setText("No record found please Register");
    }
  }
}