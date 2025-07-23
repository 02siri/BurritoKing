package dao;

import java.sql.SQLException;

public interface CreditsDao {
	void setup() throws SQLException;
	int getCredits(String username);
	void insertCredits(String username, int credits);
	void updateCredits(String usernmae, int newCredits);
}
