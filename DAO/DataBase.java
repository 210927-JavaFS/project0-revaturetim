package DAO;

import java.sql.*;

public class DataBase {
	final private static String url = "jdbc:postgresql://timbosley.cjjjkidzgiux.us-east-2.rds.amazonaws.com:5432/amishbank";
	final private static String user = "postgres";
	final private static String password = "password";
	final private static String table = "bank";
	final private static String id = "bank_id_num";
	final private static String name = "bank_user";
	final private static String pin = "bank_user_password";
	final private static String balance = "bank_balance";
	final private static String status = "bank_status";
	final public static String type = "bank_user_type";
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException C) {
			System.out.println(C);		
		}
		
	}
	
	final public static String buildbaseqry = "CREATE TABLE IF NOT EXISTS " + table + "(" 
			+ id + " serial," 
			+ name + " VARCHAR(30) PRIMARY KEY NOT NULL, "
			+ pin + " VARCHAR(8) NOT NULL,"
			+ balance + " DEC(9, 2) CHECK (" + balance + ">= 0),"
			+ status + " BOOLEAN,"
			+ type + " varchar(1) CHECK (" + type + "= 'M' OR " + type +" = 'E' OR " + type + " = 'C')"
			+ ");"
			+ "--This must have at least one user to start out with"
			+ "INSERT INTO " + table + "(" 
			+ name + ", " 
			+ pin + ", "
			+ balance + ", "
			+ status +", "
			+ type +") VALUES ('Tim', '474140', .1, TRUE, 'M');";
	
	public static ResultSet getResult(final String qry) throws SQLException {	
		Connection con = DriverManager.getConnection(url, user, password);
		PreparedStatement stmt = con.prepareStatement(qry);//this does not prevent SQL injection.
		return stmt.executeQuery();		
	}
	
	public static String getData(final String key, final String col) throws SQLException {
		String sql = "SELECT " + col + " FROM " + table + "WHERE " + name + "=" + key;
		return getResult(sql).getString(col);
				
	}
	
	public static ResultSet setData(final String key, final String col, final String newvalue) throws SQLException {
		String sql = "UPDATE " + table + "SET " + col + "=" + newvalue + "WHERE " + name + "=" + key;
		ResultSet result = getResult(sql);
		return result;				
	}
	
	public static float getMoney(final String key) throws SQLException {
		String sql = "SELECT " + balance + " FROM " + table + "WHERE " + name + "=" + key;
		ResultSet result = getResult(sql);
		return result.getFloat(balance);
	}
	
	public static float addMoney(final String key, float amount) throws SQLException {
		float newamount = getMoney(key) + amount;
		setData(key, balance, String.valueOf(newamount));			
		return newamount;
	
	}
	
	public static float minusMoney(final String key, float amount) throws SQLException {
		return addMoney(key, -1f * amount);
	}
	
	
	public static boolean isActive(final String key)  throws SQLException {
		String sql = "SELECT " + status + " FROM " + table + "WHERE " + name + "=" + key;
		ResultSet result = getResult(sql);
		return result.getBoolean(status);
	}
	
	public static boolean setActive(final String key, final boolean act)  throws SQLException {
			byte b = 0;//false by default
				if (act == true) {
					b = 1;
				}
				else {
					b = 0;
				}
			
			ResultSet result = setData(key, status, String.valueOf(b));
			return result.getBoolean(status);
	}
	
	public static String getPassword(final String key)  throws SQLException {
			String sql = "SELECT " + pin + " FROM " + table + "WHERE " + name + "=" + key;
			ResultSet result = getResult(sql);
			String drowssap = result.getString(pin);
			StringBuffer buf = new StringBuffer(drowssap);
			StringBuffer password = buf.reverse();//the encryption is just reversing the order of it in the database
			return password.toString();
	}
	
	public static String setPassword(final String key, final String password) {
			try {
			StringBuffer buf = new StringBuffer(password);
			StringBuffer drowssap = buf.reverse();//the encryption is just reversing the order of it in the database	
			ResultSet result = setData(key, pin, drowssap.toString());
			return result.getString(pin);
			}
			catch (SQLException S) {
				System.out.println(S);
				return "XXXXXXXX";
				
			}	
		
	}
	
	public static boolean userExist(final String key) throws SQLException {
		
		return true;
	}
	
	public static void addUser() {
		
		
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
