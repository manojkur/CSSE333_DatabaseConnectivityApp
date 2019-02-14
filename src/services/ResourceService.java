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

import tables.Resource;

public class ResourceService implements Services {
	private DatabaseConnectionService dbService = null;
	private JComponent view;
	private boolean isOwner;

	public ResourceService(DatabaseConnectionService dbService, boolean isOwner) {
		this.dbService = dbService;
		this.isOwner = isOwner;
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
		if (this.isOwner) {
			JPanel insert = new JPanel();
			insert.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			insert.setLayout(new BoxLayout(insert, BoxLayout.Y_AXIS));

			JLabel insertNameLabel = new JLabel("Name: ");
			insert.add(insertNameLabel);
			JTextField insertNameText = (new JTextField() {
				public JTextField setMaxSize(Dimension d) {
					setMaximumSize(d);
					return this;
				}
			}).setMaxSize(new Dimension(width, height));
			insert.add(insertNameText);

			JButton insertButton = new JButton("Insert");
			insertButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					Resource k = new Resource();
					k.Name = insertNameText.getText();
					addResource(k);

					insertNameText.setText("");

					tabbedPane.remove(view);
					view = getScrollableTable();
					tabbedPane.insertTab("View", null, view, "View", 0);
					dropDown.removeAllItems();
					for (Resource resource : getResources()) {
						dropDown.addItem("ID: " + resource.ID + " - Name:  " + resource.Name);
					}
					dropDown2.removeAllItems();
					for (Resource resource : getResources()) {
						dropDown2.addItem("ID: " + resource.ID + " - Name:  " + resource.Name);
					}

				}
			});

			insert.add(insertButton);
			tabbedPane.addTab("Insert", insert);

			JPanel update = new JPanel();
			update.setLayout(new BoxLayout(update, BoxLayout.Y_AXIS));
			update.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			for (Resource resource : getResources()) {
				dropDown.addItem("ID: " + resource.ID + " - Name:  " + resource.Name);
			}
			JPanel innerPanel = new JPanel(new FlowLayout());
			innerPanel.setMaximumSize(new Dimension(width, height + 20));
			innerPanel.add(dropDown);
			update.add(innerPanel);

			JLabel updateNameLabel = new JLabel("Name: ");
			update.add(updateNameLabel);
			JTextField updateNameText = (new JTextField() {
				public JTextField setMaxSize(Dimension d) {
					setMaximumSize(d);
					return this;
				}
			}).setMaxSize(new Dimension(width, height));
			update.add(updateNameText);

			dropDown.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						String id = dropDown.getSelectedItem().toString().split("-")[0].split(" ")[1];
						Resource resource = null;
						for (Resource k : getResources()) {
							if (Integer.toString(k.ID).equals(id)) {
								resource = k;
								break;
							}
						}
						updateNameText.setText(resource.Name);
					} catch (Exception e1) {
						updateNameText.setText("");
					}
				}
			});

			JButton updateButton = new JButton("Update");
			updateButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					Resource k = new Resource();
					k.ID = Integer.parseInt(dropDown.getSelectedItem().toString().split("-")[0].split(" ")[1]);
					k.Name = updateNameText.getText();
					updateResource(k);

					updateNameText.setText("");

					tabbedPane.remove(view);
					view = getScrollableTable();
					tabbedPane.insertTab("View", null, view, "View", 0);
					dropDown.removeAllItems();
					for (Resource resource : getResources()) {
						dropDown.addItem("ID: " + resource.ID + " - Name:  " + resource.Name);
					}
					dropDown2.removeAllItems();
					for (Resource resource : getResources()) {
						dropDown2.addItem("ID: " + resource.ID + " - Name:  " + resource.Name);
					}
				}
			});

			update.add(updateButton);
			tabbedPane.addTab("Update", update);

			JPanel delete = new JPanel();
			delete.setLayout(new BoxLayout(delete, BoxLayout.Y_AXIS));
			delete.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			for (Resource resource : getResources()) {
				dropDown2.addItem("ID: " + resource.ID + " - Name:  " + resource.Name);
			}
			JPanel innerPanel2 = new JPanel(new FlowLayout());
			innerPanel2.setMaximumSize(new Dimension(width, height + 20));
			innerPanel2.add(dropDown2);
			delete.add(innerPanel2);

			JButton deleteButton = new JButton("Delete");
			deleteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {

					int id = Integer.parseInt(dropDown2.getSelectedItem().toString().split("-")[0].split(" ")[1]);
					deleteResource(id);

					tabbedPane.remove(view);
					view = getScrollableTable();
					tabbedPane.insertTab("View", null, view, "View", 0);
					dropDown.removeAllItems();
					for (Resource resource : getResources()) {
						dropDown.addItem("ID: " + resource.ID + " - Name:  " + resource.Name);
					}
					dropDown2.removeAllItems();
					for (Resource resource : getResources()) {
						dropDown2.addItem("ID: " + resource.ID + " - Name:  " + resource.Name);
					}
				}
			});

			delete.add(deleteButton);
			tabbedPane.addTab("Delete", delete);
		}
		panel.add(tabbedPane);
		return panel;
	}

	public JComponent getScrollableTable() {
		String[] columnNames = { "ID", "Name" };
		ArrayList<Resource> resources = getResources();
		Object[][] data = new Object[resources.size()][5];
		for (int i = 0; i < resources.size(); i++) {
			Resource k = resources.get(i);
			data[i] = k.getRow();
		}
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class;
				case 1:
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

	public boolean addResource(Resource k) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.Insert_Resource(?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, k.Name);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a name");
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

	public boolean updateResource(Resource k) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.Update_Resource(?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, k.ID);
			cs.setString(3, k.Name);
			cs.execute();

			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a valid ID");
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

	public boolean deleteResource(int ID) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.Delete_Resource(?) }");
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

	public ArrayList<String> getResourceNames() {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> sodas = new ArrayList<String>();
		try {
			stmt = dbService.getConnection().createStatement();
			stmt.execute("select distinct name from Resource");
			rs = stmt.getResultSet();
			while (rs.next()) {
				sodas.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sodas;
	}

	public ArrayList<Resource> getResources() {
		try {
			String query = "SELECT ID, Name \nFROM Resource\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			System.out.println(rs.toString());
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve Resources.");
			ex.printStackTrace();
			return new ArrayList<Resource>();
		}

	}

	private ArrayList<Resource> parseResults(ResultSet rs) {
		try {
			ArrayList<Resource> resources = new ArrayList<Resource>();
			while (rs.next()) {
				Resource resource = new Resource();
				resource.ID = rs.getInt("ID");
				resource.Name = rs.getString("Name");

				resources.add(resource);
			}
			return resources;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving resources. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<Resource>();
		}

	}

}
