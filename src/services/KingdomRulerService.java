package services;

import java.awt.BorderLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Views.KingdomRuler;

public class KingdomRulerService implements ViewServices {
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
		String[] columnNames = "Name,ShortName,DateConquered,GDP,Succession,Type,YearsOfExperience,Title,Dynasty,FirstName,LastName,Gender,OtherNames,Suffix"
				.split(",");
		ArrayList<KingdomRuler> kingdoms = getKingdomWithRuler();
		Object[][] data = new Object[kingdoms.size()][5];
		for (int i = 0; i < kingdoms.size(); i++) {
			KingdomRuler k = kingdoms.get(i);
			data[i] = k.getRow();
		}
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 2:
					return java.sql.Date.class;
				case 3:
					return Long.class;
				case 6:
					return Integer.class;
				case 11:
					return Character.class;
				default:
					return String.class;
				}
			}
		};
		JTable table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		table.setColumnSelectionAllowed(true);
		table.setRowSelectionAllowed(true);

		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
		JTextField jtfFilter = new JTextField();
		JButton jbtFilter = new JButton("Filter");

		table.setRowSorter(rowSorter);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Search:"), BorderLayout.WEST);
		panel.add(jtfFilter, BorderLayout.CENTER);

		JPanel scrollPane = new JPanel();
		scrollPane.setLayout(new BorderLayout());
		scrollPane.add(panel, BorderLayout.SOUTH);
		scrollPane.add(new JScrollPane(table), BorderLayout.CENTER);

		jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException();
			}

		});

		return scrollPane;
	}

	private ArrayList<KingdomRuler> parseResults(ResultSet rs) {
		try {
			ArrayList<KingdomRuler> kingdoms = new ArrayList<>();
			// int NameIndex = rs.findColumn("Name");
			// int ShortNameIndex = rs.findColumn("ShortName");
			// int DateConqueredIndex = rs.findColumn("DateConquered");
			// int GDPIndex = rs.findColumn("GDP");
			// int SuccessionIndex = rs.findColumn("Succession");
			// int TypeIndex = rs.findColumn("Type");
			// int yearsOfExperienceIndex = rs.findColumn("YearsOfExperience");
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