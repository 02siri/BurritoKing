package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Model;

public class ConfirmationController {
	private Stage stage;
	private Stage parentStage;
	private Model model;
	@FXML
	private Button homeConfirm;
	@FXML
	private Button logoutConfirm;
	
	public ConfirmationController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	public void loadConfirmView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ConfirmationView.fxml"));
			loader.setController(this);
			Pane root = loader.load();
			showStage(root);
		}catch(IOException e)
		{
			e.printStackTrace();
		}	
	}
	
	@FXML
	public void initialize() {
		logoutConfirm.setOnAction(e->logoutButton());
	}
	
	
	private void logoutButton() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
			LoginController loginController = new LoginController(new Stage(), model);
			loader.setController(loginController);
			Pane root = loader.load();
			Scene scene = new Scene(root, 500, 300);
			parentStage.setScene(scene);
			parentStage.show();	
			this.stage.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		this.stage.close();
	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 400, 261);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Order Confirmation");
		stage.show();
	}
}
