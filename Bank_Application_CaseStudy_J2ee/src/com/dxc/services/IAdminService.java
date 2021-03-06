package com.dxc.services;

import java.util.List;

import com.dxc.pojos.Customer;

public interface IAdminService 
{
	public String authenticate(String id,String password);
	public void addCustomer(Customer c);
	public List<Customer> getCustomerDetails(int account_number);
	public String updateCustomerDetails(int account_number,String name,double  balance);
	public void deleteCustomer(int account_number);
	public List<Customer> showAllCustomers();
	public List<Double> balanceEnquiry(int account_number);

}
