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

import tables.Heir;

public class HeirService implements Services {
	private DatabaseConnectionService dbService = null;
	private JComponent view;

	public HeirService(DatabaseConnectionService dbService) {
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

		JLabel insertKIDLabel = new JLabel("KID: ");
		insert.add(insertKIDLabel);
		JTextField insertKIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertKIDText);

		JLabel insertTitleStartLabel = new JLabel("TitleStart: ");
		insert.add(insertTitleStartLabel);
		JTextField insertTitleStartText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertTitleStartText);

		JLabel insertTitleEndLabel = new JLabel("TitleEnd: ");
		insert.add(insertTitleEndLabel);
		JTextField insertTitleEndText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertTitleEndText);

		JLabel insertShortTitleLabel = new JLabel("ShortTitle: ");
		insert.add(insertShortTitleLabel);
		JTextField insertShortTitleText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertShortTitleText);

		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Heir k = new Heir();
				try {
					k.PID = Integer.parseInt(insertPIDText.getText());
					k.KID = Integer.parseInt(insertKIDText.getText());
				} catch (NumberFormatException e) {

				}
				k.TitleStart = insertTitleStartText.getText();
				k.TitleEnd = insertTitleEndText.getText();
				k.ShortTitle = insertShortTitleText.getText();
				addHeir(k);

				insertPIDText.setText("");
				insertKIDText.setText("");
				insertTitleEndText.setText("");
				insertTitleStartText.setText("");
				insertShortTitleText.setText("");

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
				dropDown.removeAllItems();
				for (Heir heir : getHeirs()) {
					dropDown.addItem("ID: " + heir.ID + " - Name:  " + heir.TitleEnd);
				}
				dropDown2.removeAllItems();
				for (Heir heir : getHeirs()) {
					dropDown2.addItem("ID: " + heir.ID + " - Name:  " + heir.TitleEnd);
				}
			}
		});

		insert.add(insertButton);
		tabbedPane.addTab("Insert", insert);

		JPanel update = new JPanel();
		update.setLayout(new BoxLayout(update, BoxLayout.Y_AXIS));
		update.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (Heir heir : getHeirs()) {
			dropDown.addItem("ID: " + heir.ID + " - Name:  " + heir.TitleEnd);
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

		JLabel updateKIDLabel = new JLabel("KID: ");
		update.add(updateKIDLabel);
		JTextField updateKIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateKIDText);

		JLabel updateTitleStartLabel = new JLabel("TitleStart: ");
		update.add(updateTitleStartLabel);
		JTextField updateTitleStartText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateTitleStartText);

		JLabel updateTitleEndLabel = new JLabel("TitleEnd: ");
		update.add(updateTitleEndLabel);
		JTextField updateTitleEndText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateTitleEndText);

		JLabel updateShortTitleLabel = new JLabel("ShortTitle: ");
		update.add(updateShortTitleLabel);
		JTextField updateShortTitleText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateShortTitleText);

		dropDown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					String id = dropDown.getSelectedItem().toString().split("-")[0].split(" ")[1];
					Heir heir = null;
					for (Heir k : getHeirs()) {
						if (Integer.toString(k.ID).equals(id)) {
							heir = k;
							break;
						}
					}
					updatePIDText.setText(Integer.toString(heir.PID));
					updateKIDText.setText(Integer.toString(heir.KID));
					updateTitleStartText.setText(heir.TitleStart);
					updateTitleEndText.setText(heir.TitleEnd);
					updateShortTitleText.setText(heir.ShortTitle);
				} catch (Exception e1) {
					updatePIDText.setText("");
					updateKIDText.setText("");
					updateTitleEndText.setText("");
					updateTitleStartText.setText("");
					updateShortTitleText.setText("");
				}
			}
		});

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Heir k = new Heir();
				k.ID = Integer.parseInt(dropDown.getSelectedItem().toString().split("-")[0].split(" ")[1]);
				try {
					k.PID = Integer.parseInt(updatePIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.KID = Integer.parseInt(updateKIDText.getText());
				} catch (NumberFormatException e) {

				}
				k.TitleStart = updateTitleStartText.getText();
				k.TitleEnd = updateTitleEndText.getText();
				k.ShortTitle = updateShortTitleText.getText();
				updateHeir(k);

				updatePIDText.setText("");
				updateKIDText.setText("");
				updateTitleEndText.setText("");
				updateTitleStartText.setText("");
				updateShortTitleText.setText("");

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
				dropDown.removeAllItems();
				for (Heir heir : getHeirs()) {
					dropDown.addItem("ID: " + heir.ID + " - Name:  " + heir.TitleEnd);
				}
				dropDown2.removeAllItems();
				for (Heir heir : getHeirs()) {
					dropDown2.addItem("ID: " + heir.ID + " - Name:  " + heir.TitleEnd);
				}
			}
		});

		update.add(updateButton);
		tabbedPane.addTab("Update", update);

		JPanel delete = new JPanel();
		delete.setLayout(new BoxLayout(delete, BoxLayout.Y_AXIS));
		delete.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (Heir heir : getHeirs()) {
			dropDown2.addItem("ID: " + heir.ID + " - Name:  " + heir.TitleEnd);
		}
		JPanel innerPanel2 = new JPanel(new FlowLayout());
		innerPanel2.setMaximumSize(new Dimension(width, height + 20));
		innerPanel2.add(dropDown2);
		delete.add(innerPanel2);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				int id = Integer.parseInt(dropDown2.getSelectedItem().toString().split("-")[0].split(" ")[1]);
				deleteHeir(id);

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
				dropDown.removeAllItems();
				for (Heir heir : getHeirs()) {
					dropDown.addItem("ID: " + heir.ID + " - Name:  " + heir.TitleEnd);
				}
				dropDown2.removeAllItems();
				for (Heir heir : getHeirs()) {
					dropDown2.addItem("ID: " + heir.ID + " - Name:  " + heir.TitleEnd);
				}
			}
		});

		delete.add(deleteButton);
		tabbedPane.addTab("Delete", delete);

		panel.add(tabbedPane);
		return panel;
	}

	public JComponent getScrollableTable() {
		String[] columnNames = { "ID", "PID", "KID", "TitleStart", "TitleEnd", "ShortTitle" };
		ArrayList<Heir> heirs = getHeirs();
		Object[][] data = new Object[heirs.size()][5];
		for (int i = 0; i < heirs.size(); i++) {
			Heir k = heirs.get(i);
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
					return String.class;
				case 4:
					return String.class;
				case 5:
					return String.class;
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

	public boolean addHeir(Heir k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Insert_Heir(?, ?, ?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, k.PID);
			cs.setInt(3, k.KID);
			cs.setString(4, k.TitleStart);
			cs.setString(5, k.TitleEnd);
			cs.setString(6, k.ShortTitle);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a Person ID");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a Kingdom ID");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "The Person ID " + k.PID + " does not exist");
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "The Kingdom ID " + k.KID + " does not exist");
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

	public boolean updateHeir(Heir k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Update_Heir(?, ?, ?, ?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, k.ID);
			cs.setInt(3, k.PID);
			cs.setInt(4, k.KID);
			cs.setString(5, k.TitleStart);
			cs.setString(6, k.TitleEnd);
			cs.setString(7, k.ShortTitle);
			cs.execute();

			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a valid ID");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a valid Person ID");
				break;
			case 3:
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

	public boolean deleteHeir(int ID) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.Delete_Heir(?) }");
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

	public ArrayList<String> getHeirNames() {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> sodas = new ArrayList<String>();
		try {
			stmt = dbService.getConnection().createStatement();
			stmt.execute("select distinct name from Heir");
			rs = stmt.getResultSet();
			while (rs.next()) {
				sodas.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sodas;
	}

	public ArrayList<Heir> getHeirs() {
		try {
			String query = "SELECT ID, PID, KID, TitleStart, TitleEnd, ShortTitle \nFROM Heir\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve heirs.");
			ex.printStackTrace();
			return new ArrayList<Heir>();
		}

	}

	private ArrayList<Heir> parseResults(ResultSet rs) {
		try {
			ArrayList<Heir> heirs = new ArrayList<Heir>();
			while (rs.next()) {
				Heir heir = new Heir();
				heir.ID = rs.getInt("ID");
				heir.PID = rs.getInt("PID");
				heir.KID = rs.getInt("KID");
				heir.TitleStart = rs.getString("TitleStart");
				heir.TitleEnd = rs.getString("TitleEnd");
				heir.ShortTitle = rs.getString("ShortTitle");

				heirs.add(heir);
			}
			return heirs;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "An error ocurred while retrieving heirs. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<Heir>();
		}

	}

}
