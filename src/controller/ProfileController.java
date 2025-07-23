package controller;

import java.io.IOException;
import java.sql.SQLException;

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

public class ProfileController {
	private Model model;
	private Stage parentStage;
	private Stage stage;
	private User currentUser;
	
	@FXML
	private Label emailTitle;
	@FXML
	private Label dobTitle;
	@FXML
	private Label firstNameProfile;
	@FXML
	private Label lastNameProfile;
	@FXML
	private Label usernameProfile;
	@FXML
	private Label passwordProfile;
	@FXML
	private Label vipStatusProfile;
	@FXML
	private Label emailProfile;
	@FXML
	private Label dobProfile;
	@FXML
	private Label usernameLabelProfile;
	@FXML
	private Button changeFirstName;
	@FXML
	private Button changeLastName;
	@FXML
	private Button changePassword;
	@FXML
	private Label emailLabel;
	@FXML
	private Label dobLabel;
	@FXML
	private Button unsubscribe;
	@FXML
	private Label messageLabel;
	@FXML
	private Button backButton;
	@FXML
	private Button logoutButton;
	
	
	
	public ProfileController(Stage parentStage, Model model,User currentUser) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.currentUser = currentUser;
	}
	
	public void loadProfileView()
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Profile.fxml"));
			ProfileController profileController = new ProfileController(parentStage, model,currentUser);
			loader.setController(profileController);
			Pane root = loader.load();
			profileController.showStage(root);
			stage.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void updateProfileView() {
		retreiveProfileInfo();
	}
	@FXML
	public void initialize()
	{	//newLabel.setText("");
		retreiveProfileInfo();

		if(currentUser!=null) {
			setUser(currentUser);
		}
		
		backButton.setOnAction(e->back());
		logoutButton.setOnAction(e->logout());
		
		changeFirstName.setOnAction(e->changeProfile("Enter New First Name:","firstName"));
		changeLastName.setOnAction(e->changeProfile("Enter New Last Name:","lastName"));
		changePassword.setOnAction(e->changeProfile("Enter New Password","password"));
		
		unsubscribe.setOnAction(e->{
			try {
				unsubscribeVIP();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		if(!model.getCurrentUser().getisVIP()) {
			unsubscribe.setVisible(false);
			emailTitle.setVisible(false);
			dobTitle.setVisible(false);
			emailProfile.setVisible(false);
			dobProfile.setVisible(false);
			emailLabel.setVisible(false);
			dobLabel.setVisible(false);
		}
	}
	

	private void changeProfile(String labelText, String field) {
			try{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditProfile.fxml"));
				EditProfileController editController = new EditProfileController(this.stage,model,this);
				loader.setController(editController);
				Pane root = loader.load();
				
				editController.setLabelText(labelText);
				editController.setField(field);
				
				editController.showStage(root);
				
				messageLabel.setText("Please logout and login to see the changes.");

				}catch(IOException e) {
				e.printStackTrace();
				System.out.println("Error loading EditProfile.fxml");
			}
		System.out.print("Change profile button clicked");
		
	}

	private void unsubscribeVIP() throws SQLException {
		model.getUserDao().unsubscribeFromVIP(model.getCurrentUser().getUsername());
		System.out.println("Removed from VIP Successfully");
		retreiveProfileInfo();
	}
	

	public void setUser(User user) {
		currentUser = user;
	}

	public void retreiveProfileInfo() {
		usernameLabelProfile.setText("..." + model.getCurrentUser().getUsername());
		firstNameProfile.setText(model.getCurrentUser().getFirstName());
		lastNameProfile.setText(model.getCurrentUser().getLastName());
		usernameProfile.setText(model.getCurrentUser().getUsername());
		passwordProfile.setText(model.getCurrentUser().getPassword());
		
		if(model.getCurrentUser().getisVIP())
		{
			vipStatusProfile.setText("VIP User");
			emailProfile.setText(model.getCurrentUser().getEmail());
			dobProfile.setText(model.getCurrentUser().getDOB().toString());
		}
		else
		{
			vipStatusProfile.setText("Not A VIP User");
		}
		
	}
	
	private void back() {	
		this.parentStage.show();
		this.stage.close();
		
	}
	
	private void logout() {
		
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
			LoginController loginController = new LoginController(new Stage(), model);
			loader.setController(loginController);
			Pane root = loader.load();
			Scene scene = new Scene(root, 500, 300);
			parentStage.setScene(scene);
			parentStage.show();	
			this.stage.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 587, 450);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Profile");
		stage.show();
	}
}
