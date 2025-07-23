package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;
import model.VIPUser;

public class VIPUserController extends UserController{
	private Model model;
	private Stage stage;
	private Stage parentStage;
	private User currentUser;
	@FXML
	private Button emailYes;
	@FXML
	private Button emailNo;   
	@FXML 
	private Label email;
	@FXML
	private Button logout;
	@FXML
	private TextArea emailAdd;
	@FXML
	private DatePicker dob;
	@FXML
	private Label status;
	@FXML
	private Button backStep2;
	
	
	public VIPUserController(Stage parentStage, Model model, User currentUser) {
		super(parentStage, model);
		this.stage = new Stage();
		this.model = model;
		this.parentStage = parentStage;
		setUser(currentUser);
	}

	@FXML
	public void initialize() {
		if(currentUser!=null) {
			setUser(currentUser);
		}
		
		emailYes.setOnAction(e->emailYesButton());
		emailNo.setOnAction(e->emailNoButton());
		email.setOnMouseEntered(e->emailHover());
		if(this.backStep2!=null)
		{
			backStep2.setOnAction(e->backButtonStep2());
		}
		if(this.logout!=null)
		{
			logout.setOnAction(e->logoutButton());
		}

	}
	
	public void setUser(User user) {
		//user = model.getCurrentUser();
		this.currentUser = user;
		
	}

	public void loadVipStep1()
	{	
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VipStep1.fxml"));
			VIPUserController vipController = new VIPUserController(parentStage, model,currentUser);
			loader.setController(vipController);
			Pane root = loader.load();
			vipController.showStage(root);
			stage.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void emailYesButton()
	{
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VipStep2.fxml"));
			loader.setController(this);
			Pane root = loader.load();
			Scene scene = new Scene(root, 600, 400);
			stage.setScene(scene);
			stage.show();

			}catch(IOException e) {
			e.printStackTrace();
			System.out.println("Error loading VipStep2.fxml");
		}
		
	}
		
	public void emailNoButton() 
	{
		this.parentStage.show();
		this.stage.close();
	}
		
	
	public void emailHover()
	{
		System.out.println("Hover");
	}
	
	private void backButtonStep2() {
		try {
			loadVipStep1();
			System.out.println("Back button in Vip Step 2 is pressed");
			
		}catch(NullPointerException e)
		{
			e.printStackTrace();
		}
	}
	
	public void logoutButton()
	{
		LocalDate DOB = dob.getValue();
		String emailText = emailAdd.getText();
		if(emailText!=null && !emailText.isEmpty()&& DOB!=null)
		{
			VIPUser vipUser = new VIPUser(currentUser.getUsername(), currentUser.getPassword());
			vipUser.setEmail(emailText);
			vipUser.setDOB(DOB);
			vipUser.setisVIP(true);
			model.setCurrentUser(vipUser);
		
			//System.out.println("Current User class: " + model.getCurrentUser().getClass().getName());
			System.out.println("Updated currentUser to VIPUser: " + vipUser.getClass().getName());
			
			try {
				model.getUserDao().updateVIPfields(
						vipUser.getUsername(),
						vipUser.getEmail(),
						vipUser.getDOB(),
						vipUser.getisVIP());
				System.out.println("VIP User details updated successfully");
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
				LoginController loginController = new LoginController(new Stage(), model);
				loader.setController(loginController);
				Pane root = loader.load();
				Scene scene = new Scene(root, 500, 300);
				parentStage.setScene(scene);
				parentStage.show();	
				stage.close();
			}catch(IOException | SQLException e)
			{
				status.setText(e.getMessage());
				status.setTextFill(Color.RED);
			}
		}
		else
		{
			status.setText("Empty email or dob");
			status.setTextFill(Color.RED);
		}
	}
	public void VIPCredits() {
		CreditController credits = new CreditController(parentStage, model);
		
	}
//	
//	public void orderMeal() {
//		OrderController order = new OrderController(parentStage, model);
//	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 336, 254);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("VIP Confirmation");
		stage.show();
	}
}
