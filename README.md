Assignment 2 - Further Programming Readme File
1. Program Class Design:
   following the MVC Pattern, the class design of my project is as follows:
  A. Inheritance:
  The classes User.java and VIPUser.java follow inheritance, since VIPUser.java is a child class to User.java and inhertits its methods and properties.
  B. Encapsulation:
  Mostly all the classes in the project follow encapsulation, since the methods and attributes that are to be set private and kept private and not accessed by any outside class.     Wherever neecessary, my classes have attributes and methods of public visibility, so that it can be accessed by other classes.
  C.Abstraction :
  Abstration is widely used in my project, since I made the class FoodItem as an abstract class and all its related food items shared the abstract methods declared in the FoodItem   class.
  Another way where abstraction is being followed is by creating interfaces of all the Dao classes, and then implementing tham in their respective sub-classes. 
  D. Composition
  Composition is heavily followed in the project, since the model classes are part of a lot of methods on the controller classes.The logics defined in the model classes are used     by the controlled classes, to thus make changes in the files in the view package.

3. Design Pattern Implemented:
   I have implemented two design patterns - Factory Design Pattern and Facade Design Pattern.
   The Factory design pattern is followed by the model class ItemFactory, and the Facade design pattern is followed by the model class Model.

4. JUnit Test Implemented:
   I have implemented 5 unit tests for the User model class in the class UserTest.java.
   In this class, I have tested the comparison of the username, password,firstName, lastName and email of the user with the mock input provided
   Following are the unit tests:
   package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
	@Test
	public void testUsername() {
		User user = new User("John_brick","john1234");
		assertEquals("John_brick",user.getUsername());
	}
	@Test
	public void testPassword() {
		User user = new User("John_brick","john1234");
		assertEquals("john1234",user.getPassword());
	}
	@Test
	public void testFirstName() {
		User user = new User("John_brick","john1234");
		user.setFirstName("John");
		assertEquals("John",user.getFirstName());
	}
	@Test
	public void testLastName() {
		User user = new User("John_brick","john1234");
		user.setLastName("Brick");
		assertEquals("Brick",user.getLastName());
	}
	@Test
	public void testEmail() {
		User user = new User("John_brick","john1234");
		user.setEmail("john@brick.com");
		assertEquals("john@brick.com",user.getEmail());
	}
	
}
