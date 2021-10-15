package com.revature.User;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

import com.revature.DAO.DataBase;
import com.revature.AmishBank;


public class Employee extends user {
	
	
	Employee(String n) {
		super(n);
		choices.add("1. Activate Customer Account");
		choices.add("2. Deactivate Customer Account");
		choices.add("3. Show Customer's Balance");
		choices.add("4. Deposit Money into Customer's Account");
		choices.add("5. Withdraw Money from Customer's Account");
		choices.add("6. Logout");
		
	}

	
	
	public void userMenu() {
		Scanner input = new Scanner(System.in);	
			while (true) {
				
				for (String s : (Iterable<String>)choices) {
					System.out.println(s);
					
				}
				System.out.print("What is your decision?  ");
				String userresponse = input.next();
				if (userresponse.equals("1")) {
					activateAccount(true);
				}
				else if (userresponse.equals("2")) {
					activateAccount(false);		
				}
				else if (userresponse.equals("3")) {
					balance();
				}
				else if (userresponse.equals("4")) {
					deposit();
				}
				else if (userresponse.equals("5")) {
					withdraw();
				}
				else if (userresponse.equals("6") || userresponse.equals("q")) {
				break; //this terminates menu loop
				}
				else {
					System.out.println("That was not an available option.");
				}
			}
			
		}
	
	public void deposit() {
		String customer = whichCust();
		try {
			if (DataBase.userExist(customer)) {
				float a = howMuch();
				float b = DataBase.addMoney(customer, a);
				System.out.println("You have added this much money to this customer's account");
			}
			else {
				AmishBank.log.info(customer + " was not found");	
			}
		}catch (SQLException S) {
			AmishBank.log.severe(S.toString());
		}		
	}
	
	public void balance() {
		String customer = whichCust();
		try {
			if (DataBase.userExist(customer)) {
				float b = DataBase.getMoney(customer);
				System.out.println(customer + " has $" + b + " to spend!");
			}
			else {
				AmishBank.log.info(customer + " was not found");	
			}
		}catch (SQLException S) {
			AmishBank.log.severe(S.toString());
		}
	}
	  
	public void activateAccount(boolean activate) {
		System.out.print("Which Cusmtomer? ");
		Scanner input = new Scanner(System.in);	
		String customer = input.nextLine();
		try {
			if (DataBase.userExist(customer)) {
				DataBase.setActive(customer, activate);
					if (activate == true) {
						DataBase.setActive(customer, true);
						System.out.println("You have activatd " + customer + "'s account!");
					}
					else {
						DataBase.setActive(customer, false);
						System.out.println("You have de-activated " + customer + "'s account!");
					}
			}
			else {
				System.out.print("What is this customer's pin? ");
				String p = input.nextLine();
				System.out.println("What is their balance? ");
				float b = input.nextFloat();
				
				DataBase.createUser(customer, p, b, true, "C");
				
			}
		}catch (SQLException S) {
			AmishBank.log.severe(S.toString());
		}	
	}
	
	public void withdraw() {
		String customer = whichCust();
		try {
			if (DataBase.userExist(customer)) {
				float a = howMuch();
				float b = DataBase.minusMoney(customer, a);
				System.out.println("You have withdrawn this much money from this customer's account");
			}
			else {
				
				AmishBank.log.info(customer + " was not found");	
			}
		}catch (SQLException S) {
			AmishBank.log.severe(S.toString());
		}
	}
	
	private String whichCust() {
		System.out.print("Which Cusmtomer? ");
		Scanner input = new Scanner(System.in);	
		return input.nextLine();
	}
	
	
	
}
