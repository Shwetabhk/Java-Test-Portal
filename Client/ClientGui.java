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
import java.net.InetAddress;
public class ClientGui extends Application
{
    String ipad="";
    Socket socket=null;
    public void start(Stage s)
    {
        getHome(s);
    }
    public void getHome(Stage s)
    {
        s.setTitle("Mock Test Portal");
        GridPane grid=new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        Text title=new Text("Mock Test Portal. Please Login or Register");
        title.setFont(Font.font("Calibri",FontWeight.BOLD,20));
        grid.add(title,0,0,3,1);
        Label ip=new Label("Server IP Address : ");
        TextField t=new TextField();
        Button b1=new Button("Connect");
        Button b2=new Button("Sign in");
        Button b3=new Button("Register");
        HBox h=new HBox(10);
        Text t3=new Text();
        h.setAlignment(Pos.BOTTOM_RIGHT);
        h.getChildren().add(b2);
        h.getChildren().add(b3);
        grid.add(ip,0,2);
        grid.add(t,1,2);
        grid.add(b1,2,2);
        grid.add(h,1,6);
        grid.add(t3,1,8);
        b1.setOnAction(new EventHandler<ActionEvent>()
        {
          public void handle(ActionEvent e)
          {
              ipad=t.getText();
              if(!ipad.equals(""))
              {
                try
                { 
                   socket=new Socket(InetAddress.getByName(ipad),8080);
                   t3.setText("Successfully Connected");
                } 
                catch(Exception ex)
                {
                   t3.setText("Wrong IP entered.Try Again!");
                   socket=null;
                }
              }
              else
              {
                  t3.setText("Enter IP to Continue");
              }
          }
        });
        b2.setOnAction(new EventHandler<ActionEvent>()
        {
          public void handle(ActionEvent e)
          {
              if(socket!=null||!ipad.equals(""))
              {
                LoginGui lg=new LoginGui();
                lg.ip=ipad;
                lg.getStage(s,socket);
                s.show();
              }
              else
              {
                t3.setText("Enter correct IP to continue");
                socket=null;
              }
          }
        });
        b3.setOnAction(new EventHandler<ActionEvent>()
        {
          public void handle(ActionEvent e)
          {
              NewUser nu=new NewUser();
              nu.ip=ipad;
              nu.getStage(s);
              s.show();
          }
        });
        Scene s1=new Scene(grid,700,500);
        s.setScene(s1);
        s.show();
    }
    public static void main(String args[])throws Exception
    {
        launch(args);
        System.out.println(InetAddress.getByName("0.0.0.0"));
    }
}