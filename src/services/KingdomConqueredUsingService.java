package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Views.KingdomConqueredUsing;

public class KingdomConqueredUsingService implements ViewServices {
	private DatabaseConnectionService dbService = null;

	public KingdomConqueredUsingService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<KingdomConqueredUsing> getKingdomConqueredWith() {
		try {
			String query = "SELECT * \nFROM dbo.KingdomConqueredUsing\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve kingdoms and how it was conquered.");
			ex.printStackTrace();
			return new ArrayList<>();
		}

	}

	public JComponent getScrollableTable() {
		String[] columnNames = "Name,ShortName,DateConquered,GDP,Succession,Type,ConqueredMethodName,Effectiveness"
				.split(",");
		ArrayList<KingdomConqueredUsing> kingdoms = getKingdomConqueredWith();
		Object[][] data = new Object[kingdoms.size()][5];
		for (int i = 0; i < kingdoms.size(); i++) {
			KingdomConqueredUsing k = kingdoms.get(i);
			data[i] = k.getRow();
		}
		JTable table = new JTable(data, columnNames);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);

		return scrollPane;
	}

	private ArrayList<KingdomConqueredUsing> parseResults(ResultSet rs) {
		try {
			ArrayList<KingdomConqueredUsing> kingdoms = new ArrayList<>();
			while (rs.next()) {
				KingdomConqueredUsing kingdom = new KingdomConqueredUsing();
				kingdom.Name = rs.getString("Name");
				kingdom.ShortName = rs.getString("ShortName");
				kingdom.DateConquered = rs.getDate("DateConquered");
				kingdom.GDP = rs.getLong("GDP");
				kingdom.Succession = rs.getString("Succession");
				kingdom.Type = rs.getString("Type");
				kingdom.ConqueredMethodName = rs.getString("ConqueredMethodName");
				kingdom.effectiveness = rs.getString("Effectiveness");
				kingdoms.add(kingdom);
			}
			return kingdoms;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving the list of kingdoms and how it was conquered. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<>();
		}

	}
}