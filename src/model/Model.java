package model;

import java.sql.SQLException;

import dao.UserDao;
import dao.UserDaoImpl;
import dao.OrderDao;
import dao.OrderDaoImpl;
import dao.CreditsDao;
import dao.CreditsDaoImpl;
import dao.FoodItemsDao;
import dao.FoodItemsDaoImpl;
import dao.OrderItemsDao;
import dao.OrderItemsDaoImpl;

public class Model {
	private UserDao userDao;
	private OrderDao orderDao;
	private FoodItemsDao foodItemsDao;
	private CreditsDao creditsDao;
	private OrderItemsDao orderItemsDao;
	private User currentUser; 
	private Order currentOrder;
	private Cart cart;
	
	
	public Model() {
		userDao = new UserDaoImpl();
		orderDao = new OrderDaoImpl();
		foodItemsDao = new FoodItemsDaoImpl();
		creditsDao = new CreditsDaoImpl(this);
		orderItemsDao = new OrderItemsDaoImpl();
		currentOrder = null;
		this.cart = new Cart();
	}
	
	
	public void setup() throws SQLException {
		userDao.setup();
		orderDao.setup();
		foodItemsDao.setup();
		orderItemsDao.setup();
		creditsDao.setup();
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
		System.out.println("In model class - updated current User to :" + user.getClass().getName());
	}
	
	public Order getCurrentOrder() {
		return currentOrder;
	}
	
	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}
	
	public Cart getCart()
	{
		return cart;
	}
	
	
	public void addItem(FoodItem item) {
		if(item.getQty()>0) {
			cart.addFoodItem(item);
		}
	}
	
	public void removeItem(FoodItem item) {
		cart.removeFoodItem(item);
	}
	public UserDao getUserDao() {
		return userDao;
	}
	
	public OrderDao getOrderDao() {
		return orderDao;
	}
	
	public FoodItemsDao getFoodItemsDao() {
		return foodItemsDao;
	}
	
	public OrderItemsDao getOrderItemsDao() {
		return orderItemsDao;
	}
	public CreditsDao getCreditDao() {
		return creditsDao;
	}
	
}
