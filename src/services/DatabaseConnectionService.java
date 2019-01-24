package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionService {

	// DO NOT EDIT THIS STRING, YOU WILL RECEIVE NO CREDIT FOR THIS TASK IF THIS
	// STRING IS EDITED
	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public DatabaseConnectionService(String serverName, String databaseName) {
		// DO NOT CHANGE THIS METHOD
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String user, String pass) {
		// TODO: Task 1
		// BUILD YOUR CONNECTION STRING HERE USING THE SAMPLE URL ABOVE
		String finalUrl = SampleURL.replace("${dbServer}", this.serverName);
		finalUrl = finalUrl.replace("${dbName}", this.databaseName);
		finalUrl = finalUrl.replace("${user}", user);
		finalUrl = finalUrl.replace("${pass}", pass);
		try {
			this.connection = DriverManager.getConnection(finalUrl);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void closeConnection() {
		// TODO: Task 1
		try {
			if (this.connection != null && !this.connection.isClosed()) {
				this.connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
