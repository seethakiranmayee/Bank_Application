package com.dxc.services;

import java.sql.Date;
import java.util.List;

import com.dxc.dao.IUserDao;
import com.dxc.dao.UserDaoImpl;
import com.dxc.pojos.Customer;
import com.dxc.pojos.TransactionDetails;

public class UserServiceImpl implements IUserService
{
	IUserDao userdao=new UserDaoImpl();
	public boolean authenticate(int id, String password)
	{
		return userdao.authenticate(id, password);

	}
	public String deposit(int account_number,double balance)
	{
		return userdao.deposit(account_number,balance);
		
	}
	public String withdraw(int account_number, double balance) 
	{
		return userdao.withdraw(account_number, balance);
	}
	public List<Double> checkBalance(int account_number)
	{
		return userdao.checkBalance(account_number);
	}
	public String updateNewPassword(int userid,String newpass)
	{
		return userdao.updateNewPassword(userid,newpass);
	}
	public String transferMoney(String account_number1,String account_number2,double balance)
	{
       return userdao.transferMoney(account_number1, account_number2,balance);   
		
	}
	public List<TransactionDetails > miniStatement(int account_number)
	{
		return userdao.miniStatement(account_number);
	    
	}
}
