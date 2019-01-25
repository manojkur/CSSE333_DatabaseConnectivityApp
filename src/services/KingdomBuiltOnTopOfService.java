package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Views.KingdomBuiltOnTopOf;

public class KingdomBuiltOnTopOfService {
	private DatabaseConnectionService dbService = null;

	public KingdomBuiltOnTopOfService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<KingdomBuiltOnTopOf> getKingdomBuiltOnTopOfView() {
		try {
			String query = "SELECT * \nFROM dbo.KingdomBuiltOnTopOf\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve kingdoms by what they were buillt on top of.");
			ex.printStackTrace();
			return new ArrayList<KingdomBuiltOnTopOf>();
		}

	}

	private ArrayList<KingdomBuiltOnTopOf> parseResults(ResultSet rs) {
		try {
			ArrayList<KingdomBuiltOnTopOf> kingdoms = new ArrayList<KingdomBuiltOnTopOf>();
//			int NameIndex = rs.findColumn("Name");
//			int ShortNameIndex = rs.findColumn("ShortName");
//			int DateConqueredIndex = rs.findColumn("DateConquered");
//			int GDPIndex = rs.findColumn("GDP");
//			int SuccessionIndex = rs.findColumn("Succession");
//			int TypeIndex = rs.findColumn("Type");
//			int terrainNameIndex = rs.findColumn("TerrainName");
//			int traverseDifficultyIndex = rs.findColumn("TraverseDifficulty");
			while (rs.next()) {
				KingdomBuiltOnTopOf kingdom = new KingdomBuiltOnTopOf();
				kingdom.Name = rs.getString("Name");
				kingdom.ShortName = rs.getString("ShortName");
				kingdom.DateConquered = rs.getDate("DateConquered");
				kingdom.GDP = rs.getLong("GDP");
				kingdom.Succession = rs.getString("Succession");
				kingdom.Type = rs.getString("Type");
				kingdom.terrainName = rs.getString("TerrainName");
				kingdom.traverseDifficulty = rs.getString("TraverseDifficulty");

				kingdoms.add(kingdom);
			}
			return kingdoms;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving the list of kingdoms and what they were built on top if. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<KingdomBuiltOnTopOf>();
		}

	}
}