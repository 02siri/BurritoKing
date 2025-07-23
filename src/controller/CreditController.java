package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;

public class CreditController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private Button checkCredits;
	@FXML
	private MenuItem howToUse;
	
	public CreditController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	// Add your code to complete the functionality of the program
	@FXML
	public void initialise() {
//		checkCredits.setOnAction(e -> credits());
//		howToUse.setOnAction(e -> info());
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Order.fxml"));
//			OrderController orderController = new OrderController(stage, model,currentUser);
//			
//			loader.setController(orderController);
//			VBox root = loader.load();
//			orderController.showStage(root);
//		}catch(Exception e)
//		{
//			System.out.println("Error");
//		}	
	}
	
	public void credits()
	{
		
	}
	
	public void info() {
	
	}
	
	
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("VIP Credits");
		stage.show();
	}
}
