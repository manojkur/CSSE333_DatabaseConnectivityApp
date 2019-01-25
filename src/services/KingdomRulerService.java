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
import Views.KingdomRuler;

public class KingdomRulerService {
	private DatabaseConnectionService dbService = null;

	public KingdomRulerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<KingdomRuler> getKingdomWithRuler() {
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
	
	public JComponent getScrollableTable() {
		String[] columnNames = "Name,ShortName,DateConquered,GDP,Succession,Type,YearsOfExperience,Title,Dynasty,FirstName,LastName,Gender,OtherNames,Suffix".split(",");
		ArrayList<KingdomRuler> kingdoms = getKingdomWithRuler();
		Object[][] data = new Object[kingdoms.size()][5];
		for (int i = 0; i < kingdoms.size(); i++) {
			KingdomRuler k = kingdoms.get(i);
			data[i] = k.getRow();
		}
		JTable table = new JTable(data, columnNames);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);

		return scrollPane;
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