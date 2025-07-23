package dao;

import java.sql.SQLException;
import java.util.List;

import model.FoodItem;

public interface FoodItemsDao {
	void setup() throws SQLException;
	void insertFoodItem(FoodItem foodItem) throws SQLException;
	void insertFoodItems(List<FoodItem> foodItems) throws SQLException;
}
