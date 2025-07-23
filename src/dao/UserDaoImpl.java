package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import model.User;
import model.VIPUser;

public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "users";
	User user = new User();
	public UserDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();
			)
				{
			String createTableSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
				+ "username VARCHAR(10) NOT NULL,"
				+ "password VARCHAR(8) NOT NULL,"
				+ "firstName VARCHAR(20) NOT NULL,"
				+ "lastName VARCHAR(20) NOT NULL,"
				+  "email VARCHAR(50),"
				+  "dob DATE,"
				+  "isVIP BOOLEAN,"
				+ "PRIMARY KEY (username))";
			stmt.executeUpdate(createTableSql);
		}
		catch(SQLException e)
		{
			System.err.print("Error");
			e.printStackTrace();
		}
		 
	}

	@Override
	public User getUser(String username, String password) throws SQLException {
		String getUser = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(getUser);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user;
					if(rs.getBoolean("isVIP")) {
						user = new VIPUser(username, password);
					}
					else
					{
						user = new User(username, password);
					}
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setFirstName(rs.getString("firstName"));
					user.setLastName(rs.getString("lastName"));
					user.setEmail(rs.getString("email"));
					if(rs.getDate("dob")!=null)
					{
						user.setDOB(rs.getDate("dob").toLocalDate());
					}
					user.setisVIP(rs.getBoolean("isVIP"));
					
					return user;
				}
				return null;
			} 
		}
	}

	@Override
	public User createUser(String username, String password, String firstName, String lastName) throws SQLException {
		String createUser = "INSERT INTO " + TABLE_NAME + "(username, password, firstName, lastName) VALUES (?, ?,?,?)";
		try (
				Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(createUser);
			){
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, firstName);
			stmt.setString(4, lastName);

			stmt.executeUpdate();
			
			user.setUsername(username);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			return user;
					
		} 
	}
	@Override
	public boolean checkField(String field, String value) throws SQLException{
		String checkUsername = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + field + " = ?";
		try(Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(checkUsername);
			){
			stmt.setString(1, value);
			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()) {
					return rs.getInt(1)>0; //returns true if count of the first row from the first column is greater than 0
				}
			}
		}
		return false;
	}
	@Override
	public void updateVIPfields(String username, String email, LocalDate dob,boolean isVIP) throws SQLException
	{	String update = "UPDATE " + TABLE_NAME +" SET email = ?, dob = ?, isVIP = ? WHERE username = ?";
		try(Connection connection = Database.getConnection();
			PreparedStatement updateStmt = connection.prepareStatement(update)){
				updateStmt.setString(1,email);
				updateStmt.setDate(2, Date.valueOf(dob));
				updateStmt.setBoolean(3, isVIP);
				updateStmt.setString(4, username);
				int rowsAffected = updateStmt.executeUpdate();
				if(rowsAffected>0)
				{
					System.out.println("User details updated successfully.");
				}
				else
				{
					System.out.println("No rows affected");
				}
			}
		}
	
	@Override
	public void updateProfile(String username,String field,String newVal)
	{	
		String updateProfile = "UPDATE " + TABLE_NAME + " SET " + field + " = ? WHERE username = ?";
		try(Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(updateProfile)){
			stmt.setString(1, newVal);
			stmt.setString(2, username);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
		
	@Override
	public void unsubscribeFromVIP(String username)
	{
		String updateProfile = "UPDATE " + TABLE_NAME + " SET email = NULL, dob = NULL ,isVIP = ? WHERE username = ?";
		try(Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(updateProfile)){
			user.setisVIP(false);
			stmt.setBoolean(1, user.getisVIP());
			stmt.setString(2, username);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}		


