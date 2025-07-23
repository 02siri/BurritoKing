package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Model;

public class CreditsCheckoutController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	private double originalPrice;
	private double redeemedCredits;
	private double updatedPrice;
	@FXML
	private Label originalPriceLabel;
	@FXML
	private Label creditsUsed;
	@FXML
	private Label updatedTotalPrice;
	@FXML
	private Button redeemMoreLess;
	@FXML
	private Button paymentCredits;
	
	public CreditsCheckoutController(Stage parentStage, Model model, double originalPrice, double redeemedCredits, double updatedPrice) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.originalPrice = originalPrice;
		this.redeemedCredits = redeemedCredits;
		this.updatedPrice = updatedPrice;
	
	}
	
	public void loadCreditsCheckoutFXML() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreditsCheckout.fxml"));
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
		originalPriceLabel.setText("$" + originalPrice);
		creditsUsed.setText(String.format("%.2f", redeemedCredits));
		updatedTotalPrice.setText("$" + updatedPrice);
		
		redeemMoreLess.setOnAction(e->redeemMoreLessButton());
		paymentCredits.setOnAction(e->paymentCreditsButton());
		
	}
	private void paymentCreditsButton() {
		PaymentController paymentController = new PaymentController(this.stage,model,updatedPrice);
		paymentController.loadPaymentView();
		stage.close();
	}

	private void redeemMoreLessButton() {
		this.parentStage.show();
		this.stage.close();
	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 306, 340);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Credits Checkout");
		stage.show();
		
	}
}
