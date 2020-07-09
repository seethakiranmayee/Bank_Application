package com.dxc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.dxc.pojos.Customer;

public class AdminDaoImpl implements IAdminDao 
{
	private static Connection con;
	static
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded....");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kiranmayee?autoReconnect=true&useSSL=false",
					"root", "Jashwin@11");
			System.out.println("connected to database.......");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public String authenticate(String id,String password)
    {
		try {
			PreparedStatement pstmt=con.prepareStatement("select * from bank_admin");
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				if(id.equals(rs.getString(1)) && password.equals(rs.getString(2)))
		    	{
		    		  return " Admin Login Successfull!!!!";
		    	}
		    	else
		    	{
		    		return " Admin Login is failed!!!!";
		    	}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    	
    }
	@Override
	public void addCustomer(Customer c) 
	{
		 try {
				PreparedStatement pstmt=con.prepareStatement("insert into customer values(?,?,?,?)");
				pstmt.setInt(1,c.getAccount_Number());
				pstmt.setString(2, c.getName());
				pstmt.setDouble(3,c.getBalance());
				pstmt.setString(4, c.getPassword());
				pstmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("The customer details are added successfull!!!");
		
	}
	public List<Customer> getCustomerDetails(int account_number) {

		List<Customer> list = new ArrayList<>();
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("select * from customer where account_number=?");
			pstmt.setInt(1, account_number);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Customer c = new Customer(rs.getInt(1), rs.getString(2), rs.getDouble(3),rs.getString(4));
				list.add(c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public String updateCustomerDetails(int account_number,String name,double  balance) 
	{
		try {
			PreparedStatement pstmt=con.prepareStatement("update customer set name=?,balance=? where account_number=?");
			pstmt.setString(1, name);
			pstmt.setDouble(2, balance);
			pstmt.setInt(3, account_number);
			pstmt.execute();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "customer details are successfully updated!!!";
	}
		public void deleteCustomer(int account_number)
		{
			try
			{
				PreparedStatement pstmt=con.prepareStatement("delete  from customer where account_number=?");
			pstmt.setInt(1, account_number);
			pstmt.execute();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		public List<Customer> showAllCustomers()
		{
			List<Customer> list=new ArrayList<>();
			try {
				Statement stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from customer");
			while(rs.next())
			{
				Customer s=new Customer(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getString(4));
				list.add(s);
				
			}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
		public List<Double> balanceEnquiry(int account_number)
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
	}
	
