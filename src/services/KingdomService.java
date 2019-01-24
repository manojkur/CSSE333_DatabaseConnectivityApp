<<<<<<< HEAD
package services;

import java.awt.BorderLayout;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import tables.Kingdom;

public class KingdomService {
	private DatabaseConnectionService dbService = null;

	public KingdomService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public JPanel getJPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		String[] actions = { "View", "Insert", "Update", "Delete" };
		JComboBox actionsComboBox = new JComboBox(actions);
		actionsComboBox.setSelectedIndex(4);
		// actionsComboBox.addActionListener(this);

		String[] columnNames = { "ID", "Name", "ShortName", "DateConquered", "GDP", "Succession", "Type" };
		ArrayList<Kingdom> kingdoms = getKingdoms();
		Object[][] data = new Object[kingdoms.size()][5];
		for (int i = 0; i < kingdoms.size(); i++) {
			Kingdom k = kingdoms.get(i);
			data[i] = k.getKingdom();
		}
		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);

		panel.add(actionsComboBox, BorderLayout.PAGE_START);
		panel.add(scrollPane, BorderLayout.CENTER);
		return panel;
	}

	public boolean addKingdom(Kingdom k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Insert_Kingdom(?, ?, ?, ?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, k.Name);
			cs.setString(3, k.ShortName);
			cs.setDate(2, k.DateConquered);
			cs.setLong(3, k.GDP);
			cs.setString(2, k.Succession);
			cs.setString(3, k.Type);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a Name");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a ShortName");
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "Please provide a GDP");
				break;
			case 5:
				JOptionPane.showMessageDialog(null, "Please provide a Date that has happened");
				break;
			case 6:
				JOptionPane.showMessageDialog(null, "Please provide the Kingdom Type");
				break;
			default:
				break;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

//	public boolean updateKingdom(String KingdomName, String ShortName, Date DateConquered, int GDP, String Succession,
//			String Type) {
//		try {
//			CallableStatement cs = this.dbService.getConnection()
//					.prepareCall("{ ? = call dbo.Insert_Kingdom(?, ?, ?, ?, ?, ?) }");
//			cs.registerOutParameter(1, Types.INTEGER);
//			cs.setString(2, k.Name);
//			cs.setString(3, k.ShortName);
//			cs.setDate(2, k.DateConquered);
//			cs.setInt(3, k.GDP);
//			cs.setString(2, k.Succession);
//			cs.setString(3, k.Type);
//			cs.execute();
//			int returnVal = cs.getInt(1);
//			switch (returnVal) {
//			case 1:
//				JOptionPane.showMessageDialog(null, "Please provide a Name");
//				break;
//			case 2:
//				JOptionPane.showMessageDialog(null, "Please provide a ShortName");
//				break;
//			case 4:
//				JOptionPane.showMessageDialog(null, "Please provide a GDP");
//				break;
//			case 5:
//				JOptionPane.showMessageDialog(null, "Please provide a Date that has happened");
//				break;
//			case 6:
//				JOptionPane.showMessageDialog(null, "Please provide the Kingdom Type");
//				break;
//			default:
//				break;
//			}
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

	public ArrayList<String> getKingdomNames() {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> sodas = new ArrayList<String>();
		try {
			stmt = dbService.getConnection().createStatement();
			stmt.execute("select distinct name from Kingdom");
			rs = stmt.getResultSet();
			while (rs.next()) {
				sodas.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sodas;
	}

	public ArrayList<Kingdom> getKingdoms() {
		try {
			String query = "SELECT ID, Name, ShortName, DateConquered, GDP, Succession, Type \nFROM Kingdom\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve sodas by restaurant.");
			ex.printStackTrace();
			return new ArrayList<Kingdom>();
		}

	}

	private ArrayList<Kingdom> parseResults(ResultSet rs) {
		try {
			ArrayList<Kingdom> kingdoms = new ArrayList<Kingdom>();
			int IDIndex = rs.findColumn("ID");
			int NameIndex = rs.findColumn("Name");
			int ShortNameIndex = rs.findColumn("ShortName");
			int DateConqueredIndex = rs.findColumn("DateConquered");
			int GDPIndex = rs.findColumn("GDP");
			int SuccessionIndex = rs.findColumn("Succession");
			int TypeIndex = rs.findColumn("Type");
			while (rs.next()) {
				Kingdom kingdom = new Kingdom();
				kingdom.ID = rs.getInt(IDIndex);
				kingdom.Name = rs.getString(NameIndex);
				kingdom.ShortName = rs.getString(ShortNameIndex);
				kingdom.DateConquered = rs.getDate(DateConqueredIndex);
				kingdom.GDP = rs.getLong(GDPIndex);
				kingdom.Succession = rs.getString(SuccessionIndex);
				kingdom.Type = rs.getString(TypeIndex);

				kingdoms.add(kingdom);
			}
			return kingdoms;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving sodas by restaurants. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<Kingdom>();
		}

	}

}
=======
package services;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import tables.Kingdom;

