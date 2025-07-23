package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Model;
import model.User;
import model.VIPUser;

public class CheckoutController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	
	private double totalPrice;
	private int totalWaitTime;
	private double discount;
	
	@FXML
	private Label totalPriceLabel;
	@FXML
	private Label totalWaitTimeLabel;
	@FXML
	private Label creditsLabel;
	@FXML
	private Button redeemCreditsCheckout;
	@FXML
	private Button redeemButton;
	@FXML
	private TextField redeemText;
	@FXML
	private Button backToOrder;
	@FXML
	private Button payment;
	
	public User currentUser;
	
	
	
	public CheckoutController(Stage parentStage, Model model,double totalPrice, int totalWaitTime) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.totalPrice = totalPrice;
		this.totalWaitTime = totalWaitTime;
	}
	
	public void loadCheckoutView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Checkout.fxml"));
			CheckoutController checkoutController = new CheckoutController(parentStage,model,totalPrice, totalWaitTime);
			checkoutController.setUser(currentUser);
			loader.setController(this);
			Pane root = loader.load();
			showStage(root);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setUser(User user) {
		this.currentUser = user;
	}
	@FXML
	public void initialize() {
		totalPriceLabel.setText("$" + totalPrice);
		totalWaitTimeLabel.setText(totalWaitTime + " minutes");
		
		User currentUser = model.getCurrentUser();
		System.out.println("Current User class: " + currentUser.getClass().getName());
		if(currentUser instanceof VIPUser) {
			VIPUser vipUser = (VIPUser) (currentUser);
				redeemCreditsCheckout.setVisible(true);
				creditsLabel.setVisible(true);
		}
		else
		{
			redeemCreditsCheckout.setVisible(false);
			creditsLabel.setVisible(false);
		}
		
		redeemCreditsCheckout.setOnAction(e->redeemCredits());
		backToOrder.setOnAction(e->backToOrderButton());
		payment.setOnAction(e->paymentButton());
	}
	
	private void redeemCredits() {
		
		User currentUser = model.getCurrentUser();
		
		if(currentUser instanceof VIPUser) {
			VIPUser vipUser = (VIPUser) (currentUser);
			int currentCredits = model.getCreditDao().getCredits(vipUser.getUsername());
			vipUser.setCredits(currentCredits);
			if(currentCredits>0) {

				System.out.println("Current credits: " + vipUser.getCredits());
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RedeemCredits.fxml"));
					RedeemCreditsController redeemCreditsController = new RedeemCreditsController(parentStage,model,totalPrice);
					//redeemCreditsController.setUser(currentUser);
					loader.setController(redeemCreditsController);
					Pane root = loader.load();
					showStage(root);
					//this.stage.hide();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				else
				{
					creditsLabel.setText("Not enough credits.");
				}
		}
		else
		{
			creditsLabel.setText("currentUser is not an instanceof VIPUser");
		}
	}

	public void paymentButton() {
		
		PaymentController paymentController = new PaymentController(this.stage,model,totalPrice);
		paymentController.loadPaymentView();
		stage.hide();
	}

	public void backToOrderButton() {
		this.parentStage.show();
		this.stage.close();
		
	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 306, 340);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Checkout");
		stage.show();
	}
}
