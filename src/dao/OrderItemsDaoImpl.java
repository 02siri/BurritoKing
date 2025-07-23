package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.FoodItem;
import model.ItemFactory;
import model.Order;

public class OrderItemsDaoImpl implements OrderItemsDao{
	private final String TABLE_NAME = "orderItems";
	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();
			)
				{
			String createTableSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
				+ "orderID VARCHAR(20),"
				+ "itemID INT,"
				+ "quantity INT,"
				+ "price DECIMAL (10,2),"
				+ "FOREIGN KEY (orderID) REFERENCES orders(orderID),"
				+ "FOREIGN KEY (itemID) REFERENCES foodItems(itemID))";
			stmt.executeUpdate(createTableSql);
		
		}
		catch(SQLException e)
		{
			System.err.print("Error");
			e.printStackTrace();
		}
		
	}
	
	
	public void addItems(Order order) {
		String insertItems = "INSERT INTO " + TABLE_NAME + " (orderID, itemID, quantity, price) VALUES (?,?,?,?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(insertItems);)
		{
			for(FoodItem item : order.getFoodItems()) {
				stmt.setString(1, order.getOrderID());
				stmt.setInt(2, item.getItemID());
				stmt.setInt(3,item.getQty());
				stmt.setDouble(4, item.getTotalPrice());
				stmt.addBatch();
			}
			stmt.executeBatch();
			System.out.println("Items added into orderItems table");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public List<FoodItem> getFoodItems(String orderID) {
		List <FoodItem> foodItems = new ArrayList<>();
		String getFoodItems = "SELECT oi.itemID, oi.quantity, oi.price, fi.foodName FROM " + TABLE_NAME + " oi JOIN foodItem fi ON oi.ItemID = fi.ItemID WHERE oi.orderID = ?";
		try(Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(getFoodItems);)
		{
			stmt.setString(1,orderID);
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
					int itemID = rs.getInt("itemID");
					String itemName = rs.getString("foodName");
					int totalQuantity = rs.getInt("quantity");
					double totalPrice = rs.getDouble("price");
					FoodItem foodItem = ItemFactory.createFoodItem(itemID, itemName);
					foodItem.setTotalPrice(totalPrice);
					foodItem.setQty(totalQuantity);
					
					foodItems.add(foodItem);
					
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return foodItems;
	}

}