package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class EditProfileController {
	private Model model;
	private Stage parentStage;
	private Stage stage;
	private User currentUser;
	private ProfileController pc;
	private String field;
	
	@FXML
	private Label changeLabel;
	@FXML
	private TextField newValText;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Label messageLabel;
	
	public EditProfileController(Stage parentStage, Model model,ProfileController profileController) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.pc = profileController;
	}
	@FXML
	public void initialize()
	{	
		saveButton.setOnAction(e->{
			try {
				saveChanges();
			} catch (SQLException e1) {
				System.out.println("Changes not saved");
				e1.printStackTrace();
			}
		});
		cancelButton.setOnAction(e->cancelChanges());
	}
	
	public void setLabelText(String newLabelText)
	{
		changeLabel.setText(newLabelText);
		changeLabel.setTextFill(Color.RED);
	}
	
	public void setField(String field)
	{
		this.field = field;
	}
	private void saveChanges() throws SQLException
	{
		String newValue = newValText.getText();
		if(field.equals("firstName") || (field.equals("lastName") || field.equals("password"))){
			if(model.getUserDao().checkField(field, newValue)) {
				messageLabel.setText(field + "is already taken. Choose a new " + field + ".");
				messageLabel.setTextFill(Color.RED);
			}
		}
		model.getUserDao().updateProfile(model.getCurrentUser().getUsername(), field, newValue);
		pc.updateProfileView();
		stage.close();
		
	}
	
	private void cancelChanges()
	{
		stage.close();
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 396, 254);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Edit Details");
		stage.show();

	}
}