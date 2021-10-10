package User;
import java.util.*;
import java.sql.*;
import DAO.DataBase;

public class Employee extends user {
	
	
	Employee(String n) {
		super(n);
		choices.add("1. Activate Customer Account");
		choices.add("2. Deactivate Customer Account");
		choices.add("3. Show Customer's Balance");
		choices.add("4. Deposit Money into Customer's Account");
		choices.add("5. Withdraw Money from Customer's Account");
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
				else if (userresponse.equals("Q") || userresponse.equals("q")) {
				break; //this terminates menu loop
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
				
			}
		}catch (SQLException S) {
			
		}
		
		
	}
	
	public void balance() {
		String customer = whichCust();
		try {
			if (DataBase.userExist(customer)) {
				float b = DataBase.getMoney(customer);
				System.out.println(b);
			}
			else {
				
			}
		}catch (SQLException S) {
			
		}
	}
	
	public void activateAccount(boolean activate) {
		String customer = whichCust();
		try {
			if (DataBase.userExist(customer)) {
				DataBase.setActive(customer, activate);
					if (activate == true) {
						System.out.println("You have activatd this customers account!");
					}
					else {
						System.out.println("You have deactivatd this customers account!");
					}
			}
			else {
				/*
				 * create new customer here
				 */
				
			}
		}catch (SQLException S) {
			
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
				
			}
		}catch (SQLException S) {
			
		}
	}
	
	private String whichCust() {
		System.out.print("Which Cusmtomer? ");
		Scanner input = new Scanner(System.in);	
		return input.nextLine();
	}
	
	
}
