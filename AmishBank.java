import java.util.*;
import java.sql.*;
import DAO.DataBase;
import User.*;

/*
 * This application is for a small Amish community located in Pennsylvania that refuses to upgrade their old DOS 286 systems 
 * but they want a bank at their local 1981 ATM machine located at the center of their town.   These limitations has forced us 
 * to come up with an old fashioned "green screen" type user interface so that they can bank electronically without violating 
 * their religion.   
 */
public class AmishBank {
	
	public static void main(String[] arg) {
		final String intromessage = "Welcome to The Amish Bank of Pennsylvania.  We appreciate your deposits!";
		final String exitmessage = "Thank you for using the Amish Bank of Pennsylvania";
		final Scanner userinput = new Scanner(System.in);
		System.out.println(intromessage);
			try {
				/* This section will check if there is a manager user who can do everything.
				 * Assume there is one for now.  If it finds that there is no manager user 
				 * then it will go into a special script to create one and restart after that.
				 * 
				 * 
				 * 
				 * 
				 */
				
				DataBase.getResult(DataBase.buildbaseqry);
				
			}
			catch (SQLException S) {
				System.out.println(S);
			}
		
		boolean managerexist = true;
			
		System.out.print("Login: ");
		
		String username = userinput.nextLine();
		System.out.print("Password: ");
		String password = userinput.nextLine();
		try {
		user ewzer = user.createUser(username, password);
		boolean ewzerexist = (ewzer != null);
			if (ewzerexist) {
				ewzer.userMenu();
				
			}
		}
		catch (SQLException S) {
			System.out.println(S);//replace these with iej4.jar test info methods.
		}
		
	System.out.println(exitmessage);
	//end of program
	}
		
}
	
