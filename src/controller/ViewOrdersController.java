package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.FoodItem;
import model.Model;
import model.Order;
import model.OrderService;
import model.User;

public class ViewOrdersController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	private User currentUser;
	private OrderService orderService;
	
	@FXML
	private ListView<String> ordersListView;
	@FXML
	private Button backViewOrders;
	@FXML
	private Button exportOrders;
	
	public ViewOrdersController(Stage parentStage, Model model,User currentUser) {
		this.parentStage = parentStage;
		this.stage = new Stage();
		this.model = model;
		this.currentUser = currentUser;
		this.orderService = new OrderService(model);
	}
	
	public void loadOrdersView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewOrders.fxml"));
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
		loadOrders();
		backViewOrders.setOnAction(e->back());
		exportOrders.setOnAction(e->{
			List<String> items = ordersListView.getItems();
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
			File file = fileChooser.showSaveDialog(stage);
			if(file!=null) {
				//String filePath = "allOrders.csv";
				export(items,file.getAbsolutePath());
			}
			
		});
	}
	
	private void loadOrders() {
		ObservableList<String> allOrders = FXCollections.observableArrayList();
		 for(Order order : orderService.getAllOrders(model.getCurrentUser().getUsername())) {
			 StringBuilder orderDetails = new StringBuilder();
			 orderDetails.append("Order ID.: ").append(order.getOrderID()).append("\n");
			 orderDetails.append("Order Status: ").append(order.getOrderStatus()).append("\n");
			 orderDetails.append("Order Date and Time: ").append(order.getDateTime()).append("\n");
			 orderDetails.append("Total Price:$ ").append(order.getTotalPrice()).append("\n");
			 orderDetails.append("Items Ordered: \n");
			 orderDetails.append(String.format("%-20s %-10s %-10s\n","Name","Qty","Price"));
			 
			 List<FoodItem> foodItems = model.getOrderItemsDao().getFoodItems(order.getOrderID());
			 for(FoodItem item : foodItems) {
				 orderDetails.append(String.format("%-20s %-10d $%-10.2f\n",item.getName(),item.getQty(),item.getTotalPrice()));
			 }
			 orderDetails.append("\n"); 
			allOrders.add(orderDetails.toString());
		 } 
	 ordersListView.setItems(allOrders);
	}
	
	public void export(List<String> items, String filePath) {
		try(FileWriter writer = new FileWriter(filePath)){
			for(String item : items) {
				writer.append(item).append("\n");
			}
			writer.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void back() {
		this.parentStage.show();
		this.stage.close();
	}

	

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 494, 508);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("View All Orders");
		stage.show();
	}
}
