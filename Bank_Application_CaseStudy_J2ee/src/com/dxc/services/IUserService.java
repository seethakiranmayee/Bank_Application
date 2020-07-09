package com.dxc.services;

import java.sql.Date;
import java.util.List;

import com.dxc.pojos.Customer;
import com.dxc.pojos.TransactionDetails;

public interface IUserService 
{
	public boolean authenticate(int id, String password); 
	public String  deposit(int account_number,double balance);
	public String withdraw(int account_number,double balance);
	public List<Double> checkBalance(int account_number);
	public String updateNewPassword(int userid,String newpass);
	public String transferMoney(String account_number1,String account_number2,double balance);
	public List<TransactionDetails> miniStatement(int account_number);
}
