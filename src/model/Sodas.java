package model;
public class Sodas extends FoodItem{

	public int waitTime = 0;
	
	//Sodas Constructor
	public Sodas(int itemID) {
		super(itemID); //calling the super constructor to initialise  itemID
	}
	
	//getters and setters for initialising Price, Name and calculating waitTime
	@Override
	public double getPrice()
	{
		return 2.50;
	}
	
	
	@Override
	public String getName()
	{
		return "Sodas";
	}

	
	@Override
	public int waitTime(int qty)
	{
		waitTime = 0 * qty;
		return waitTime;
	}
	
}


	
