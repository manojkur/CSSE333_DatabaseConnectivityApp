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

import tables.FunctionsUsing;

public class FunctionsUsingService implements Services {
	private DatabaseConnectionService dbService = null;
	private JComponent view;

	public FunctionsUsingService(DatabaseConnectionService dbService) {
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

		JLabel insertRIDLabel = new JLabel("RID: ");
		insert.add(insertRIDLabel);
		JTextField insertRIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertRIDText);

		JLabel insertCIDLabel = new JLabel("CID: ");
		insert.add(insertCIDLabel);
		JTextField insertCIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertCIDText);

		JLabel insertQuantityLabel = new JLabel("Quantity: ");
		insert.add(insertQuantityLabel);
		JTextField insertQuantityText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertQuantityText);

		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FunctionsUsing k = new FunctionsUsing();
				try {
					k.RID = Integer.parseInt(insertRIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.Quantity = Integer.parseInt(insertQuantityText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.CID = Integer.parseInt(insertCIDText.getText());
				} catch (NumberFormatException e) {

				}
				addFunctionsUsing(k);

				insertRIDText.setText("");
				insertCIDText.setText("");
				insertQuantityText.setText("");

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
				dropDown.removeAllItems();
				for (FunctionsUsing functionsUsing : getFunctionsUsings()) {
					dropDown.addItem("ID: " + functionsUsing.ID);
				}
				dropDown2.removeAllItems();
				for (FunctionsUsing functionsUsing : getFunctionsUsings()) {
					dropDown2.addItem("ID: " + functionsUsing.ID);
				}

			}
		});

		insert.add(insertButton);
		tabbedPane.addTab("Insert", insert);

		JPanel update = new JPanel();
		update.setLayout(new BoxLayout(update, BoxLayout.Y_AXIS));
		update.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (FunctionsUsing functionsUsing : getFunctionsUsings()) {
			dropDown.addItem("ID: " + functionsUsing.ID);
		}
		JPanel innerPanel = new JPanel(new FlowLayout());
		innerPanel.setMaximumSize(new Dimension(width, height + 20));
		innerPanel.add(dropDown);
		update.add(innerPanel);

		JLabel updateRIDLabel = new JLabel("RID: ");
		update.add(updateRIDLabel);
		JTextField updateRIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateRIDText);

		JLabel updateCIDLabel = new JLabel("CID: ");
		update.add(updateCIDLabel);
		JTextField updateCIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateCIDText);

		JLabel updateQuantityLabel = new JLabel("Quantity: ");
		update.add(updateQuantityLabel);
		JTextField updateQuantityText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateQuantityText);

		dropDown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String id = dropDown.getSelectedItem().toString().split(" ")[1];
					List<FunctionsUsing> functionsUsings = getFunctionsUsings();
					FunctionsUsing k = null;
					for (FunctionsUsing functionsUsing : functionsUsings) {
						if (Integer.toString(functionsUsing.ID).equals(id)) {
							k = functionsUsing;
							break;
						}
					}
					updateRIDText.setText(Integer.toString(k.RID));
					updateCIDText.setText(Integer.toString(k.CID));
					updateQuantityText.setText(Integer.toString(k.Quantity));
				} catch (Exception e1) {
					updateRIDText.setText("");
					updateCIDText.setText("");
					updateQuantityText.setText("");
				}
			}
		});

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FunctionsUsing k = new FunctionsUsing();
				k.ID = Integer.parseInt(dropDown.getSelectedItem().toString().split(" ")[1]);
				try {
					k.RID = Integer.parseInt(updateRIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.CID = Integer.parseInt(updateCIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.Quantity = Integer.parseInt(updateQuantityText.getText());
				} catch (NumberFormatException e) {

				}
				updateFunctionsUsing(k);

				updateRIDText.setText("");
				updateCIDText.setText("");
				updateQuantityText.setText("");

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
				dropDown.removeAllItems();
				for (FunctionsUsing functionsUsing : getFunctionsUsings()) {
					dropDown.addItem("ID: " + functionsUsing.ID);
				}
				dropDown2.removeAllItems();
				for (FunctionsUsing functionsUsing : getFunctionsUsings()) {
					dropDown2.addItem("ID: " + functionsUsing.ID);
				}
			}
		});

		update.add(updateButton);
		tabbedPane.addTab("Update", update);

		JPanel delete = new JPanel();
		delete.setLayout(new BoxLayout(delete, BoxLayout.Y_AXIS));
		delete.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (FunctionsUsing functionsUsing : getFunctionsUsings()) {
			dropDown2.addItem("ID: " + functionsUsing.ID);
		}
		JPanel innerPanel2 = new JPanel(new FlowLayout());
		innerPanel2.setMaximumSize(new Dimension(width, height + 20));
		innerPanel2.add(dropDown2);
		delete.add(innerPanel2);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				int id = Integer.parseInt(dropDown2.getSelectedItem().toString().split(" ")[1]);
				deleteFunctionsUsing(id);

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
				dropDown.removeAllItems();
				for (FunctionsUsing functionsUsing : getFunctionsUsings()) {
					dropDown.addItem("ID: " + functionsUsing.ID);
				}
				dropDown2.removeAllItems();
				for (FunctionsUsing functionsUsing : getFunctionsUsings()) {
					dropDown2.addItem("ID: " + functionsUsing.ID);
				}
			}
		});

		delete.add(deleteButton);
		tabbedPane.addTab("Delete", delete);

		panel.add(tabbedPane);
		return panel;
	}

	public JComponent getScrollableTable() {
		String[] columnNames = { "ID", "RID", "CID", "Quantity" };
		ArrayList<FunctionsUsing> functionsUsings = getFunctionsUsings();
		Object[][] data = new Object[functionsUsings.size()][5];
		for (int i = 0; i < functionsUsings.size(); i++) {
			FunctionsUsing k = functionsUsings.get(i);
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

	public boolean addFunctionsUsing(FunctionsUsing k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Insert_FunctionsUsing(?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, k.RID);
			cs.setInt(3, k.CID);
			cs.setInt(4, k.Quantity);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a name");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a City ID");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "Please provide an Quantity of at least 0");
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "The Resource ID " + k.RID + " does not exist");
				break;
			case 5:
				JOptionPane.showMessageDialog(null, "The City ID " + k.CID + " does not exist");
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

	public boolean updateFunctionsUsing(FunctionsUsing k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Update_FunctionsUsing(?, ?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, k.ID);
			cs.setInt(3, k.RID);
			cs.setInt(4, k.CID);
			cs.setInt(5, k.Quantity);
			cs.execute();

			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a valid ID");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a quantity of at least 0");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "Please provide a valid Resource ID");
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "Please provide a valid City ID");
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

	public boolean deleteFunctionsUsing(int ID) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Delete_FunctionsUsing(?) }");
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

	public ArrayList<String> getFunctionsUsingNames() {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> sodas = new ArrayList<String>();
		try {
			stmt = dbService.getConnection().createStatement();
			stmt.execute("select distinct name from FunctionsUsing");
			rs = stmt.getResultSet();
			while (rs.next()) {
				sodas.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sodas;
	}

	public ArrayList<FunctionsUsing> getFunctionsUsings() {
		try {
			String query = "SELECT ID, RID, CID, Quantity \nFROM FunctionsUsing\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve functionsUsings.");
			ex.printStackTrace();
			return new ArrayList<FunctionsUsing>();
		}

	}

	private ArrayList<FunctionsUsing> parseResults(ResultSet rs) {
		try {
			ArrayList<FunctionsUsing> functionsUsings = new ArrayList<FunctionsUsing>();
			while (rs.next()) {
				FunctionsUsing functionsUsing = new FunctionsUsing();
				functionsUsing.ID = rs.getInt("ID");
				functionsUsing.RID = rs.getInt("RID");
				functionsUsing.CID = rs.getInt("CID");
				functionsUsing.Quantity = rs.getInt("Quantity");

				functionsUsings.add(functionsUsing);
			}
			return functionsUsings;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving functionsUsings. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<FunctionsUsing>();
		}

	}

}
