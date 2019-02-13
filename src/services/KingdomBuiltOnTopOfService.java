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
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 2:
					return java.sql.Date.class;
				case 3:
					return Long.class;
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