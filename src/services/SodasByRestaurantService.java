package services;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.text.html.parser.ParserDelegator;

import sodabase.ui.SodaByRestaurant;

public class SodasByRestaurantService {

	private DatabaseConnectionService dbService = null;

	public SodasByRestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addSodaByRestaurant(String rest, String soda, double price) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.AddSells(?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, soda);
			cs.setString(3, rest);
			cs.setDouble(4, price);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "ERROR: Soda name cannot be null or empty.");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "ERROR: Rest name cannot be null or empty.");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "ERROR: Price cannot be null or empty.");
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "ERROR: Given soda does not exist.");
				break;
			case 5:
				JOptionPane.showMessageDialog(null, "ERROR: Given restaurant name does not exist.");
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

	public ArrayList<SodaByRestaurant> getSodasByRestaurants(String rest, String soda, String price,
			boolean useGreaterThanEqual) {
		try {
			Double newPrice = null;
			int counter = 1;
			// make sure price exists
			if(price != null && !price.isEmpty()){
				 newPrice = Double.parseDouble(price);
			}
			// initialize the PreparedStatement
			String query = buildParameterizedSqlStatementString(rest, soda, newPrice, useGreaterThanEqual);
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			// replace the ? with the actual parameter
			if (rest != null && !rest.isEmpty()) {
				stmt.setString(counter, rest);
				counter++;
			}
			if (soda != null && !soda.isEmpty()) {
				stmt.setString(counter, soda);
				counter++;
			}
			if (newPrice != null) {
				stmt.setDouble(counter, newPrice);
				counter++;
			}
			// run query
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve sodas by restaurant.");
			ex.printStackTrace();
			return new ArrayList<SodaByRestaurant>();
		}
		
	}
	/**
	 * Creates a string containing ? in the correct places in the SQL statement based on the filter information provided.
	 * 
	 * @param rest - The restaurant to match
	 * @param soda - The soda to match
	 * @param price - The price to compare to
	 * @param useGreaterThanEqual - If true, the prices returned should be greater than or equal to what's given, otherwise less than or equal.
	 * @return A string representing the SQL statement to be executed 
	 */
	private String buildParameterizedSqlStatementString(String rest, String soda, Double price, boolean useGreaterThanEqual) {
		String sqlStatement = "SELECT Restaurant, Soda, Manufacturer, RestaurantContact, Price \nFROM SodasByRestaurant\n";
		ArrayList<String> wheresToAdd = new ArrayList<String>();

		if (rest != null) {
			wheresToAdd.add("Restaurant = ?");
		}
		if (soda != null) {
			wheresToAdd.add("Soda = ?");
		}
		if (price != null) {
			if (useGreaterThanEqual) {
				wheresToAdd.add("Price >= ?");
			} else {
				wheresToAdd.add("Price <= ?");
			}
		}
		boolean isFirst = true;
		while (wheresToAdd.size() > 0) {
			if (isFirst) {
				sqlStatement = sqlStatement + " WHERE " + wheresToAdd.remove(0);
				isFirst = false;
			} else {
				sqlStatement = sqlStatement + " AND " + wheresToAdd.remove(0);
			}
		}
		return sqlStatement;
	}

	private ArrayList<SodaByRestaurant> parseResults(ResultSet rs) {
		try {
			ArrayList<SodaByRestaurant> sodasByRestaurants = new ArrayList<SodaByRestaurant>();
			int restNameIndex = rs.findColumn("Restaurant");
			int sodaNameIndex = rs.findColumn("Soda");
			int manfIndex = rs.findColumn("Manufacturer");
			int restContactIndex = rs.findColumn("RestaurantContact");
			int priceIndex = rs.findColumn("Price");
			while (rs.next()) {
				sodasByRestaurants.add(new SodaByRestaurant(rs.getString(restNameIndex), rs.getString(sodaNameIndex),
						rs.getString(manfIndex), rs.getString(restContactIndex), rs.getDouble(priceIndex)));
			}
			System.out.println(sodasByRestaurants.size());
			return sodasByRestaurants;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving sodas by restaurants. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<SodaByRestaurant>();
		}

	}


}
