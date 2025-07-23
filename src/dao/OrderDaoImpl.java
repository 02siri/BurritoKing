package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.OrderStatus;
import model.User;
import model.FoodItem;
import model.Model;

public class OrderDaoImpl implements OrderDao{
	private final String TABLE_NAME = "orders";
	Model model;
	public OrderDaoImpl() {
		
	}

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();
			)
				{  
			String createTableSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
				+ "username VARCHAR (10),"
				+ "orderID VARCHAR,"
				+ "orderDateTime DATETIME,"
				+ "statusID INTEGER,"
				+ "totalPrice DECIMAL (10,2),"
				+ "PRIMARY KEY (orderID),"
				+ "FOREIGN KEY (username) REFERENCES users(username),"
				+ "FOREIGN KEY (statusID) REFERENCES orderStatus(statusID))";
			stmt.executeUpdate(createTableSql);
		
		}
		catch(SQLException e)
		{
			System.err.print("Error");
			e.printStackTrace();
		}
		 
	}
	
	public void addOrder(Order order,Model model) {
		String addOrder = "INSERT INTO  " + TABLE_NAME +  " (username, orderID, orderDateTime, statusID,totalPrice) VALUES (?,?,?,?,?,?)";
		try(Connection connection = Database.getConnection();
			PreparedStatement stmt = connection.prepareStatement(addOrder)){
			
			stmt.setString(1, model.getCurrentUser().getUsername());
			stmt.setString(2, order.getOrderID());
			stmt.setObject(3, order.getDateTime());
			stmt.setInt(4, order.getOrderStatus().getID());
			stmt.setDouble(5, order.getTotalPrice());
			stmt.executeUpdate();
			
			// model.getOrderItemsDao().addItems(order);
			} 
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean checkUsernameOrders(String username){
		String checkUsername = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE username = ?";
		try(Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(checkUsername);
			){
			stmt.setString(1, username);
			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()) {
					return rs.getInt(1)>0; //returns true if count of the first row from the first column is greater than 0
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean orderIDExists(String orderID)
	{
		boolean exist = false;
		String existQuery = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE orderID = ?";
		try(Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(existQuery))
		{	
			stmt.setString(1, orderID);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				return rs.getInt(1)>0;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exist;
		
	}

	@Override
	public List<Order> getOrders(String username) {
		List<Order> orders = new ArrayList<>();
		String getOrders = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
		try(Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(getOrders)){
			stmt.setString(1, username);
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
					Order order = new Order();
					order.setOrderID(rs.getString("orderID"));
					order.setDateTime(rs.getObject("orderDateTime", LocalDateTime.class));
					int statusID = rs.getInt("statusID");
					OrderStatus status = null;
					switch(statusID) {
					case 1:
						status = OrderStatus.PLACED;
						break;
					case 2:
						status = OrderStatus.COLLECTED;
					case 3:
						status = OrderStatus.CANCELLED;
						break;
					default:
						break;
					}
					
					order.setOrderStatus(status);
					order.setTotalPrice(rs.getDouble("totalPrice"));
					
					orders.add(order);
				}
			}
			}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}
	
	@Override
	public boolean updateOrderStatus(String orderID, OrderStatus status) {
		String updateStatus = "UPDATE " + TABLE_NAME + " SET statusID = ? WHERE orderID = ?";
		try(Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(updateStatus)){
			stmt.setInt(1, status.getID());
			stmt.setString(2, orderID);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
