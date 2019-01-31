package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Views.KingdomBuiltOnTopOf;

public class KingdomBuiltOnTopOfService implements ViewServices {
	private DatabaseConnectionService dbService = null;

	public KingdomBuiltOnTopOfService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<KingdomBuiltOnTopOf> getKingdomBuiltOnTopOfView() {
		try {
			String query = "SELECT * FROM dbo.KingdomBuiltOnTopOf\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve kingdoms by what they were built on top of.");
			ex.printStackTrace();
			return new ArrayList<KingdomBuiltOnTopOf>();
		}

	}

	public JComponent getScrollableTable() {
		String[] columnNames = "Name,ShortName,DateConquered,GDP,Succession,Type,CityName,TerrainName,TraverseDifficulty"
				.split(",");
		ArrayList<KingdomBuiltOnTopOf> kingdoms = getKingdomBuiltOnTopOfView();
		Object[][] data = new Object[kingdoms.size()][5];
		for (int i = 0; i < kingdoms.size(); i++) {
			KingdomBuiltOnTopOf k = kingdoms.get(i);
			data[i] = k.getRow();
		}
		JTable table = new JTable(data, columnNames);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);

		return scrollPane;
	}

	private ArrayList<KingdomBuiltOnTopOf> parseResults(ResultSet rs) {
		try {
			ArrayList<KingdomBuiltOnTopOf> kingdoms = new ArrayList<KingdomBuiltOnTopOf>();
			// int NameIndex = rs.findColumn("Name");
			// int ShortNameIndex = rs.findColumn("ShortName");
			// int DateConqueredIndex = rs.findColumn("DateConquered");
			// int GDPIndex = rs.findColumn("GDP");
			// int SuccessionIndex = rs.findColumn("Succession");
			// int TypeIndex = rs.findColumn("Type");
			// int terrainNameIndex = rs.findColumn("TerrainName");
			// int traverseDifficultyIndex =
			// rs.findColumn("TraverseDifficulty");
			while (rs.next()) {
				KingdomBuiltOnTopOf kingdom = new KingdomBuiltOnTopOf();
				kingdom.Name = rs.getString("Name");
				kingdom.ShortName = rs.getString("ShortName");
				kingdom.DateConquered = rs.getDate("DateConquered");
				kingdom.GDP = rs.getLong("GDP");
				kingdom.Succession = rs.getString("Succession");
				kingdom.Type = rs.getString("Type");
				kingdom.cityName = rs.getString("CityName");
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