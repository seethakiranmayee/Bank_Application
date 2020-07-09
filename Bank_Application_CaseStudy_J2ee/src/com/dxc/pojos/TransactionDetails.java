package com.dxc.pojos;

import java.sql.Date;

public class TransactionDetails 
{
     private String account_number;
     private String account_type;
     private double amount;
     private Date time;
     
     public  TransactionDetails ()
     {
    	 
     }

	public TransactionDetails(String account_number, String account_type, double amount, Date time) {
		super();
		this.account_number = account_number;
		this.account_type = account_type;
		this.amount = amount;
		this.time = time;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_nunmber(String account_number) {
		this.account_number = account_number;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "TransactionDetails [account_nunmber=" + account_number + ", account_type=" + account_type + ", amount="
				+ amount + ", time=" + time + "]";
	}
     
}
