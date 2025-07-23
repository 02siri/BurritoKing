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
