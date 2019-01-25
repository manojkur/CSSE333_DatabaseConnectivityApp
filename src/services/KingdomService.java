package services;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import tables.Kingdom;

public class KingdomService {
	private DatabaseConnectionService dbService = null;

	public KingdomService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public JPanel getJPanel() {
		JPanel panel = new JPanel(new CardLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		JComponent view = getScrollableTable();
		tabbedPane.addTab("View", view);

		JPanel insert = new JPanel();
		insert.setLayout(new BoxLayout(insert, BoxLayout.Y_AXIS));
		JLabel insertNameLabel = new JLabel("Name: ");
		insert.add(insertNameLabel);
		JTextField insertNameText = new JTextField();
		insert.add(insertNameText);

		JLabel insertShortNameLabel = new JLabel("ShortName: ");
		insert.add(insertShortNameLabel);
		JTextField insertShortNameText = new JTextField();
		insert.add(insertShortNameText);

		JLabel insertDateConqueredYearLabel = new JLabel("Date Conquered Year: ");
		insert.add(insertDateConqueredYearLabel);
		JTextField insertDateConqueredYearText = new JTextField();
		insert.add(insertDateConqueredYearText);

		JLabel insertDateConqueredMonthLabel = new JLabel("Date Conquered Month: ");
		insert.add(insertDateConqueredMonthLabel);
		JTextField insertDateConqueredMonthText = new JTextField();
		insert.add(insertDateConqueredMonthText);

		JLabel insertDateConqueredDayLabel = new JLabel("Date Conquered Day: ");
		insert.add(insertDateConqueredDayLabel);
		JTextField insertDateConqueredDayText = new JTextField();
		insert.add(insertDateConqueredDayText);

		JLabel insertGdpLabel = new JLabel("GDP: ");
		insert.add(insertGdpLabel);
		JTextField insertGdpText = new JTextField();
		insert.add(insertGdpText);

		JLabel insertSuccessionLabel = new JLabel("Succession: ");
		insert.add(insertSuccessionLabel);
		JTextField insertSuccessionText = new JTextField();
		insert.add(insertSuccessionText);

		JLabel insertTypeLabel = new JLabel("Type: ");
		insert.add(insertTypeLabel);
		JTextField insertTypeText = new JTextField();
		insert.add(insertTypeText);

		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				insertSuccessionText.getText();
			}
		});

		insert.add(insertButton);
		tabbedPane.addTab("Insert", insert);
//		panel.add(view);
//		String[] actions = { "View", "Insert", "Update", "Delete" };
//		JComboBox actionsComboBox = new JComboBox(actions);
//		actionsComboBox.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				JComboBox combo = (JComboBox) e.getSource();
//				String setting = (String) combo.getSelectedItem();
//				if (setting.equals("View")) {
//					panel.remove
//					panel.add(getScrollableTable());
//					panel.revalidate();
//					panel.repaint();
//				} else {
//					panel.remove(1);
//					panel.revalidate();
//					panel.repaint();
//				}
//			}
//		});
		panel.add(tabbedPane);
		return panel;
	}

	public JComponent getScrollableTable() {
		String[] columnNames = { "ID", "Name", "ShortName", "DateConquered", "GDP", "Succession", "Type" };
		ArrayList<Kingdom> kingdoms = getKingdoms();
		Object[][] data = new Object[kingdoms.size()][5];
		for (int i = 0; i < kingdoms.size(); i++) {
			Kingdom k = kingdoms.get(i);
			data[i] = k.getKingdom();
		}
		JTable table = new JTable(data, columnNames);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);

		return scrollPane;
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
