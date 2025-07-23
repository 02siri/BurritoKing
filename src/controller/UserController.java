package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class UserController {
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private Button updateUser;
	@FXML
	private Button deleteUser;
	@FXML
	private Label status;
	
	private Stage stage;
	private Stage parentStage;
	private Model model;
	private User user;
	
	public UserController(Stage parentStage, Model model)
	{
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}


	@FXML
	public void initialise() {
		updateUser.setOnAction(e->updateUser());
		deleteUser.setOnAction(e->deleteUser());
	}
	public void updateUser()
	{
		
	}
	public void deleteUser()
	{
		
	}
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("User Profile");
		stage.show();
	}
}
