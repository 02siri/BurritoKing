module A2_Project {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	requires junit;
	
	opens controller to javafx.graphics, javafx.fxml;
	opens dao to javafx.graphics, javafx.fxml;
	opens main to javafx.graphics, javafx.fxml;
	opens model to javafx.graphics, javafx.fxml,javafx.base,junit;
	opens view to javafx.graphics, javafx.fxml;
}