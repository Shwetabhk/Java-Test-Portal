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
public class LoginGui extends Application
{
	Stage s1;
	public void getStage()
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
		HBox hbutton=new HBox(10);
		hbutton.setAlignment(Pos.BOTTOM_RIGHT);
		hbutton.getChildren().add(b);
		grid.add(hbutton,1,4);
		Text actiontarget=new Text();
		grid.add(actiontarget,1,6);
		b.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				String u=user.getText();
				String p=pass.getText();
				Login l=new Login(u,p,actiontarget);
			}
		});
		Scene s=new Scene(grid);
		s1.setFullScreen(true);
		s1.setScene(s);
		s1.show();
	}
}