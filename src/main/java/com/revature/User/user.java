package com.revature.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import com.revature.DAO.DataBase;
import com.revature.AmishBank;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract public class user {
protected String name;
protected ArrayList<String> choices = new ArrayList<String>();

	user(String n) {
		name = n;
	}  
	@Test
	public static user createUser(final String name, final String password) throws SQLException {
		boolean haspassword = false;
	
		try {
			String P = DataBase.getPassword(name);
			//assertEquals(password, P);
			haspassword = password.equals(P);
		}
		catch (SQLException S) {	
			AmishBank.log.severe(S.toString());
			haspassword = false;
		}
		user loser = null;
			if (haspassword) {
				
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
				else {
					System.out.println(name + " is not active.");
				}
			}
			else {
				System.out.println("This is not the correct password!");
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
