package model;

public class Shakes extends FoodItem{
	public int max_per_batch = 2;
	public int reqQty = 0;
	public int leftQty = 0;
	public int waitTime = 0;
	
	//Shakes Constructor
	public Shakes(int itemID)
	{
		super(itemID); //calling the super constructor to initialise  itemID
	}
	//getters and setters for initialising Price, Name and calculating waitTime
	
	@Override
	public double getPrice()
	{
		return 7.50;
	}
	
	@Override
	public String getName()
	{
		return "Shakes";
	}
	
	@Override
	public int waitTime(int qty)
	{	waitTime = 0*qty;
		return waitTime;
	}
}



