package com.dxc.pojos;

public class Customer 
{
    private int account_number;
    private String name;
    private double balance;
    private String password;
    public Customer()
    {
    	
    }
	public Customer(int account_number, String name, double balance,String password) {
		super();
		this.account_number = account_number;
		this.name = name;
		this.balance = balance;
		this.password=password;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAccount_Number() {
		return account_number;
	}
	public void setAccount_Number(int account_number) {
		this.account_number = account_number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
    
}
