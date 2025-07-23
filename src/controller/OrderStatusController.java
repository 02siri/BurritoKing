package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.Order;
import model.OrderStatus;

public class OrderStatusController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private MenuItem status;
	@FXML
	private MenuItem orderDetails;
	@FXML
	private MenuItem prevOrders;
	
	
	
	
	public OrderStatusController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	// Add your code to complete the functionality of the program
	@FXML
	public void initialise() {
//		status.setOnAction(e -> status());
//		orderDetails.setOnAction(e->orderDetails());
//		prevOrders.setOnAction(e->prevOrders());
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
//			HomeController homeController = new HomeController(stage, model);
//			
//			loader.setController(homeController);
//			VBox root = loader.load();
//			homeController.showStage(root);
//		}catch(Exception e)
//		{
//			System.out.println("Error");
//		}	
	}
	
	public void status()
	{
		
	}
	
	public void orderDetails() {
		
	}
	
	public void prevOrders() {
		
	}
	
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Order Status");
		stage.show();
	}
}
