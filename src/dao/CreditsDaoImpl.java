package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Model;

public class CreditsDaoImpl implements CreditsDao{
	private final String TABLE_NAME = "credits";
	Model model;
	
	public CreditsDaoImpl(Model model) {
		this.model = model;
	}
	
	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();
			)
				{
			String createTableSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
				+ "username VARCHAR(10),"
				+ "credits INT DEFAULT 0,"
				+ "FOREIGN KEY (username) REFERENCES users(username))";
			stmt.executeUpdate(createTableSql);
		
		}
		catch(SQLException e)
		{
			System.err.print("Error");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int getCredits(String username) {
		int credits = 0;
		String get = "SELECT credits FROM " + TABLE_NAME + " WHERE username = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(get);
			){
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				credits = rs.getInt("credits");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return credits;
	}
	
	@Override
	public void insertCredits(String username, int credits){
		if(getCredits(username)>0) {
			updateCredits(username,credits);
		}
		else
		{
			String insert = "INSERT INTO " + TABLE_NAME + " (username, credits) VALUES (?,?)";
			try (Connection connection = Database.getConnection();
					PreparedStatement stmt = connection.prepareStatement(insert);
				){
				stmt.setString(1, username);
				stmt.setInt(2, credits);
				stmt.executeUpdate();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void updateCredits(String username, int newCredits) {
		String update = "UPDATE " + TABLE_NAME + " SET credits = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(update);
			){
			stmt.setInt(1, newCredits);
			stmt.setString(2, username);
			stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
		
}
