package net.tutorial.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBService {

	private final String HOST = "us-cdbr-iron-east-04.cleardb.net";
	private final String PORT = "3306";
	private final String DB_NAME = "ad_a26915a035fc832";
	private final String DB_USERNAME = "b035db40527917";
	private final String DB_PASSWORD = "61e95ee1";
	public static final int INSERT_RECORD = 1;
	public static final int UPDATE_RECORD = 2;

	private static DBService instance = new DBService();
	Connection dbConnection = null;
	private PreparedStatement ps = null;

	private DBService() {
		createTable();
	}

	public static DBService getInstance() {
		return instance;
	}

	public ArrayList<Map<String, Object>> allRecords() {

		this.dbConnection = getConnection();

		ArrayList<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
		Map<String, Object> record = null;
		String sSQL = "SELECT _id, name, email, mobile " + "FROM `contacts`";

		ResultSet rs = null;

		try {
			ps = this.dbConnection.prepareStatement(sSQL);
			rs = ps.executeQuery(sSQL);

			while (rs.next()) {
				record = new HashMap<String, Object>();
				record.put("_id", rs.getInt("_id"));
				record.put("name", rs.getString("name"));
				record.put("email", rs.getString("email"));
				record.put("mobile", rs.getString("mobile"));
				records.add(record);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			cleanUp();
		}

		return records;
	}

	private void cleanUp() {
		try {
			if (ps != null) {
				ps.close();
			}
			if (this.dbConnection != null) {
				this.dbConnection.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private Connection getConnection() {
		Connection dbConnection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found.");
			System.out.println(e.getMessage());
			return null;
		}

		try {
			dbConnection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME, DB_USERNAME,
					DB_PASSWORD);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}

		return dbConnection;
	}

	private void createTable() {
		this.dbConnection = getConnection();

		String createTableSQL = "CREATE TABLE IF NOT EXISTS `contacts` (" + "`_id` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`name` varchar(45) DEFAULT NULL," + "`email` varchar(45) DEFAULT NULL,"
				+ "`mobile` varchar(45) DEFAULT NULL," + "PRIMARY KEY (`_id`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

		try {
			ps = this.dbConnection.prepareStatement(createTableSQL);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}
	}

	public void deleteRecord(int id) {
		this.dbConnection = getConnection();

		try {
			String sSQL = "DELETE FROM `contacts` WHERE _id=?";
			ps = this.dbConnection.prepareStatement(sSQL);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			cleanUp();
		}
	}

	public Map<String, Object> findRecord(int id) {
		this.dbConnection = getConnection();
		
		Map<String, Object> record = new HashMap<String, Object>();
		ResultSet rs = null;

		try {
			String sSQL = "SELECT * FROM `contacts` WHERE _id=?";
			ps = this.dbConnection.prepareStatement(sSQL);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			System.out.println("pass 1");
			if (rs.next()) {
				System.out.println("pass 2");
				record.put("_id", rs.getInt("_id"));
				record.put("name", rs.getString("name"));
				record.put("email", rs.getString("email"));
				record.put("mobile", rs.getString("mobile"));
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			cleanUp();
		}
		System.out.println("pass 3");
		return record;
	}

	public void updateRecord(int transaction, Map<String, Object> record) {
		this.dbConnection = getConnection();

		String sSQL = null;

		if (transaction == UPDATE_RECORD) {
			sSQL = "UPDATE `contacts` " + "SET name = ? , email = ? , mobile = ? " + "WHERE _id = ?";
		} else {
			sSQL = "INSERT INTO `contacts`" + "(`name`, `email`, `mobile`) VALUES" + "(?,?,?)";
		}

		try {
			ps = this.dbConnection.prepareStatement(sSQL);
			ps.setString(1, (String) record.get("name"));
			ps.setString(2, (String) record.get("email"));
			ps.setString(3, (String) record.get("mobile"));
			if (transaction == UPDATE_RECORD) {
				ps.setInt(4, (int) record.get("_id"));
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			cleanUp();
		}
	}

}
