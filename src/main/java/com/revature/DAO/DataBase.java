package com.revature.DAO;

import java.sql.*;
import java.util.logging.Logger;

import com.revature.AmishBank;

public class DataBase {
	final private static String url = "jdbc:postgresql://timbosley.cjjjkidzgiux.us-east-2.rds.amazonaws.com:5432/amishbank";
	final private static String user = "postgres";
	final private static String password = "password";
	public final static String table = "bank";
	final private static String id = "bank_id_num";
	public final static String name = "bank_user";
	final private static String pin = "bank_user_password";
	public final static String balance = "bank_balance";
	public final static String status = "bank_status";
	final public static String type = "bank_user_type";
	final public static String event = "register_event";
	final public static String login = "register_login";
	final public static String event_table = "bank_events";
	final public static String login_table = "bank_login";
	
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException C) {
			
			AmishBank.log.severe(C.toString());	
		}
		
	}
	
	final public static String buildbaseqry = "CREATE TABLE IF NOT EXISTS " + table + " (" 
			+ id + " serial, " 
			+ name + " VARCHAR(30) PRIMARY KEY NOT NULL, "
			+ pin + " VARCHAR(8) NOT NULL, "
			+ balance + " DEC(9, 2) CHECK (" + balance + ">= 0), "
			+ status + " BOOLEAN, "
			+ type + " varchar(1) CHECK (" + type + "= 'M' OR " + type +" = 'E' OR " + type + " = 'C')"
			+ "); "
			//+ "--This must have at least one user to start out with\n"
			+ "INSERT INTO " + table + " (" 
			+ name + ", " 
			+ pin + ", "
			+ balance + ", "
			+ status +", "
			+ type +") VALUES ('Tim', '474140', .1, TRUE, 'M');";
	final public static String buildeventtable = "CREATE TABLE IF NOT EXISTS "
			+ event_table + " (person_name varchar(30) NOT NULL, person_time varchar(30));\r\n";
	
	final public static String buildlogtable = "CREATE TABLE IF NOT EXISTS "
			+ login_table + " (person_name varchar(30) NOT NULL, person_time varchar(30));\r\n";
	
	private static boolean register(final String n, final String s) throws SQLException {
		final String sql = "CALL " + s + " ('" + n + "');";
		return setRow(sql);
	}
	
	public static ResultSet getResult(final String qry) throws SQLException {	
		Connection con = DriverManager.getConnection(url, user, password);
		PreparedStatement stmt = con.prepareStatement(qry);//this does not prevent SQL injection.
		ResultSet result = stmt.executeQuery();
		result.next();//this has to increment the results
		return result;		
	}
	
	public static boolean setRow(final String qry) throws SQLException {	
		Connection con = DriverManager.getConnection(url, user, password);
		PreparedStatement stmt = con.prepareStatement(qry);//this does not prevent SQL injection.
		int rownumber = stmt.executeUpdate();
			if (rownumber == 0) {
				return false;
			}
			else {
				return true;
			}
	}
	
	
	
	public static String getData(final String key, final String col) throws SQLException {
		final String sql = "SELECT " + col + " FROM " + table + " WHERE " + name + "=" + "'" + key + "';";
		final ResultSet result = getResult(sql);
		return result.getString(col);
				
	}
	
	public static float getMoney(final String key) throws SQLException {
		String sql = "SELECT " + balance + " FROM " + table + " WHERE " + name + "=" + "'" + key + "';";
		ResultSet result = getResult(sql);
		return result.getFloat(balance);
	}
	
	public static float addMoney(final String key, float amount) throws SQLException {
		float newamount = getMoney(key) + amount;
		updateUser(key, balance, String.valueOf(newamount));			
		return newamount;
	
	}
	
	public static float minusMoney(final String key, float amount) throws SQLException {
		return addMoney(key, -1f * amount);
	}
	
	
	public static boolean isActive(final String key)  throws SQLException {
		String sql = "SELECT " + status + " FROM " + table + " WHERE " + name + "=" + "'" + key + "'";
		ResultSet result = getResult(sql);	
		return result.getBoolean(status);
	}
	
	public static boolean setActive(final String key, final boolean act)  throws SQLException {		
			return updateUser(key, status, String.valueOf(act));
	}
	
	public static String getPassword(final String key)  throws SQLException {
			String sql = "SELECT " + pin + " FROM " + table + " WHERE " + table + "." + name + "=" + "'" + key + "'";
			ResultSet result = getResult(sql);		
			String password = result.getString(pin);	
			return encrypt(password);
	}
	
	public static boolean setPassword(final String key, final String password) throws SQLException {
	return updateUser(key, pin, encrypt(password));
	}
	
	private static String encrypt(String password) {
		StringBuffer buf = new StringBuffer(password);
		StringBuffer drowssap = buf.reverse();//the encryption is just reversing the order of it in the database	
		return drowssap.toString();
	}
	
	public static boolean updateUser(final String key, final String col, final String newvalue) throws SQLException {
		String sql = "UPDATE " + table + " SET " + col + "=" + newvalue + " WHERE " + name + "='" + key + "';";
		register(key, DataBase.event);
		return setRow(sql);	
	}
	
	public static boolean userExist(final String key) throws SQLException {
		String sql = "SELECT count(" + name + ") FROM " + table + " WHERE " + name + "='" + key + "';";
		try {
		ResultSet result = getResult(sql);
		
			int a = result.getInt(1);
			//System.out.println(a);
			if (a == 0) return false;
			else return true;
		}
		catch (SQLException S) {
			return false;
		}
	}
	
	public static ResultSet createUser(final String n, final String p, final float b, final boolean s, final String t) throws SQLException {
		final String sql = "INSERT INTO " + table + "(" + name + ", " + pin + ", " + balance + ", " + status + ", " + type + ") VALUES (" 
		+ "'" + n + "'" + ", "
		+ "'" + encrypt(p) + "', "
		+ String.valueOf(b) + ", "
		+ String.valueOf(s) + ", "
		+ "'" + t + "'" + ");";
		
		//System.out.println(sql);
		register(n, DataBase.event);
		setRow(sql);
		return null;	
		
	}
	
	public static boolean removeUser(final String n) throws SQLException {
		final String sql = "DELETE FROM " + table + " WHERE " + name + "='" + n + "';";
		register(n, DataBase.event);
		return setRow(sql);
	}
	
	private static String sqlBool(final boolean b) {
		byte n = 0;
		if (b) {
			n = 1;	
		}  
		else {
			n = 0;
		}
		return String.valueOf(b);		
	}
	
	public static void loginout(final String key) throws SQLException {
		
		register(key, DataBase.login);
		
	}
	
	public static Connection getConnection() throws SQLException {
		// For many frameworks using JDBC or operating with JDBC it is necessary to
		// "register" the driver
		// you are using to make the framework aware of it.
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		/*String url = "jdbc:postgresql://javafs-210927.cvtq9j4axrge.us-east-1.rds.amazonaws.com:5432/demos";
		String user = "postgres"; //It is possible to use env variables to hide this information
		String password = "password"; //you would access them with System.getenv("var-name");
		*/
		return DriverManager.getConnection(url, user, password);
	}

}
