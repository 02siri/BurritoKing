package controller;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.Order;
import model.Cart;
import model.FoodItem;

public class CartController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	private Cart cart;
	public double totalPrice;
	public int totalWaitTime;
	
	@FXML
	private Button back;
	@FXML
	private Button checkout;
	@FXML
	private ListView<String>itemsCartList;
	@FXML
	private ListView<String>totalPriceCartList;
	
	public CartController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	public void loadCartView()
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CartView.fxml"));
			loader.setController(this);
			Pane root = loader.load();
			showStage(root);
			cartView();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void initialize() {
		back.setOnAction(e->backButton());
		checkout.setOnAction(e->checkoutButton());
		
	}

	public void cartView()
	{	
		ObservableList<String> items = FXCollections.observableArrayList();
		ObservableList<String> prices = FXCollections.observableArrayList();
		
		int totalQuantity = 0;
		totalPrice = 0.0;
		totalWaitTime = 0;
		
		for(FoodItem item : model.getCart().getItems()) {
			int qty = item.getQty();
			double price = item.getPrice()*qty;
			
			items.add(item.getName() + " -QTY: " + qty);
			prices.add("$" + price);
			
			totalQuantity +=qty;
			totalPrice+=price;
			int itemWaitTime = item.waitTime(qty);
			totalWaitTime = Math.max(totalWaitTime, itemWaitTime) ;
			
			item.setTotalQuantity(totalQuantity);
			item.setTotalPrice(totalPrice);
			item.setTotalWaitTime(totalWaitTime);
			
		}
		
		
		itemsCartList.setItems(items);
		totalPriceCartList.setItems(prices);
		
	}
	
	public void backButton() {
		this.parentStage.show();
		this.stage.close();
	}
	
	public void checkoutButton() {
		CheckoutController checkoutController = new CheckoutController(this.stage,model,totalPrice,totalWaitTime);
		checkoutController.loadCheckoutView();
		stage.hide();
	}
	
	
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Shopping Basket");
		stage.show();
	}
}
