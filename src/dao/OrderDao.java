package dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import model.FoodItem;
import model.Model;
import model.Order;
import model.OrderStatus;

/**
 * A data access object (DAO) is a pattern that provides an abstract interface 
 * to a database or other persistence mechanism. 
 * the DAO maps application calls to the persistence layer and provides some specific data operations 
 * without exposing details of the database. 
 */
public interface OrderDao {
	void setup() throws SQLException;
	boolean orderIDExists(String orderID);
	boolean checkUsernameOrders(String username);
	//void addFood(String username,String orderID,int foodID, String foodName, double totalPrice,int quantity,boolean isVIP,FoodItem foodItem,Model model)throws SQLException;
	//int getStatusID(String statusName);
	void addOrder(Order order,Model model);
	List<Order> getOrders(String username);
	boolean updateOrderStatus(String orderID, OrderStatus status);
	
}
