package dao;

import java.sql.SQLException;
import java.time.LocalDate;

import model.User;
import model.VIPUser;

/**
 * A data access object (DAO) is a pattern that provides an abstract interface 
 * to a database or other persistence mechanism. 
 * the DAO maps application calls to the persistence layer and provides some specific data operations 
 * without exposing details of the database. 
 */
public interface UserDao {
	void setup() throws SQLException;
	User getUser(String username, String password) throws SQLException;
	User createUser(String username, String password, String firstName, String lastName) throws SQLException;
	boolean checkField(String field, String value) throws SQLException;
	void updateVIPfields(String username, String email, LocalDate dob,boolean isVIP) throws SQLException;
	void updateProfile(String username, String field, String newVal);
	void unsubscribeFromVIP(String username);
}
