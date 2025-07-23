package model;

public class Burgers extends FoodItem{
	public int max_per_batch = 2;
	public int reqQty = 0;
	public int leftQty = 0;
	public int waitTime = 0;
	
	//Burgers constructor
	public Burgers(int itemID)
	{
		super(itemID); //calling the super constructor to initialise itemID
	}
	
	//getters and setters for initialising Price, Name and calculating waitTime
	@Override
	public double getPrice()
	{
		return 8.50;
	}
	
	
	@Override
	public String getName()
	{
		return "Burgers";
	}
	
	@Override
	public int waitTime(int qty)
	{ 	waitTime = 8;
		return waitTime*qty;
	}
}



