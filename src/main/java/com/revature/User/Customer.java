package com.revature.User;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

import com.revature.DAO.DataBase;
import com.revature.AmishBank;


public class Customer extends user {
	
	Customer(String n) {
		super(n);
		choices.add("1. Show Balance");
		choices.add("2. Deposit Money");
		choices.add("3. Withdraw Money");
		choices.add("4. Logout");
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
				balance();
				}
				else if (userresponse.equals("2")) {
				deposit();
				}
				else if (userresponse.equals("3")) {
				withdraw();
				}
				/*else if (userresponse.equals("4")) {
				
				}
				else if (userresponse.equals("5")) {
				
				}else if (userresponse.equals("6")) {
				
				}*/
				else if (userresponse.equals("4") || userresponse.equals("q")) {
				break; //this terminates menu loop
				}
				else {
					System.out.println("That was not an available option.");
				}
			}
			
		}

	@Override
	public void balance() {
		try {
			if (true/*DataBase.userExist(name)*/) {
				float b = DataBase.getMoney(name);
				System.out.println("You have $" + String.valueOf(b) + " in your account.");
			}
			else {
				AmishBank.log.info(name + " was not found");	
			}
		}catch (SQLException S) {
			
			AmishBank.log.severe(S.toString());
		}
		
	}

	@Override
	public void deposit() {
		try {
			if (/*DataBase.userExist(name)*/true) {
				float a = howMuch();
				float b = DataBase.addMoney(name, a);
				System.out.println("You have added $" + String.valueOf(b) + " to your account");
			}
			else {
				AmishBank.log.info(name + " was not found");	
			}
		}catch (SQLException S) {
			AmishBank.log.severe(S.toString());
		}
		
	}

	@Override
	public void withdraw() {
		try {
			if (/*DataBase.userExist(name)*/true) {
				float a = howMuch();
				float b = DataBase.minusMoney(name, a);
				System.out.println("You have withdrawn $" + String.valueOf(b) + " from to your account");
			}
			else {
				AmishBank.log.info(name + " was not found");	
			}
		}catch (SQLException S) {		
			AmishBank.log.severe(S.toString());
		}
		
	}
}
