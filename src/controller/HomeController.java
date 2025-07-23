package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.Order;
import model.OrderService;
import model.OrderStatus;
import model.User;
import model.FoodItem;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


public class HomeController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	
	@FXML
	private Label names;
	@FXML
	private Label isVIPLabel;
	@FXML
	private Button editProfile;
	@FXML
	private ListView<String> activeOrdersList;
	@FXML
	private Button viewOrders;
	@FXML
	private Button upgradeToVIPButton;
	@FXML
	private Button order;
	@FXML
	private Button logout;
	
	public User currentUser;
	public OrderService orderService;
	private Order currentOrder;
	
	
	public HomeController(Stage parentStage, Model model,User currentUser) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.currentUser = currentUser;
		this.orderService = new OrderService(model);
		this.currentOrder = model.getCurrentOrder();
	}
	
	public void loadHomeFXML()
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
			HomeController homeController = new HomeController(stage, model,currentUser);
			homeController.setUser(currentUser);
			loader.setController(this);
			Pane root = loader.load();
			showStage(root);
			setUser(currentUser);
		}catch(Exception e)
		{
			System.out.println("Error");
		}	
	}

	
	@FXML
	public void initialize() {
		editProfile.setOnAction(e->editProfile());
		upgradeToVIPButton.setOnAction(e->upgradeToVIP());
		order.setOnAction(e->placeOrder());
		viewOrders.setOnAction(e->viewAllOrders());
		logout.setOnAction(e->logoutButton());
		
	}

	public void viewAllOrders() {
		ViewOrdersController viewOrdersController = new ViewOrdersController(this.stage,model,currentUser);
		viewOrdersController.loadOrdersView();
		this.stage.hide();
	}

	public void activeOrders() {
		if(currentUser!=null) {
			 ObservableList<String> activeOrders = FXCollections.observableArrayList();
			 for(Order order : orderService.getActiveOrders(model.getCurrentUser().getUsername())) {
				 //VBox orderDetailsBox = new VBox();
				 
				 StringBuilder orderDetails = new StringBuilder();
				 orderDetails.append("Order ID.: ").append(order.getOrderID()).append("  ");
				 orderDetails.append("Order Status: ").append(order.getOrderStatus()).append("   ");
				 orderDetails.append("Total Price: $").append(order.getTotalPrice()).append("\n");
				 orderDetails.append("Items Ordered: \n");
				 orderDetails.append(String.format("%-20s %-10s %-10s\n","Name","Qty","Price"));
				 
				 List<FoodItem> foodItems = model.getOrderItemsDao().getFoodItems(order.getOrderID());
				 for(FoodItem item : foodItems) {
					 orderDetails.append(String.format("%-20s %-10d $%-10.2f\n",item.getName(),item.getQty(),item.getTotalPrice()));
				 }
				 orderDetails.append("\n"); 
				 
				activeOrders.add(orderDetails.toString());
				
			 } 
			 activeOrdersList.setCellFactory(listView -> createCustomCell(listView));
		 activeOrdersList.setItems(activeOrders);
	 }
	
	}

	private ListCell<String> createCustomCell(ListView<String> listView) {
		ListCell<String> cell = new ListCell<String>(){
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item,empty);
				if(empty) {
					setText(null);
					setGraphic(null);
				}
				else {
					String [] orderDetails = item.split("\n");
					VBox orderDetailsBox = new VBox();
					orderDetailsBox.setSpacing(5);
					
					for(int i=0; i<orderDetails.length-1;i++) {
						Label orderDetailsLabel = new Label(orderDetails[i]);
						orderDetailsBox.getChildren().add(orderDetailsLabel);
					}
					HBox buttons = new HBox();
					buttons.setSpacing(5);
					
					Button collect = new Button("Collect");
					collect.setOnAction(e->{collectOrder();
											getListView().getSelectionModel().select(getIndex());});
					buttons.getChildren().add(collect);
					
					Button cancel = new Button("Cancel");
					cancel.setOnAction(e->cancelOrder());
					buttons.getChildren().add(cancel);
					
					orderDetailsBox.getChildren().add(buttons);
					setText(null);
					setGraphic(orderDetailsBox);
				}
			}
		};
		return cell;
	}

	private void cancelOrder() {
		System.out.println("Cancel button pressed");
	}

	private void collectOrder() {
		String selectedOrderID = getSelectedOrderID();
		if(selectedOrderID != null) {
			boolean orderCollected = model.getOrderDao().updateOrderStatus(selectedOrderID, OrderStatus.COLLECTED); 
			if(orderCollected) {
				activeOrders();
				System.out.println("Order collected successfully.");
			}
			else
			{
				System.out.println("Failed to collect order");
			}
		}
		else
		{
			System.out.println("No order selected to collect.");
		}
		
	}	
		
	private String getSelectedOrderID() {	
		String selectedOrder = activeOrdersList.getSelectionModel().getSelectedItem();
		if(selectedOrder != null) {
			String [] parts = selectedOrder.split(":");
			if(parts.length>= 1) {
				return parts[0].trim();
			}else
			{
				System.out.println("Invalid format for selected item");
				return null;
			}
		}else
		{
			System.out.println("No item selected");
			return null;
		}
	}

	public void setUser(User user) {
		this.currentUser = user;
		if(user!=null) {
			names.setText(user.getFirstName() + " " + user.getLastName());
			Boolean isVIP = user.getisVIP();
			if(isVIP) {
				isVIPLabel.setText("VIP User");
				upgradeToVIPButton.setVisible(false);
			}
			else
			{
				upgradeToVIPButton.setVisible(true);
			}
		}
		activeOrders();
	}
	
	
	public void editProfile()
	{
		ProfileController profileObj = new ProfileController(this.stage, model,currentUser);
		profileObj.loadProfileView();
		stage.hide();
	}
	
	@FXML
	public void upgradeToVIP() {
		VIPUserController vipObj = new VIPUserController(this.stage, model, currentUser);
		vipObj.loadVipStep1();
		stage.hide();
	}
	
	@FXML
	public void placeOrder() {
		OrderController orderObj = new OrderController(this.stage,model);
		orderObj.loadOrderView();
		stage.hide();
		
	}
	
	public void logoutButton() {
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
		
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 706, 461);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Dashboard");
		stage.show();
	}
}
