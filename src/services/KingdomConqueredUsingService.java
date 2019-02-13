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