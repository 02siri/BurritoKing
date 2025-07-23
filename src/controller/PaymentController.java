package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import dao.CreditsDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Model;
import model.Order;
import model.OrderStatus;
import model.Payment;
import model.User;
import model.VIPUser;

public class PaymentController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	private double totalPrice;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	@FXML
	private TextField cardNoText;
	@FXML
	private DatePicker expiryDate;
	@FXML
	private PasswordField cvvText;
	@FXML
	private TextField orderDateTime;
	@FXML
	private Label messageLabel;
	@FXML
	private Button backPayment;
	@FXML
	private Button confirm;
	@FXML
	private Button placeOrder;
	
	
	public PaymentController(Stage parentStage, Model model,double totalPrice) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.totalPrice = totalPrice;
	}
	
	public void loadPaymentView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Payment.fxml"));
			loader.setController(this);
			Pane root = loader.load();
			showStage(root);
		}catch(IOException e)
		{
			e.printStackTrace();
		}	
		
	}
	@FXML
	public void initialize() {
		CreditsDaoImpl creditsDao = new CreditsDaoImpl(model);
		backPayment.setOnAction(e->backPaymentButton());
		confirm.setOnAction(e->confirmDetails());
		placeOrder.setOnAction(e->placeOrderButton());
	}

	private void confirmDetails() {
		String cardNo = cardNoText.getText();
		LocalDate expiry = expiryDate.getValue();
		String cvv = cvvText.getText();	

		if(cardNo.isEmpty() || expiry==null || cvv.isEmpty() || orderDateTime.getText().isEmpty()) {
			messageLabel.setText("Please fill in all the fields");
		}
		else {
			try {
				LocalDateTime orderDatetime = LocalDateTime.parse(orderDateTime.getText(),formatter);
				Payment payment = new Payment(cardNo,expiry,Integer.parseInt(cvv));
				
				
				if(!payment.checkCardNo()) {
					messageLabel.setText("The card number must be of 16 digits");
				}
				
				if(!payment.checkExpiryDate()) {
					messageLabel.setText("Please enter future expiry date");
				}
				
				if(!payment.checkCvv()) {
					messageLabel.setText("The CVV must be of 3 digits only");
				}
				
				if(!payment.checkOrderDateTime(orderDatetime)) {
					messageLabel.setText("Please enter a current or future order date and time");
				}
				if(payment.checkCardNo()&&payment.checkCvv()&&payment.checkExpiryDate()){
					messageLabel.setText("Card Details entered successfuly");
				}
			}catch(DateTimeParseException | NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void backPaymentButton() {
		this.parentStage.show();
		this.stage.close();
	}
	
	public void placeOrderButton() {
		String orderDateTimeString = orderDateTime.getText();
		LocalDateTime orderDatetime = LocalDateTime.parse(orderDateTime.getText(),formatter);
		
		try {
			Order order = new Order(model);
			order.generateOrderID();
			order.setDateTime(orderDatetime);
			order.setOrderStatus(OrderStatus.PLACED);
			order.setTotalPrice(totalPrice);
			
			User currentUser = model.getCurrentUser();
			if(currentUser instanceof VIPUser) {
				VIPUser vipUser = (VIPUser) (currentUser);
				int currentCredits = model.getCreditDao().getCredits(vipUser.getUsername());
				
				int collectedCredits = vipUser.collectCredits(totalPrice);
				
				int totalCredits = currentCredits + collectedCredits;
				vipUser.setCredits(totalCredits);
				
				model.getCreditDao().insertCredits(vipUser.getUsername(), vipUser.getCredits());
			}
			model.getOrderDao().addOrder(order,model);
			model.getOrderItemsDao().addItems(order);
			model.setCurrentOrder(order);
			
			System.out.println("Order placed with ID: "+ order.getOrderID());
			ConfirmationController confirmController = new ConfirmationController(parentStage,model);
			confirmController.loadConfirmView();
			this.stage.close();
		}catch(DateTimeParseException e) {
			e.printStackTrace();
		}
		
	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Order Status");
		stage.show();
	}

	
}
