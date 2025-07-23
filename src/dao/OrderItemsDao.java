package dao;
import java.sql.SQLException;
import java.util.List;

import model.FoodItem;
import model.Order;

public interface OrderItemsDao {
	void setup() throws SQLException;
	void addItems(Order order);
	List<FoodItem> getFoodItems(String orderID);
}