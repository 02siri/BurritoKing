package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//parent class to VIPUser
public class User {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Order order;
	public String email;
	public LocalDate dob;
	public Boolean isVIP;
	public Model model;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User() {}
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public LocalDate getDOB()
	{
		return dob;	
	}
	public void setDOB(LocalDate dob)
	{
		this.dob = dob;
	}
	public Boolean getisVIP() {
		return isVIP;
	}
	public void setisVIP(Boolean isVIP) {
		this.isVIP = isVIP;
	}
	
	public Order getOrder()
	{
		return this.order;
	}
	
	public void setOrder(Order order)
	{
		this.order = order;
	}

}