public class KingdomService {
	private DatabaseConnectionService dbService = null;

	public KingdomService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean addKingdom(Kingdom k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Insert_Kingdom(?, ?, ?, ?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, k.Name);
			cs.setString(3, k.ShortName);
			cs.setDate(4, k.DateConquered);
			cs.setLong(5, k.GDP);
			cs.setString(6, k.Succession);
			cs.setString(7, k.Type);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a Name");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a ShortName");
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "Please provide a GDP");
				break;
			case 5:
				JOptionPane.showMessageDialog(null, "Please provide a Date that has happened");
				break;
			case 6:
				JOptionPane.showMessageDialog(null, "Please provide the Kingdom Type");
				break;
			default:
				break;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateKingdom(int id, String KingdomName, String ShortName, Date DateConquered, long GDP,
			String Succession, String Type) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Update_Kingdom(?,?,?,?,?,?,?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			cs.setString(3, KingdomName);
			cs.setString(4, ShortName);
			cs.setDate(5, DateConquered);
			cs.setLong(6, GDP);
			cs.setString(7, Succession);
			cs.setString(8, Type);

			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a valid id");
				break;
			case 5:
				JOptionPane.showMessageDialog(null, "Please provide a Date that has happened");
				break;
			default:
				break;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<String> getKingdomNames() {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> sodas = new ArrayList<String>();
		try {
			stmt = dbService.getConnection().createStatement();
			stmt.execute("select distinct name from Kingdom");
			rs = stmt.getResultSet();
			while (rs.next()) {
				sodas.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sodas;
	}

	public ArrayList<Kingdom> getKingdoms() {
		try {
			String query = "SELECT ID, Name, ShortName, DateConquered, GDP, Succession, Type \nFROM Kingdom\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve sodas by restaurant.");
			ex.printStackTrace();
			return new ArrayList<Kingdom>();
		}

	}

	private ArrayList<Kingdom> parseResults(ResultSet rs) {
		try {
			ArrayList<Kingdom> kingdoms = new ArrayList<Kingdom>();
			int IDIndex = rs.findColumn("ID");
			int NameIndex = rs.findColumn("Name");
			int ShortNameIndex = rs.findColumn("ShortName");
			int DateConqueredIndex = rs.findColumn("DateConquered");
			int GDPIndex = rs.findColumn("GDP");
			int SuccessionIndex = rs.findColumn("Succession");
			int TypeIndex = rs.findColumn("Type");
			while (rs.next()) {
				Kingdom kingdom = new Kingdom();
				kingdom.ID = rs.getInt(IDIndex);
				kingdom.Name = rs.getString(NameIndex);
				kingdom.ShortName = rs.getString(ShortNameIndex);
				kingdom.DateConquered = rs.getDate(DateConqueredIndex);
				kingdom.GDP = rs.getLong(GDPIndex);
				kingdom.Succession = rs.getString(SuccessionIndex);
				kingdom.Type = rs.getString(TypeIndex);

				kingdoms.add(kingdom);
			}
			return kingdoms;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving sodas by restaurants. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<Kingdom>();
		}

	}

}
>>>>>>> 6ec56f5a9d59b914ae6f2236415365ba56e571b9
