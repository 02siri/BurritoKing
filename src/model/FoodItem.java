package model;
public abstract class FoodItem{
	private int qty;
	private double price;
	private double totalPrice=0;
	private int waitTime=0;
	private int itemID;
	private int totalQty = 0;
	private String name;
	
	public FoodItem(int itemId)
	{
		//this.price = price;
		this.itemID = itemId;
	}
	
	public int getItemID()
	{
		return itemID;
	}
	
	public void setItemID(int itemID)
	{
		this.itemID = itemID;
	}
	
	public int getQty()
	{
		return this.qty;
	}
	
	public void setQty(int quantity)
	{
		this.qty = quantity;
	}
	
	public double getTotalPrice()
	{
		return qty*getPrice();
	}
	public void setTotalPrice(Double totalPrice)
	{
		this.totalPrice = totalPrice;
	}
	
	public int getTotalQuantity() {
		return totalQty;
	}
	
	public void setTotalQuantity(int totalQty) {
		this.totalQty = totalQty;
	}
	
	public void setTotalWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
	public int getTotalWaitTime() {
		return this.waitTime;
	}
	
	public abstract double getPrice();
	//abstract method to return the wait time of the quantity of each food item ordered
	public abstract int waitTime(int qty);
	
	//abstract method to return the name of each food item
	public abstract String getName();
	
	public void setName(String name) {
		this.name = name;
	}
	
}
