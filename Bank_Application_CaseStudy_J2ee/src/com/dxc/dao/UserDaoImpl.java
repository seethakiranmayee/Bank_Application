package com.dxc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dxc.pojos.Customer;
import com.dxc.pojos.TransactionDetails;

public class UserDaoImpl implements IUserDao
{
	private static Connection con;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded....");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kiranmayee?autoReconnect=true&useSSL=false",
					"root", "Jashwin@11");
			System.out.println("connected to database.......");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean authenticate(int id, String password) 
	{
		int flag=0;
		
		try {
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from customer ");
			while (rs.next()) {
				if ( id==(rs.getInt(1)) && password.equals(rs.getString(4)))
				{ 
					flag=1;
					return true;
				}
				
			}
			if(flag==0)
			{
				return false;
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;

	}

	public String deposit(int account_number, double balance) {

		double amount = 0;
		try {
			PreparedStatement pstmt = con.prepareStatement("select * from customer where account_number=?");
			pstmt.setInt(1, account_number);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				amount = rs.getDouble(3);
				amount = amount + balance;

			}
			pstmt.close();
			PreparedStatement pstmt1 = con.prepareStatement("update customer set Balance=? where account_number=?");
			pstmt1.setDouble(1, amount);
			pstmt1.setInt(2, account_number);
			pstmt1.executeUpdate();
      
			PreparedStatement pstmt3=con.prepareStatement("insert into transaction values(?,?,?,?)");
			pstmt3.setString(1,(Integer.toString(account_number)));
			pstmt3.setString(2,"deposit");
			pstmt3.setDouble(3,balance);
			pstmt3.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
			pstmt3.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "amount deposited successfully!!!";

	}

	public String withdraw(int account_number, double requestedAmount) {
		double availableAmount = 0;
		String withdrawMessage="InSufficient Balance";
		try {
			System.out.println("withdraw amonut");
			PreparedStatement pstmt = con.prepareStatement("select * from customer where account_number=?");
			pstmt.setInt(1, account_number);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				availableAmount = rs.getDouble(3);
				if (requestedAmount >= availableAmount) {
					return withdrawMessage;
				}
				availableAmount = availableAmount - requestedAmount;
			}

			PreparedStatement pstmt1 = con.prepareStatement("update customer set balance=? where account_number=?");
			pstmt1.setDouble(1, availableAmount);
			pstmt1.setInt(2, account_number);
			pstmt1.executeUpdate();
			PreparedStatement pstmt3=con.prepareStatement("insert into transaction values(?,?,?,?)");
			pstmt3.setString(1,(Integer.toString(account_number)));
			pstmt3.setString(2,"Withdraw");
			pstmt3.setDouble(3,requestedAmount);
			pstmt3.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
			pstmt3.execute();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "amount is successfully withdraw!!!";
		

	}
	public List<Double> checkBalance(int account_number)
	{
		List<Double> list=new ArrayList<>();
		double balance=0;
		try {
			PreparedStatement pstmt=con.prepareStatement("select  balance from customer where account_number=?");
			pstmt.setInt(1, account_number);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				 balance=rs.getDouble(1);
				 list.add(balance);
				System.out.println("The requried balance for particular customer is obtained");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String updateNewPassword(int userid,String newpass)
	{
		try {
			PreparedStatement pstmt=con.prepareStatement("update customer set password=?  where Account_number=?");
			pstmt.setString(1, newpass);
			pstmt.setInt(2, userid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newpass;
		
	}
	public String transferMoney(String account_number1,String account_number2,double balance)
	{
		double amount=0,amount1=0;
		try {
			PreparedStatement pstmt=con.prepareStatement("select * from customer where Account_number=?");
			pstmt.setString(1, account_number1);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				amount=rs.getDouble(3);
				if(amount>balance)
				{
					amount=amount-balance;
					System.out.println(amount);
				}
				else {
					return "insufficiennt balance";
				}
			}
			PreparedStatement pstmt1=con.prepareStatement("update customer set balance=? where Account_number=?");
			pstmt1.setDouble(1,amount);
			pstmt1.setString(2, account_number1);
			pstmt1.executeUpdate();
			System.out.println("deducting the from user1");
			
			PreparedStatement pstmt2=con.prepareStatement("select * from customer where Account_number=?");
			pstmt2.setString(1, account_number2);
			ResultSet rs1=pstmt2.executeQuery();
			while(rs1.next())
			{
				amount1=rs1.getDouble(3);
				amount1=amount1+balance;
				System.out.println(amount1);
			}
			PreparedStatement pstmt3=con.prepareStatement("update customer set balance=? where Account_number=?");
			pstmt3.setDouble(1,amount1);
			pstmt3.setString(2, account_number2);
			pstmt3.executeUpdate();
			System.out.println("updating the user2 balance");
			PreparedStatement pstmt4=con.prepareStatement("insert into transaction values(?,?,?,?)");
			pstmt4.setString(1,account_number1);
			pstmt4.setString(2,"transfer");
			pstmt4.setDouble(3,balance);
			pstmt4.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
			pstmt4.execute();
		}catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return "Amount is transfered successfully!!!";
	}
		
		public List<TransactionDetails> miniStatement(int account_number)
		{
			List<TransactionDetails> list=new ArrayList<TransactionDetails>();
			try {
				PreparedStatement pstmt=con.prepareStatement("select * from transaction  where Account_number=? order by Time desc limit 5");
				pstmt.setInt(1, account_number);
				ResultSet rs=pstmt.executeQuery();
				while(rs.next())
				{
					
				  
					list.add(new TransactionDetails(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getDate(4)));
				}
				
			
						
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
	}