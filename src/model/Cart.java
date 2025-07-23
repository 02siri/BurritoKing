package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
	private List <FoodItem> cartItems;
	
	public Cart() {
		cartItems = new ArrayList<>();
	}
	
	public void addFoodItem(FoodItem item) {
		for(FoodItem cartItem : cartItems) {
			if(cartItem.getItemID()==item.getItemID()) {
				cartItem.setQty(item.getQty());
				return;
			}
		}
		
		cartItems.add(item);
	}
	
	public void removeFoodItem(FoodItem item) {
		Iterator<FoodItem> iterator = cartItems.iterator();
		while(iterator.hasNext()) {
			FoodItem cartItem = iterator.next();
			if(cartItem.getItemID() == item.getItemID()) {
				iterator.remove();
				return;
			}
		}
	}

	
	public List <FoodItem> getItems()
	{
		return cartItems;
	}
	
	public boolean isEmpty() {
		return cartItems.isEmpty();
				
	}
	
}
