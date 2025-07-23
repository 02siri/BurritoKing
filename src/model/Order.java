package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random; 

public class Order {
	private String orderID;
	private List<FoodItem> foodItems; 
	private OrderStatus orderStatus;	
	private int waitTime;
	private double totalPrice;
	private int totalWaitTime;
	private LocalDateTime dateTime;
	private String alph = "ABCDEF";
	private Model model;
	private Cart cart;
	
	public Order(Model model)
	{
		this.model = model;
		this.cart = model.getCart();
		this.foodItems = new ArrayList<>(cart.getItems());
	}
	
	public Order() {
		
	}
	
	public String getOrderID()
	{
		return this.orderID;
	}
	
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public void generateOrderID()
	{	Random rand = new Random();
		StringBuilder orderID = new StringBuilder();
		
		char firstAlph = alph.charAt(rand.nextInt(alph.length()));
		char secondAlph = alph.charAt(rand.nextInt(alph.length()));
		int num = rand.nextInt(100);
		String str = firstAlph + secondAlph + String.format("%02d",num);
		
		
		while(model.getOrderDao().orderIDExists(str))
		{
			firstAlph = alph.charAt(rand.nextInt(alph.length()));
			secondAlph = alph.charAt(rand.nextInt(alph.length()));
			num = rand.nextInt(100);
			str = firstAlph + secondAlph + String.format("%02d",num);
		}
		this.orderID = orderID.append(str).toString();
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public List<FoodItem> getFoodItems()
	{
		return foodItems;
	}
	
	
	public double getTotalPrice() {
		
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
