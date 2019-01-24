

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class RestaurantService {

	private DatabaseConnectionService dbService = null;
	
	public RestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addResturant(String restName, String addr, String contact) {
//		try {
//			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.AddRestaurant(?, ?, ?) }");
//			cs.registerOutParameter(1, Types.INTEGER);
//			cs.setString(2, restName);
//			cs.setString(3, addr);
//			cs.setString(4, contact);
//			cs.execute();
//			int returnVal = cs.getInt(1);
//			switch (returnVal) {
//			case 1:
//				JOptionPane.showMessageDialog(null, "ERROR: Restaurant name cannot be null or empty.");
//				break;
//			case 2:
//				JOptionPane.showMessageDialog(null, "ERROR: Restaurant name already exists.");
//				break;
//			default:
//				// succeeded, do nothing
//				break;
//			}
//			return true;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return false;
	}
	

	public ArrayList<String> getRestaurants() {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> rests = new ArrayList<String>();
		try {
			stmt = dbService.getConnection().createStatement();
			stmt.execute("select * from Kingdom");
			rs = stmt.getResultSet();
			while(rs.next()){
				rests.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rests;
	}
}
