package services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SodaService {

	private DatabaseConnectionService dbService = null;
	
	public SodaService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean addSoda(String sodaName, String manf) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.AddSoda(?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, sodaName);
			cs.setString(3, manf);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "ERROR: Soda name cannot be null or empty.");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "ERROR: Rest name already exists.");
				break;
			default:
				// succeeded, do nothing
				break;
			}
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<String> getSodas() {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> sodas = new ArrayList<String>();
		try {
			stmt = dbService.getConnection().createStatement();
			stmt.execute("select distinct name from Soda");
			rs = stmt.getResultSet();
			while(rs.next()){
				sodas.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sodas;
	}
}
