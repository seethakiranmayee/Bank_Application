package com.dxc.services;

import java.util.List;

import com.dxc.dao.AdminDaoImpl;
import com.dxc.dao.IAdminDao;
import com.dxc.pojos.Customer;

public class AdminServiceImpl implements IAdminService 
{
	IAdminDao admindao=new AdminDaoImpl();
   
	@Override
	public String authenticate(String id, String password) 
	{
		return admindao.authenticate(id, password);
	}

	@Override
	public void addCustomer(Customer c) 
	{
		admindao.addCustomer(c);
		
	}
	public List<Customer> getCustomerDetails(int account_number)
	{
		return admindao.getCustomerDetails(account_number);
	}
	public String updateCustomerDetails(int account_number,String name,double  balance)
	{
		return admindao.updateCustomerDetails(account_number, name, balance);
	}
	public void deleteCustomer(int account_number)
	{
		admindao.deleteCustomer(account_number);
	}
	public List<Customer> showAllCustomers()
	{
		return admindao.showAllCustomers();
	}
	public List<Double> balanceEnquiry(int account_number)
	{
		 return admindao.balanceEnquiry(account_number);
	}

}
