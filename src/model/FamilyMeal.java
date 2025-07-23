package model;

public class FamilyMeal extends FoodItem{
	private Burritos burrito;
	private Fries fries;
	private Sodas sodas;
	private Burgers burgers;
	private Shakes shakes;
	private static double discount = 5.00;
	
	public FamilyMeal(int itemId) {
		super(itemId);
		this.burrito = new Burritos(itemId);
		this.fries = new Fries(itemId);
		this.sodas = new Sodas(itemId);
		this.burgers = new Burgers(itemId);
		this.shakes = new Shakes(itemId);
	}

	@Override
	public double getPrice()
	{
		double burritoPrice = burrito.getPrice();
		double friesPrice = fries.getPrice();
		double sodasPrice = sodas.getPrice();
		double burgersPrice = burgers.getPrice();
		double shakesPrice = shakes.getPrice();
		
		double totalPrice = burritoPrice + friesPrice + sodasPrice + burgersPrice + shakesPrice;
	
		totalPrice -=discount;
		
		if(totalPrice < 0) {
			totalPrice = 0;
		}
		
		return totalPrice;
	}
	
	@Override
	public String getName() {
		return "Family Meal (1 Burrito, 1 Fries, 1 Soda, 1 Burger, 1 Shake)";
	}
	
	@Override
	public int waitTime(int qty) {
		int burritoTime = burrito.waitTime(qty);
		int friesTime = fries.waitTime(qty);
		int sodasTime = sodas.waitTime(qty);
		int burgerTime = burgers.waitTime(qty);
		int shakesTime = shakes.waitTime(qty);
		
		int totalTime =Math.max(burritoTime,Math.max(friesTime,Math.max(sodasTime,Math.max(shakesTime, burgerTime))));
		return totalTime;
	}

}
