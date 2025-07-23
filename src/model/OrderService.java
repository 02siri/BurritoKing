package model;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
	private Model model;
	
	public OrderService(Model model) {
		this.model = model;
	}
	
	public List<Order> getActiveOrders(String username) {
		
		List<Order> allOrders = model.getOrderDao().getOrders(username);
		List<Order> activeOrders = new ArrayList<>();
		for(Order order : allOrders) {
			if(order.getOrderStatus().getID()==1) {
				activeOrders.add(order);
			}
		}
		return activeOrders;
	}
	
	public List<Order> getAllOrders(String username){
		List<Order> allOrders = model.getOrderDao().getOrders(username);
		return allOrders;
	}
	
}
