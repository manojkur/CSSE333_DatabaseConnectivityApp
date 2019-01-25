package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Views.KingdomMilitary;

public class KingdomMilitaryService {
	private DatabaseConnectionService dbService = null;

	public KingdomMilitaryService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<KingdomMilitary> getKingdomBuiltOnTopOfView() {
		try {
			String query = "SELECT * \nFROM dbo.KingdomMilitary\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve kingdoms and the military.");
			ex.printStackTrace();
			return new ArrayList<>();
		}

	}

	private ArrayList<KingdomMilitary> parseResults(ResultSet rs) {
		try {
			ArrayList<KingdomMilitary> kingdoms = new ArrayList<>();
			while (rs.next()) {
				KingdomMilitary kingdom = new KingdomMilitary();
				kingdom.Name = rs.getString("Name");
				kingdom.ShortName = rs.getString("ShortName");
				kingdom.DateConquered = rs.getDate("DateConquered");
				kingdom.GDP = rs.getLong("GDP");
				kingdom.Succession = rs.getString("Succession");
				kingdom.Type = rs.getString("Type");
				kingdom.MilitaryName = rs.getString("MilitaryName");
				kingdom.budget = rs.getInt("Budget");
				kingdoms.add(kingdom);
			}
			return kingdoms;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving the list of kingdoms and the military. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<>();
		}

	}
}
