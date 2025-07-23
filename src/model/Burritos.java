package model;

public class Burritos extends FoodItem{
	public int max_per_batch = 2;
	public int reqQty = 0;
	public int leftQty = 0;
	public int waitTime = 0;
	
	//Burritos Constructor
	public Burritos(int itemID)
	{
		super(itemID); //calling the super constructor to initialise  itemID
	}
	//getters and setters for initialising Price, Name and calculating waitTime
	@Override
	public double getPrice()
	{
		return 7.0;
	}
	
	@Override
	public String getName()
	{
		return "Burritos";
	}
	
	//Returning the wait Time to prepare the quantity of burritos ordered
	@Override
	public int waitTime(int qty){	
		reqQty = qty;
	
		if(leftQty > 0)
		{
			if(reqQty < max_per_batch)
			{
				reqQty = 1;
				leftQty  = leftQty - reqQty;
			}
			
			else if(reqQty >= max_per_batch)
			{
				reqQty = reqQty - leftQty;
				leftQty = 0;
			}
		}
		
		if(reqQty < max_per_batch) 
		{	
			waitTime = 9;
			leftQty = max_per_batch - reqQty;
		}
		
		else if(reqQty == max_per_batch)
		{
			waitTime = 9;
		}
		else
		{	
			if(reqQty % 2!=0)
			{
			waitTime = ((reqQty/max_per_batch) + 1 ) * 9;
			leftQty = reqQty % 2;
			}
			
			else
			{
				waitTime = (reqQty/2) * 9;
			}
		}
		return waitTime;
//		int batchesNeeded = (int) Math.ceil((double)qty/max_per_batch);
//		leftQty = Math.max(0, (batchesNeeded * max_per_batch)-qty);
//		return batchesNeeded * 9;
	}
	
//	public int getLeftQty()
//	{
//		return leftQty;
//	}
}



