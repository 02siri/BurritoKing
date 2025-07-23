package model;

public class OrderStatus {
	public static final OrderStatus PLACED = new OrderStatus(1,"PLACED");
	public static final OrderStatus COLLECTED = new OrderStatus(2,"COLLECTED");
	public static final OrderStatus CANCELLED = new OrderStatus(3,"CANCELLED");
	
	private int id;
	private String status;
	
	public OrderStatus(int id, String status)
	{	this.id = id;
		this.status = status;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	public String getStatus()
	{
		return status;
	}
	
	public String toString()
	{
		return status;
	}
}
