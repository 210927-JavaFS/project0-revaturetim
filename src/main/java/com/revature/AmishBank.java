package com.revature;
/*DONT CHANGE SETTINGS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.*;

import com.revature.DAO.DataBase;
import com.revature.User.user;

/*This is the postgres procedure stored in my data base and is used to log events in event table
CREATE OR REPLACE PROCEDURE register_event(n varchar(30))  LANGUAGE plpgsql
AS $$
BEGIN
	
INSERT INTO bank_events (person_name, person_time) VALUES (n, now());


END;
$$;

 */

/*
 * This application is for a small Amish community located in Pennsylvania that refuses to upgrade their old DOS 286 systems 
 * but they want a bank at their local 1981 ATM machine located at the center of their town.   These limitations has forced us 
 * to come up with an old fashioned "green screen" type user interface so that they can bank electronically without violating 
 * their religion.   
 */
public class AmishBank {
	public static Logger log = Logger.getAnonymousLogger();
	
	public static void main(String[] arg) {
		log.setLevel(Level.OFF);
		final String intromessage = "Welcome to The Amish Bank of Pennsylvania.  We appreciate your deposits!";
		final String exitmessage = "Thank you for using the Amish Bank of Pennsylvania";
		final Scanner userinput = new Scanner(System.in);
		System.out.println(intromessage);
			try {
				DataBase.setRow(DataBase.buildbaseqry);
			}
			catch (SQLException S) {
				log.severe(S.toString());
			}
			try {
				DataBase.setRow(DataBase.buildeventtable);	
			}
			catch (SQLException S) {
				log.severe(S.toString());
			}
			try {
				DataBase.setRow(DataBase.buildlogtable);
			}
			catch (SQLException S) {
				log.severe(S.toString());
			}
LOGIN:	while (true) {
			System.out.print("Login: ");
			
			String username = userinput.nextLine();
				if (username.equals("exit")) {
					break LOGIN;
				}
			System.out.print("Password: ");
			String password = userinput.nextLine();
			try {
			user ewzer = user.createUser(username, password);
			boolean ewzerexist = (ewzer != null);
				if (ewzerexist) {
					DataBase.loginout(username);
					ewzer.userMenu();	
					DataBase.loginout(username);
				}
				else {
					log.warning("unable to create user object");
				}
				
			}
			catch (SQLException S) {
				
				log.severe(S.toString());
			}
		}
		
	System.out.println(exitmessage);
	//end of program
	}
		
}
	
