package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Model;
import model.Order;
import model.User;
import model.Cart;
import model.FoodItem;
import model.ItemFactory;

public class OrderController {
	private Model model;
	private Stage parentStage;
	private Stage stage;
	private CartController cartController;
	@FXML
	private ListView<String> itemsMenu;
	@FXML
	private  ListView<Double> priceMenu;
	@FXML
	private  ListView<Spinner<Integer>> spinnersMenu;
	@FXML
	private Button cancel;
	@FXML
	private Button goToCart;
	@FXML
	private Button checkout;
	
	
	public OrderController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.model = model;
		this.parentStage = parentStage;
	}
	
	
	@FXML
	public void initialize() {
		makeMenu();
		goToCart.setOnAction(e -> goToCartButton());
		cancel.setOnAction(e->cancelButton());
	}


	public void loadOrderView()
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Order.fxml"));
			OrderController orderController = new OrderController(parentStage, model);
			//orderController.currentUser = currentUser;
			loader.setController(orderController);
			Pane root = loader.load();
			orderController.showStage(root);
			stage.hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void makeMenu()
	{	
		List<FoodItem>itemList = new ArrayList<>(Arrays.asList(
				ItemFactory.createFoodItem(101,"Burritos"),
				ItemFactory.createFoodItem(102,"Fries"),
				ItemFactory.createFoodItem(103,"Sodas"),
				ItemFactory.createFoodItem(104,"Burgers"),
				ItemFactory.createFoodItem(105,"Shakes")));
				
				if(model.getCurrentUser().getisVIP()) {
					itemList.add(ItemFactory.createFoodItem(106,"Meal (1 Burrito, 1 Fries, 1 Soda)"));
					itemList.add(ItemFactory.createFoodItem(107,"Family Meal (1 Burrito, 1 Fries, 1 Soda, 1 Burger, 1 Shake) "));
				}
		
		
		try {
			model.getFoodItemsDao().insertFoodItems(itemList);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		ObservableList<String>foodItemList = FXCollections.observableArrayList();
		ObservableList<Double>priceList = FXCollections.observableArrayList();
		ObservableList<Spinner<Integer>>spinnersList = FXCollections.observableArrayList();
		
		for(FoodItem item : itemList)
		{
			foodItemList.add(item.getItemID()+ " " +item.getName());
			priceList.add(item.getPrice());
			spinnersList.add(createSpinner(item));
		}
		itemsMenu.setItems(foodItemList);
		priceMenu.setItems(priceList);
		spinnersMenu.setItems(spinnersList);
		
		setInsets(itemsMenu);
		setInsets(priceMenu);
		setSpinnerInsets(spinnersMenu);
	}
	
	private Spinner<Integer> createSpinner(FoodItem item)
	{
		Spinner<Integer> spinner = new Spinner<>();
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
		spinner.setValueFactory(valueFactory);
			
		spinner.valueProperty().addListener((obs, oldVal, newVal)->{
			item.setQty(newVal);
			if(newVal > 0)
			{
				model.addItem(item);
			}
			else
			{
				model.removeItem(item);
			}
		});	
		return spinner;
	}
	
	public void addToCart(FoodItem item) {
		User currentUser = model.getCurrentUser();
		System.out.println("Add to Cart button has been pressed");  
		if(item!=null)
		{	
			String foodItemSelected = item.getName();
			double priceSelected = item.getPrice();
			item.setQty(1);
			int qtySelected = item.getQty();
			
			Order newOrder = new Order(model);
			newOrder.generateOrderID();
			String orderID = newOrder.getOrderID();
			System.out.println("Selected item and price added - controller class");
		}
		else
		{
			System.out.println("Please select a food item.");
		}
		
	}
	
	private void setSpinnerInsets(ListView<Spinner<Integer>> spinnerListView) {
		spinnerListView.setCellFactory(p-> new ListCell<Spinner<Integer>>() {
			protected void updateItem(Spinner<Integer> item, boolean empty)
			{
				super.updateItem(item, empty);
				if(item!=null)
				{
					setGraphic(item);
					setMinWidth(20);
					setPrefWidth(20);
					setMaxWidth(20);
					
					setMinHeight(30);
					setPrefHeight(30);
					setMaxHeight(30);
				}
				else
				{
					setGraphic(null);
				}
			}
		});
	}
	
	private <T> void setInsets(ListView <T> listView)
	{
		listView.setCellFactory(p-> new ListCell<T>() {
			protected void updateItem(T item, boolean empty)
			{
				super.updateItem(item, empty);
				if(item!=null)
				{
					setText(item.toString());
					setMinHeight(30);
					setPrefHeight(30);
					setMaxHeight(30);
				}
				else {
					setText(null);
				}
			}
		});
	}
	
	private void goToCartButton() {
		if(cartController == null) {
			cartController = new CartController(this.stage,model);
		}
		//Cart cart = model.getCartDao().getCart();
		cartController.loadCartView();
		stage.hide();
	}

	private void cancelButton() {
			
		this.parentStage.show();
			
		this.stage.close();
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("All-Day Menu");
		stage.show();
	}
}
