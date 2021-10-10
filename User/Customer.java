package User;

import java.sql.SQLException;
import java.util.*;

import DAO.DataBase;

public class Customer extends user {
	
	Customer(String n) {
		super(n);
		choices.add("1. Show Balance");
		choices.add("2. Deposit Money");
		choices.add("3. Withdraw Money");
		choices.add("Q Exit Program");
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
				else if (userresponse.equals("Q") || userresponse.equals("q")) {
				break; //this terminates menu loop
				}
			}
			
		}

	@Override
	public void balance() {
		try {
			if (DataBase.userExist(name)) {
				float b = DataBase.getMoney(name);
				System.out.println("You have $" + String.valueOf(b) + " in your account.");
			}
			else {
				
			}
		}catch (SQLException S) {
			
		}
		
	}

	@Override
	public void deposit() {
		try {
			if (DataBase.userExist(name)) {
				float a = howMuch();
				float b = DataBase.addMoney(name, a);
				System.out.println("You have added $" + String.valueOf(b) + " to your account");
			}
			else {
				
			}
		}catch (SQLException S) {
			
		}
		
	}

	@Override
	public void withdraw() {
		try {
			if (DataBase.userExist(name)) {
				float a = howMuch();
				float b = DataBase.minusMoney(name, a);
				System.out.println("You have withdrawn $" + String.valueOf(b) + " from to your account");
			}
			else {
				
			}
		}catch (SQLException S) {
			
		}
		
	}
}
