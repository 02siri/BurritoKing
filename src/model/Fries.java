package model;
public class Fries extends FoodItem{

	public int max_per_batch = 5;
	public int reqQty = 0;
	public int leftBatch = 0;
	public int waitTime = 8;
	public int remaining_qty_to_cook = 0;
	public int remaining_batches_to_cook = 0;
	
	//Fries Constructor
	public Fries(int itemID) {
		super(itemID); //calling the super constructor to initialise  itemID
	}
	//getters and setters for initialising Price, Name and calculating waitTime
	@Override
	public double getPrice()
	{
		return 4.00;
	}
	

	@Override
	public String getName()
	{
		return "Fries";
	}
	
	//Returning the wait time to prepare the serves of fries ordered, 
	//along with calculating the left serves of fries for the next order
	public int waitTime(int qty)
	{
		reqQty = qty;
		
		if(reqQty < max_per_batch)
		{
			//no left serves of fries
			if(leftBatch == 0)
			{
			remaining_qty_to_cook = reqQty;
			remaining_batches_to_cook = 1;
			waitTime = 8;
			leftBatch = max_per_batch - remaining_qty_to_cook;
			}
			
			else if(leftBatch>0)
			{
				if(leftBatch >= reqQty)
				{
					waitTime = 0;
					leftBatch = 0;
				}
				
				else if(leftBatch < reqQty)
				{
					remaining_qty_to_cook = reqQty - leftBatch;
					remaining_batches_to_cook = 1;
					waitTime = 8;
					leftBatch = max_per_batch - remaining_qty_to_cook;
					//System.out.println(leftBatch + " serves of fries left for next order");
					
				}
			}
		}
		
		else if (reqQty >= max_per_batch)
		{
			if(leftBatch >= reqQty)
				{
				waitTime = 0;
				leftBatch = leftBatch- reqQty;
			
				//System.out.println(leftBatch + " serves of fries left for next order");
				}
		
			else
			{
			remaining_qty_to_cook = reqQty - leftBatch;
			
			if(remaining_qty_to_cook >= max_per_batch)
			{
				if(remaining_qty_to_cook % max_per_batch != 0)
				{
				remaining_batches_to_cook = remaining_qty_to_cook/max_per_batch + 1;
				}
			
				else
				{
				remaining_batches_to_cook = remaining_qty_to_cook/max_per_batch; 
				}
				waitTime = 8 * remaining_batches_to_cook;
				leftBatch = max_per_batch - remaining_batches_to_cook;
				//System.out.println(leftBatch + " serves of fries left for next order");
			}
			
			else if(remaining_qty_to_cook < max_per_batch)
			{
				remaining_batches_to_cook = 1;
				waitTime = 8;
				leftBatch = max_per_batch - remaining_qty_to_cook;
				//System.out.println(leftBatch + " serves of fries left for next order");
			}
			
		}
		}
		
		return waitTime;
		
//		if(qty <= max_per_batch) {
//			return 0;
//		}
//		else {
//			int batchesNeeded = (int)Math.ceil((double)qty / max_per_batch);
//			remaining_qty_to_cook = Math.max(0, (batchesNeeded * max_per_batch)-qty);
//			return batchesNeeded * 8;
//		}
	}
	
//	public int getLeftQty()
//	{
//		return remaining_qty_to_cook;
//	}


}

