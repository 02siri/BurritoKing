package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Model;
import model.User;
import model.VIPUser;

public class LoginController {
	@FXML
	private TextField name;
	@FXML
	private PasswordField password;
	@FXML
	private Label message;
	@FXML
	private Button login;
	@FXML
	private Button signup;

	private Model User;
	private Model VIPUser;
	private Model model;
	private Stage stage;
	
	public LoginController(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
	}
	
	@FXML
	public void initialize() {		
		login.setOnAction(e -> login());
		signup.setOnAction(e -> signUp());
	}
	
	public void login()
	{
		{
			if (!name.getText().isEmpty() && !password.getText().isEmpty()) {
				User user;
				try {
					user = model.getUserDao().getUser(name.getText(), password.getText());
					if (user != null) {
						if(user instanceof VIPUser) {
							VIPUser vipUser = (VIPUser)user;
							model.setCurrentUser(vipUser);
							System.out.println("In login -> Current user class is: " + model.getCurrentUser().getClass().getName());
						}
						else
						{	
							model.setCurrentUser(user);	
						}
						userHomeView(user);
					} 
					else {
						message.setText("Wrong username or password");
						message.setTextFill(Color.RED);
					}
				} catch (SQLException e) {
					message.setText(e.getMessage());
					message.setTextFill(Color.RED);
				}
				
			} else {
				message.setText("Empty username or password");
				message.setTextFill(Color.RED);
			}
			name.clear();
			password.clear();
		}
	}
	
	public void signUp()
	{
			try {
				//System.out.println("Button Clicked");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignupView.fxml"));
				
				// Customize controller instance
				SignupController signupController =  new SignupController(stage, model);

				loader.setController(signupController);
				VBox root = loader.load();
				
				signupController.showStage(root);
				
				message.setText("");
				name.clear();
				password.clear();
				
				this.stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public void userHomeView(User user)
	{	
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
			HomeController homeController = new HomeController(stage, model,user);
			
			loader.setController(homeController);
			Pane root = loader.load();

			homeController.setUser(user);
			homeController.showStage(root);
			stage.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("LogIn");
		stage.show();
	}
}

