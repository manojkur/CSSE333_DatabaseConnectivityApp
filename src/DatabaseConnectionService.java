

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
		String url = SampleURL.replace("${dbServer}", serverName);
		url = url.replace("${dbName}", databaseName);
		url = url.replace("${user}", user);
		url = url.replace("${pass}", pass);
		System.out.println(url);
		try {
			connection = DriverManager.getConnection(url);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void closeConnection() {
		if (connection != null) {
			try {
				if (!connection.isClosed())
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
