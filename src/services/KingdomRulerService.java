package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Views.KingdomRuler;

public class KingdomRulerService {
	private DatabaseConnectionService dbService = null;

	public KingdomRulerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<KingdomRuler> getKingdomBuiltOnTopOfView() {
		try {
			String query = "SELECT * \nFROM dbo.KingdomRuler\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve kingdoms and the rulers.");
			ex.printStackTrace();
			return new ArrayList<KingdomRuler>();
		}

	}

	private ArrayList<KingdomRuler> parseResults(ResultSet rs) {
		try {
			ArrayList<KingdomRuler> kingdoms = new ArrayList<>();
//			int NameIndex = rs.findColumn("Name");
//			int ShortNameIndex = rs.findColumn("ShortName");
//			int DateConqueredIndex = rs.findColumn("DateConquered");
//			int GDPIndex = rs.findColumn("GDP");
//			int SuccessionIndex = rs.findColumn("Succession");
//			int TypeIndex = rs.findColumn("Type");
//			int yearsOfExperienceIndex = rs.findColumn("YearsOfExperience");
			while (rs.next()) {
				KingdomRuler kingdom = new KingdomRuler();
				kingdom.Name = rs.getString("Name");
				kingdom.ShortName = rs.getString("ShortName");
				kingdom.DateConquered = rs.getDate("DateConquered");
				kingdom.GDP = rs.getLong("GDP");
				kingdom.Succession = rs.getString("Succession");
				kingdom.Type = rs.getString("Type");
				kingdom.YearsOfExperience = rs.getInt("YearsOfExperience");
				kingdom.title = rs.getString("Title");
				kingdom.dynasty = rs.getString("Dynasty");
				kingdom.firstName = rs.getString("FirstName");
				kingdom.lastName = rs.getString("LastName");
				kingdom.gender = rs.getString("Gender").toCharArray()[0];
				kingdom.otherNames = rs.getString("OtherNames");
				kingdom.suffix = rs.getString("Suffix");
				kingdoms.add(kingdom);
			}
			return kingdoms;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving the list of kingdoms and the rulers. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<>();
		}

	}
}
