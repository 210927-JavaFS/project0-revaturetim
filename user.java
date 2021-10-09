package User;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import DAO.*;

abstract public class user {
protected String name;
protected ArrayList<String> choices = new ArrayList<String>();

	user(String n) {
		name = n;
	}
	
	public static user createUser(final String name, final String password) throws SQLException {
		String P = DataBase.getPassword(name);
		user loser = null;
			if (password.equals(P)) {
				if (DataBase.isActive(name)) {
					final String t = DataBase.getData(name, DataBase.type);
						switch (t) {
							
						case "M":
							loser = new Manager(name);
							break;
						case "E":
							loser = new Employee(name);
							break;
						case "C":
							loser = new Customer(name);
							break;
						default :
							loser = null;
							break;
						}
					
					
				}		
			}
		
		return loser;
	}
	
	public boolean getStatus() throws SQLException {
		
	return DataBase.isActive(name);	
	}
	
	abstract public void userMenu();
	abstract public void balance();
	abstract public void deposit();
	abstract public void withdraw();
	
	protected float howMuch() {
		Scanner input;
		
		do {
			System.out.print("How Much? ");
			input = new Scanner(System.in);
		} while (input.hasNextFloat() == false);
		return input.nextFloat();//this should work
	}
	

}
