package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class SignupController {
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private Button createUser;
	@FXML
	private Button backToLogin;
	@FXML
	private Label status;
	private Stage stage;
	private Stage parentStage;
	private Model model;
	
	public SignupController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	@FXML
	public void initialize() {
		createUser.setOnAction(event -> {
			if (!username.getText().isEmpty() && !password.getText().isEmpty()&&
				!firstName.getText().isEmpty() && !lastName.getText().isEmpty()) {
				User user;
				try {
					if(model.getUserDao().checkField("username",username.getText())) {
						status.setText("Already registered with this username");
						status.setTextFill(Color.RED);
					}
					else if(model.getUserDao().checkField("password",password.getText())){
						status.setText("This password is already taken");
						status.setTextFill(Color.RED);
					}
					else if(model.getUserDao().checkField("firstName",firstName.getText())) {
						status.setText("This first name is already taken");
						status.setTextFill(Color.RED);
					}
					else if(model.getUserDao().checkField("lastName",lastName.getText())){
						status.setText("This last name is already taken");
						status.setTextFill(Color.RED);
					}
					else
					{
						user = model.getUserDao().createUser(username.getText(), password.getText(),firstName.getText(),lastName.getText());
						if (user != null) {
							status.setText("Created " + user.getUsername());
							status.setTextFill(Color.GREEN);
						} else {
							status.setText("Cannot create user");
							status.setTextFill(Color.RED);
						}
					}
					
				} catch (SQLException e) {
					status.setText(e.getMessage());
					status.setTextFill(Color.RED);
				}
				
			} else {
				status.setText("One of the fields is empty");
				status.setTextFill(Color.RED);
			}
		});

		backToLogin.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Sign up");
		stage.show();
	}
}
