package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Views.KingdomCity;

public class KingdomCityService {
	private DatabaseConnectionService dbService = null;

	public KingdomCityService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<KingdomCity> getKingdomBuiltOnTopOfView() {
		try {
			String query = "SELECT * \nFROM dbo.KingdomCity\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve kingdoms and its cities.");
			ex.printStackTrace();
			return new ArrayList<>();
		}

	}

	private ArrayList<KingdomCity> parseResults(ResultSet rs) {
		try {
			ArrayList<KingdomCity> kingdoms = new ArrayList<>();
			while (rs.next()) {
				KingdomCity kingdom = new KingdomCity();
				kingdom.Name = rs.getString("Name");
				kingdom.ShortName = rs.getString("ShortName");
				kingdom.DateConquered = rs.getDate("DateConquered");
				kingdom.GDP = rs.getLong("GDP");
				kingdom.Succession = rs.getString("Succession");
				kingdom.Type = rs.getString("Type");
				kingdom.cityName = rs.getString("CityName");
				kingdom.latitude = rs.getInt("Latitude");
				kingdom.longitude = rs.getInt("Longitude");
				kingdom.population = rs.getInt("Population");
				kingdoms.add(kingdom);
			}
			return kingdoms;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving the list of kingdoms and its cities. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<>();
		}

	}
}
