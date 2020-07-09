package com.dxc.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dxc.dao.IUserDao;
import com.dxc.dao.UserDaoImpl;
import com.dxc.pojos.Customer;
import com.dxc.pojos.TransactionDetails;
import com.dxc.services.IUserService;
import com.dxc.services.UserServiceImpl;

@WebServlet("/user")
public class LoginUserServlet extends HttpServlet {
	IUserService userservice = new UserServiceImpl();
	IUserDao userdao = new UserDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(" inside control class");
		String message = null;
		String action = "";
		String temp = request.getParameter("btn");
		if (temp != null)
			action = request.getParameter("btn");
		HttpSession session = request.getSession();
		if (action.equals("deposit")) {
			int account_number = Integer.parseInt(request.getParameter("account_number"));
			double balance = Double.parseDouble(request.getParameter("balance"));
			if (balance <= 0) {
				String str = " You cannot deposit the amount into your account";
				session.setAttribute("str", str);
				response.sendRedirect("invalid_deposit.jsp");

			} else {

				String str = userservice.deposit(account_number, balance);
				session.setAttribute("str", str);
				response.sendRedirect("deposit_amount.jsp");
			}

		} else if (action.equals("withdraw")) {
			int account_number = Integer.parseInt(request.getParameter("account_number"));
			double balance = Double.parseDouble(request.getParameter("balance"));
			if (balance <= 0) {
				message = " InSufficient Amount You can't withdraw !!!!!!";
				session.setAttribute("message", message);
				response.sendRedirect("invalid_withdraw.jsp");
			} else {
				String str = userservice.withdraw(account_number, balance);
				session.setAttribute("str", str);
				response.sendRedirect("withdraw_amount.jsp");
			}

		} else if (action.equals("check_balance")) {
			int account_number = Integer.parseInt(request.getParameter("account_number"));
			List<Double> list = userservice.checkBalance(account_number);
			session.setAttribute("list", list);
			response.sendRedirect("check_balance.jsp");
		} else if (action.equals("change")) {

			String newpass = request.getParameter("newpass");
			String confirm_pass = request.getParameter("confirm");
			int account_number=(int)session.getAttribute("id");
			if (newpass.equals(confirm_pass)) {
				 String updatepassword = userservice.updateNewPassword(account_number, newpass);
				message = " password Successfully updated!!!";
			} else {
				message = " newpassword and confirm password not matched!!!";
			}
			session.setAttribute("message", message);
			response.sendRedirect("updated_password.jsp");
		}

		else if (action.equals("transfer")) {
			System.out.println(" transfering  money");
			String user1 = request.getParameter("account_number1");
			String user2 = request.getParameter("account_number2");
			double balance = Double.parseDouble(request.getParameter("amount"));
			String str = userservice.transferMoney(user1, user2, balance);
			session.setAttribute("str", str);
			response.sendRedirect("transfer_money.jsp");
		} 
		else
		{
			int account_number=(int)session.getAttribute("id");
		List<TransactionDetails> list = userservice.miniStatement(account_number);
		System.out.println(list.size() + "list size");
		request.getSession().setAttribute("ls", list);
		response.sendRedirect("mini_statement.jsp");
		}
	}
}