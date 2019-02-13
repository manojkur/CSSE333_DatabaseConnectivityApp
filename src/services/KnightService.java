package services;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import tables.Knight;

public class KnightService implements Services {
	private DatabaseConnectionService dbService = null;
	private JComponent view;

	public KnightService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public JPanel getJPanel() {
		JPanel panel = new JPanel(new CardLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		view = getScrollableTable();
		tabbedPane.addTab("View", view);
		JComboBox<String> dropDown = new JComboBox<>();
		JComboBox<String> dropDown2 = new JComboBox<>();

		int width = 500;
		int height = 20;

		JPanel insert = new JPanel();
		insert.setLayout(new BoxLayout(insert, BoxLayout.Y_AXIS));
		insert.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel insertPIDLabel = new JLabel("PID: ");
		insert.add(insertPIDLabel);
		JTextField insertPIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertPIDText);

		JLabel insertMIDLabel = new JLabel("MID: ");
		insert.add(insertMIDLabel);
		JTextField insertMIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertMIDText);

		JLabel insertKillCountLabel = new JLabel("KillCount: ");
		insert.add(insertKillCountLabel);
		JTextField insertKillCountText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertKillCountText);

		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Knight k = new Knight();
				try {
					k.PID = Integer.parseInt(insertPIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.KillCount = Integer.parseInt(insertKillCountText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.MID = Integer.parseInt(insertMIDText.getText());
				} catch (NumberFormatException e) {

				}
				addKnight(k);

				insertPIDText.setText("");
				insertMIDText.setText("");
				insertKillCountText.setText("");

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
				dropDown.removeAllItems();
				for (Knight knight : getKnights()) {
					dropDown.addItem("ID: " + knight.ID);
				}
				dropDown2.removeAllItems();
				for (Knight knight : getKnights()) {
					dropDown2.addItem("ID: " + knight.ID);
				}
			}
		});

		insert.add(insertButton);
		tabbedPane.addTab("Insert", insert);

		JPanel update = new JPanel();
		update.setLayout(new BoxLayout(update, BoxLayout.Y_AXIS));
		update.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (Knight knight : getKnights()) {
			dropDown.addItem("ID: " + knight.ID);
		}
		JPanel innerPanel = new JPanel(new FlowLayout());
		innerPanel.setMaximumSize(new Dimension(width, height + 20));
		innerPanel.add(dropDown);
		update.add(innerPanel);

		JLabel updatePIDLabel = new JLabel("PID: ");
		update.add(updatePIDLabel);
		JTextField updatePIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updatePIDText);

		JLabel updateMIDLabel = new JLabel("MID: ");
		update.add(updateMIDLabel);
		JTextField updateMIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateMIDText);

		JLabel updateKillCountLabel = new JLabel("KillCount: ");
		update.add(updateKillCountLabel);
		JTextField updateKillCountText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateKillCountText);

		dropDown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String id = dropDown.getSelectedItem().toString().split(" ")[1];
					List<Knight> knights = getKnights();
					Knight k = null;
					for (Knight knight : knights) {
						if (Integer.toString(knight.ID).equals(id)) {
							k = knight;
							break;
						}
					}
					updatePIDText.setText(Integer.toString(k.PID));
					updateMIDText.setText(Integer.toString(k.MID));
					updateKillCountText.setText(Integer.toString(k.KillCount));
				} catch (Exception e1) {
					updatePIDText.setText("");
					updateMIDText.setText("");
					updateKillCountText.setText("");
				}
			}
		});

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Knight k = new Knight();
				k.ID = Integer.parseInt(dropDown.getSelectedItem().toString().split(":")[1].substring(1));
				try {
					k.PID = Integer.parseInt(updatePIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.MID = Integer.parseInt(updateMIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.KillCount = Integer.parseInt(updateKillCountText.getText());
				} catch (NumberFormatException e) {

				}
				updateKnight(k);

				updatePIDText.setText("");
				updateMIDText.setText("");
				updateKillCountText.setText("");

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
				dropDown.removeAllItems();
				for (Knight knight : getKnights()) {
					dropDown.addItem("ID: " + knight.ID);
				}
				dropDown2.removeAllItems();
				for (Knight knight : getKnights()) {
					dropDown2.addItem("ID: " + knight.ID);
				}
			}
		});

		update.add(updateButton);
		tabbedPane.addTab("Update", update);

		JPanel delete = new JPanel();
		delete.setLayout(new BoxLayout(delete, BoxLayout.Y_AXIS));
		delete.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (Knight knight : getKnights()) {
			dropDown2.addItem("ID: " + knight.ID);
		}
		JPanel innerPanel2 = new JPanel(new FlowLayout());
		innerPanel2.setMaximumSize(new Dimension(width, height + 20));
		innerPanel2.add(dropDown2);
		delete.add(innerPanel2);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int id = Integer.parseInt(dropDown2.getSelectedItem().toString().split(" ")[1]);
				deleteKnight(id);

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
				dropDown.removeAllItems();
				for (Knight knight : getKnights()) {
					dropDown.addItem("ID: " + knight.ID);
				}
				dropDown2.removeAllItems();
				for (Knight knight : getKnights()) {
					dropDown2.addItem("ID: " + knight.ID);
				}
			}
		});

		delete.add(deleteButton);
		tabbedPane.addTab("Delete", delete);

		panel.add(tabbedPane);
		return panel;
	}

	public JComponent getScrollableTable() {
		String[] columnNames = { "ID", "PID", "MID", "KillCount" };
		ArrayList<Knight> knights = getKnights();
		Object[][] data = new Object[knights.size()][5];
		for (int i = 0; i < knights.size(); i++) {
			Knight k = knights.get(i);
			data[i] = k.getRow();
		}
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class;
				case 1:
					return Integer.class;
				case 2:
					return Integer.class;
				case 3:
					return Integer.class;
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

	public boolean addKnight(Knight k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Insert_Knight(?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, k.PID);
			cs.setInt(3, k.MID);
			cs.setInt(4, k.KillCount);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a Person ID");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a Military ID");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "Please provide a kill count of at least 0");
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "The Person ID " + k.PID + " does not exist");
				break;
			case 5:
				JOptionPane.showMessageDialog(null, "The Military ID " + k.MID + " does not exist");
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

	public boolean updateKnight(Knight k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Update_Knight(?, ?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, k.ID);
			cs.setInt(3, k.PID);
			cs.setInt(4, k.MID);
			cs.setInt(5, k.KillCount);
			cs.execute();

			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a valid ID");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a killcount of at least 0");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "Please provide a valid Person ID");
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "Please provide a valid Kingdom ID");
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

	public boolean deleteKnight(int ID) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.Delete_Knight(?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, ID);

			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a valid id");
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

	public ArrayList<String> getKnightNames() {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> sodas = new ArrayList<String>();
		try {
			stmt = dbService.getConnection().createStatement();
			stmt.execute("select distinct name from Knight");
			rs = stmt.getResultSet();
			while (rs.next()) {
				sodas.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sodas;
	}

	public ArrayList<Knight> getKnights() {
		try {
			String query = "SELECT ID, PID, MID, KillCount \nFROM Knight\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve knights.");
			ex.printStackTrace();
			return new ArrayList<Knight>();
		}

	}

	private ArrayList<Knight> parseResults(ResultSet rs) {
		try {
			ArrayList<Knight> knights = new ArrayList<Knight>();
			while (rs.next()) {
				Knight knight = new Knight();
				knight.ID = rs.getInt("ID");
				knight.PID = rs.getInt("PID");
				knight.MID = rs.getInt("MID");
				knight.KillCount = rs.getInt("KillCount");

				knights.add(knight);
			}
			return knights;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "An error ocurred while retrieving knights. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<Knight>();
		}

	}

}
