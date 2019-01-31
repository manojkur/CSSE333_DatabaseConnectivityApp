package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Views.KingdomCity;

public class KingdomCityService implements ViewServices {
	private DatabaseConnectionService dbService = null;

	public KingdomCityService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<KingdomCity> getKingdomWithCity() {
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

	public JComponent getScrollableTable() {
		String[] columnNames = "Name,ShortName,DateConquered,GDP,Succession,Type,CityName,Coordinates,Population"
				.split(",");
		ArrayList<KingdomCity> kingdoms = getKingdomWithCity();
		Object[][] data = new Object[kingdoms.size()][5];
		for (int i = 0; i < kingdoms.size(); i++) {
			KingdomCity k = kingdoms.get(i);
			data[i] = k.getRow();
		}
		JTable table = new JTable(data, columnNames);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);

		return scrollPane;
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
				kingdom.population = rs.getInt("Population");
				kingdom.Coordinates = rs.getString("Coordinates");
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