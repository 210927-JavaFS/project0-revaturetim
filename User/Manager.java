package User;

import java.sql.SQLException;
import java.util.*;

import DAO.DataBase;

public class Manager extends Employee {
	
	Manager(String n) {
		super(n);
		choices.add("1. Create Employee");
		choices.add("2. Remove Employee");
		choices.add("3. Activate Customer Account");
		choices.add("4. Deactivate Customer Account");
		choices.add("5. Show Balance Customer's Balance");
		choices.add("6. Deposit Money into Customer's Account");
		choices.add("7. Withdraw Money from Customer's Account");
		choices.add("Q  Exit Program");
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
			else if (userresponse.equals("Q") || userresponse.equals("q")) {
			break; //this terminates menu loop
			}
		input.reset();
		}
		
	}
	
	public void activateEmployee(boolean activate) {
		System.out.print("Which Employee? ");
		Scanner input = new Scanner(System.in);	
		String employee = input.nextLine();
		try {
			
				if (DataBase.userExist(employee)) {
					if (activate == false) {
						DataBase.setActive(employee, activate);
						System.out.println("You have activatd this employee's account!");
					}
					else {
						DataBase.setActive(employee, activate);
						System.out.println("You have deactivatd this employees account!");
					}
				}
				else {
					/*
					 * this is where we create a user or employee in this case
					 */
				}
		}catch (SQLException S) {
			
		}
		
		
	}
	
	
}
