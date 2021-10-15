package com.revature.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

import com.revature.DAO.DataBase;
import com.revature.AmishBank;

public class Manager extends Employee {

	Manager(String n) {
		super(n);
		choices.clear();
		choices.add("1. Create Employee");
		choices.add("2. Remove Employee or Customer");
		choices.add("3. Activate Customer Account");
		choices.add("4. Deactivate Customer Account");
		choices.add("5. Show Customer's Balance");
		choices.add("6. Deposit Money into Customer's Account");
		choices.add("7. Withdraw Money from Customer's Account");
		choices.add("8. See All Users!");
		choices.add("9. Logout");
	}

	public void userMenu() {
	Scanner input = new Scanner(System.in);
	
	
		while (true) {
			
			for (String s : (Iterable<String>)choices) {
				System.out.println(s);
				
			}
			System.out.print("What is your decision?  ");
			String userresponse = input.next();
			if (userresponse.length() > 1) {
				continue;
			}
			if (userresponse.equals("1")) {
				activateEmployee(true);
			}
			else if (userresponse.equals("2")) {
				activateEmployee(false);
			}
			else if (userresponse.equals("3")) {
				activateAccount(true);
			}
			else if (userresponse.equals("4")) {
				activateAccount(false);
			}
			else if (userresponse.equals("5")) {
				balance();
			}
			else if (userresponse.equals("6")) {
				deposit();
			}
			else if (userresponse.equals("7")) {
				withdraw();
			}
			else if (userresponse.equals("8")) {
				seeAll();
			}
			else if (userresponse.equals("9") || userresponse.equals("q")) {
				break;
			}
			else {
			System.out.println("That was not an available option");
			}
		input.reset();
		}
		
	}
	
	public void activateEmployee(boolean create) {
		System.out.print("Which Employee? ");
		Scanner input = new Scanner(System.in);	
		String employee = input.nextLine();
		try {
			
				if (create) {
					if (DataBase.userExist(employee)) {
					System.out.println(employee + " already exist.");	
					}
					else {
					System.out.print("What is the pin for this employee? "); 
					String p = input.nextLine();
					DataBase.createUser(employee, p, 0.0f, true, "E");	
					}
				}
				else {
					if (DataBase.userExist(employee)) {
						DataBase.removeUser(employee);
						System.out.println("You have removed " + employee + " from all bank records.");
					}
					else {
						System.out.println(employee + " is not in the database.");	
					}
					
				}
		}catch (SQLException S) {
			AmishBank.log.severe(S.toString());
		}
		
		
	}
	
	//This should be in DataBase but I don't have enough time to work on this.
	public void seeAll() {
		final String qry = "SELECT " + DataBase.name + ", " + DataBase.balance + ", " + DataBase.status + " FROM " + DataBase.table;
		try {
			ResultSet result = DataBase.getResult(qry);
			do {
				System.out.print(result.getString(DataBase.name) + " : ");
				System.out.print(result.getString(DataBase.balance)+ " : " );
				System.out.println(result.getString(DataBase.status));
			} while (result.next() == true);
		}
		catch (SQLException S) {
			AmishBank.log.severe(S.toString());
		}
	}
	
	
}
