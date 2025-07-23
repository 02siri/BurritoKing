package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {
	private String cardNo;
	private LocalDate expiryDate;
	private int cvv;
	

	public Payment(String cardNo, LocalDate expiryDate, int cvv)
	{
		this.cardNo = cardNo;
		this.expiryDate = expiryDate;
		this.cvv = cvv;
	}
	
	public boolean checkCardNo()
	{	if(cardNo!=null &&  cardNo.length()==16)
	  	{
			return true;
	  	}
		else
		{
			return false;
		}
	}
	
	public boolean checkExpiryDate()
	{
		if(expiryDate!=null && expiryDate.isAfter(LocalDate.now())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkCvv()
	{
		if(String.valueOf(cvv).length()==3) {
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean checkOrderDateTime(LocalDateTime orderDateTime) {
		if(orderDateTime!=null && !orderDateTime.isBefore(LocalDateTime.now())) {
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
}


