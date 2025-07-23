package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Model;
import model.User;
import model.VIPUser;

public class RedeemCreditsController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	private double totalPrice;
	private double usedCredits=0.0;
	private double updatedPrice;
	
	@FXML
	private TextField redeemText;
	@FXML
	private Button redeemButton;
	@FXML
	private Button backButton;
	
	
	public RedeemCreditsController(Stage parentStage, Model model,double totalPrice) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.totalPrice = totalPrice;
		this.usedCredits = 0;
	}
	
	@FXML
	public void initialize() {
		redeemButton.setOnAction(e->redeem());
		backButton.setOnAction(e->back());
	}

	private void redeem() {
		String creditsToRedeemStr = redeemText.getText();
		if(!creditsToRedeemStr.isEmpty()) {
			int creditsToRedeem = Integer.parseInt(creditsToRedeemStr);
			
			if(creditsToRedeem > 0) {
				User currentUser = model.getCurrentUser();
				VIPUser vipUser = (VIPUser) (currentUser);
					
				int currentCredits = model.getCreditDao().getCredits(vipUser.getUsername());
				vipUser.setCredits(currentCredits);
				 if(currentCredits>=creditsToRedeem) {
					 if(vipUser.redeemCredits(creditsToRedeem)) {
						 double discount = creditsToRedeem * 0.01; //100 cr = $1 , so 1cr = $0.01
						 totalPrice -= discount;
						 usedCredits = discount;
						 updatedPrice = totalPrice - usedCredits;
						 model.getCreditDao().insertCredits(vipUser.getUsername(), vipUser.getCredits());
							
						 CreditsCheckoutController creditsCheckoutController = new CreditsCheckoutController(this.stage, model,totalPrice,usedCredits,updatedPrice);
						 creditsCheckoutController.loadCreditsCheckoutFXML();
								
						this.stage.close();
					 }
					 else
					 {
						 System.out.println("Error in redeeming credits.");
					 }
				}
				else
				{
					System.out.println("Not enough credits");
				}
			}else
			{
				System.out.println("Some error message");
			}
		}
		else
		{
			System.out.println("Empty input");
		}
	}

	private void back() {
		this.parentStage.show();
		this.stage.close();
	}
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 306, 340);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Redeem Credts");
		stage.show();
	}
	
	
	
}
