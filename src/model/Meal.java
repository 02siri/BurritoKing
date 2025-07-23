package model;

public class Meal extends FoodItem{
	private Burritos burrito;
	private Fries fries;
	private Sodas sodas;
	private double discount = 3.00;

	//Meal Constructor
	public Meal(int itemId) {
		super(itemId); //calling the super constructor to initialise  itemID
		this.burrito = new Burritos(itemId);
		this.fries = new Fries(itemId);
		this.sodas = new Sodas(itemId);
	}
	//getters and setters for initialising Price, Name and calculating waitTime
	@Override
	public double getPrice()
	{
		double burritoPrice = burrito.getPrice();
		double friesPrice = fries.getPrice();
		double sodasPrice = sodas.getPrice();
		
		double totalPrice = burritoPrice + friesPrice + sodasPrice;
		
		if(totalPrice >= discount) {
			totalPrice -= discount;
		}
		
		return totalPrice;
	}
	
	@Override
	public String getName() {
		return "Meal (1 Burrito, 1 Fries, 1 Soda)";
	}
	
	@Override
	public int waitTime(int qty) {
		int burritoTime = burrito.waitTime(qty);
		int friesTime = fries.waitTime(qty);
		int sodasTime = sodas.waitTime(qty);
		int totalTime = Math.max(burritoTime,Math.max(sodasTime, friesTime));
		return totalTime;
	}

}
