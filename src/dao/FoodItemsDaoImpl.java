package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.FoodItem;

public class FoodItemsDaoImpl implements FoodItemsDao{
	private String TABLE_NAME = "foodItem";

		public FoodItemsDaoImpl() {
		}

		@Override
		public void setup() throws SQLException {
			try (Connection connection = Database.getConnection();
					Statement stmt = connection.createStatement();
				)
					{
				String createTableSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
					+ "itemID INT,"
					+ "foodName VARCHAR(20),"
					+ "PRICE DECIMAL(10,2),"
					+ "PRIMARY KEY (itemID))";
				stmt.executeUpdate(createTableSql);
			}
			catch(SQLException e)
			{
				System.err.print("Error");
				e.printStackTrace();
			}
			 
		}
		
		
		@Override
		public void insertFoodItem(FoodItem foodItem) throws SQLException{
			String ifExists = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE itemID = ?";
			String insert = "INSERT INTO "+ TABLE_NAME + " (itemID,foodName,price) VALUES (?,?,?)";
			try (Connection connection = Database.getConnection();
					PreparedStatement insertstmt = connection.prepareStatement(insert);
					PreparedStatement checkstmt = connection.prepareStatement(ifExists)){
				
				checkstmt.setInt(1, foodItem.getItemID());
				ResultSet rs = checkstmt.executeQuery();
				rs.next();
				int count = rs.getInt(1);
				
				if(count == 0) {
					insertstmt.setInt(1, foodItem.getItemID());
					insertstmt.setString(2, foodItem.getName());
					insertstmt.setDouble(3, foodItem.getPrice());
					insertstmt.executeUpdate();
				}
				
			}catch(SQLException e)
			{	System.out.println("Error inserting food item");
				e.printStackTrace();
			}
					
		}
		
		@Override
		public void insertFoodItems(List<FoodItem> foodItems) throws SQLException{
			for(FoodItem foodItem : foodItems) {
				insertFoodItem(foodItem);
			}
		}
		
	}
