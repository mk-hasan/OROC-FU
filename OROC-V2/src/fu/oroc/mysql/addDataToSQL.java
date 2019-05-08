package fu.oroc.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class addDataToSQL {
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	final private String host = "localhost";
	final private String user = "root";
	final private String passwd = "TuBerlin0007";
	final private String database = "oroc";

	public void connectToDB() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://" + host + "/"
					+ database + "?" + "user=" + user + "&password=" + passwd);

		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public void addNewResult(String obj_name, String material_type, String shape_type, int noOfobj, String resultlist) throws Exception{
		String query = "INSERT INTO `"+ database +"`.`result` (`id`,`obj_name`, `material_type`,`shape_type`,`noOfobj`,`resultlist`)" +
	"VALUES (NULL,?,?,?,?,?)";
		
		
	try {
		preparedStatement = connect.prepareStatement(query);
	
		
		preparedStatement.setString(1, obj_name);
		preparedStatement.setString(2, material_type);
		preparedStatement.setString(3, shape_type);
		preparedStatement.setInt(4, noOfobj);
		preparedStatement.setString(5, resultlist);
		preparedStatement.executeUpdate();
	}
	catch (Exception e) {
		// TODO: handle exception
		throw e;
	}
	}
	
	// You need to close the resultSet
		public void close() {
			try {
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (connect != null) {
					connect.close();
				}
			} catch (Exception e) {

			}
		}

}
