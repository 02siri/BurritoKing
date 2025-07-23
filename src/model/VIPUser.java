package model;

import java.time.LocalDate;

//inheriting the properties of User class
public class VIPUser extends User{
	private int credits;
	
	public VIPUser(String username, String password) {
		super(username, password);
		this.setisVIP(true);
	}
	
	public int getCredits()
	{
		return credits;
	}
	
	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	public int collectCredits(double amountSpent) {
		int earnedCredits = (int)(amountSpent/1);
		this.credits += earnedCredits;
		return this.credits;
	}
	public boolean redeemCredits(int creditsToRedeem)
	{	if(creditsToRedeem<=credits)
		{
		this.credits -= creditsToRedeem;	
		return true;
		}
		return false;
	}
	
	public int totalToPay(double totalPrice)
	{
		int totalAmt = (int)(totalPrice - credits);
		return totalAmt;
	}
}
