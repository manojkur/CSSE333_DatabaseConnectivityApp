package services;

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
import java.util.Calendar;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import tables.Kingdom;
import tables.Terrain;

public class TerrainService implements Services {
	private DatabaseConnectionService dbService = null;
	private JComponent view;

	public TerrainService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public JPanel getJPanel() {
		JPanel panel = new JPanel(new CardLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		view = getScrollableTable();
		tabbedPane.addTab("View", view);

		int width = 500;
		int height = 20;

		JPanel insert = new JPanel();
		insert.setLayout(new BoxLayout(insert, BoxLayout.Y_AXIS));
		insert.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel insertNameLabel = new JLabel("Name: ");
		insert.add(insertNameLabel);
		JTextField insertNameText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertNameText);

		JLabel insertTraverseDifficultyLabel = new JLabel(
				"TraverseDifficulty (VERY HIGH, HIGH, MEDIUM, LOW, VERY LOW): ");
		insert.add(insertTraverseDifficultyLabel);
		JTextField insertTraverseDifficultyText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertTraverseDifficultyText);

		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Terrain k = new Terrain();
				k.TraverseDifficulty = insertTraverseDifficultyText.getText();
				k.Name = insertNameText.getText();
				addTerrain(k);

				insertNameText.setText("");
				insertTraverseDifficultyText.setText("");

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
			}
		});

		insert.add(insertButton);
		tabbedPane.addTab("Insert", insert);

		JPanel update = new JPanel();
		update.setLayout(new BoxLayout(update, BoxLayout.Y_AXIS));
		update.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JComboBox<String> dropDown = new JComboBox<>();
		List<Terrain> terrains = getTerrains();
		for (Terrain terrain : terrains) {
			dropDown.addItem("ID: " + terrain.ID + " - Name:  " + terrain.Name);
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

		JLabel updateTraverseDifficultyLabel = new JLabel("TraverseDifficulty: ");
		update.add(updateTraverseDifficultyLabel);
		JTextField updateTraverseDifficultyText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateTraverseDifficultyText);

		dropDown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String id = dropDown.getSelectedItem().toString().split("-")[0].split(" ")[1];
				Terrain terrain = null;
				for (Terrain k : terrains) {
					if (Integer.toString(k.ID).equals(id)) {
						terrain = k;
						break;
					}
				}
				updateNameText.setText(terrain.Name);
				updateTraverseDifficultyText.setText(terrain.TraverseDifficulty);
			}
		});

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Terrain k = new Terrain();
				k.ID = Integer.parseInt(dropDown.getSelectedItem().toString().split("-")[0].split(" ")[1]);
				k.Name = updateNameText.getText();
				k.TraverseDifficulty = updateTraverseDifficultyText.getText();
				updateTerrain(k);

				updateNameText.setText("");
				updateTraverseDifficultyText.setText("");

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
			}
		});

		update.add(updateButton);
		tabbedPane.addTab("Update", update);

		JPanel delete = new JPanel();
		delete.setLayout(new BoxLayout(delete, BoxLayout.Y_AXIS));
		delete.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel deleteIDLabel = new JLabel("ID: ");
		delete.add(deleteIDLabel);
		JTextField deleteIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		delete.add(deleteIDText);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					int id = Integer.parseInt(deleteIDText.getText());
					deleteTerrain(id);
				} catch (NumberFormatException e) {

				}

				deleteIDText.setText("");

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
			}
		});

		delete.add(deleteButton);
		tabbedPane.addTab("Delete", delete);

		panel.add(tabbedPane);
		return panel;
	}

	public JComponent getScrollableTable() {
		String[] columnNames = { "ID", "Name", "TraverseDifficulty" };
		ArrayList<Terrain> terrains = getTerrains();
		Object[][] data = new Object[terrains.size()][5];
		for (int i = 0; i < terrains.size(); i++) {
			Terrain k = terrains.get(i);
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
				case 2:
					return String.class;
				default:
					return String.class;
				}
			}
		};
		JTable table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);

		return scrollPane;
	}

	public boolean addTerrain(Terrain k) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.Insert_Terrain(?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, k.Name);
			cs.setString(3, k.TraverseDifficulty);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a name");
				break;
			case 2:
				JOptionPane.showMessageDialog(null,
						"Please provide a TraverseDifficulty from the following: VERY HIGH, HIGH, MEDIUM, LOW, VERY LOW");
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

	public boolean updateTerrain(Terrain k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Update_Terrain(?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			System.out.println(k.ID);
			cs.setInt(2, k.ID);
			cs.setString(3, k.Name);
			cs.setString(4, k.TraverseDifficulty);
			cs.execute();
			
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a valid ID");
				break;
			case 2:
				JOptionPane.showMessageDialog(null,
						"The terrain name must be unique, non-null and only contain letters, dashes, apostrophes, and spaces");
				break;
			case 3:
				JOptionPane.showMessageDialog(null,
						"Please provide a TraverseDifficulty from the following: VERY HIGH, HIGH, MEDIUM, LOW, VERY LOW");
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

	public boolean deleteTerrain(int ID) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.Delete_Terrain(?) }");
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

	public ArrayList<String> getTerrainNames() {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> sodas = new ArrayList<String>();
		try {
			stmt = dbService.getConnection().createStatement();
			stmt.execute("select distinct name from Terrain");
			rs = stmt.getResultSet();
			while (rs.next()) {
				sodas.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sodas;
	}

	public ArrayList<Terrain> getTerrains() {
		try {
			String query = "SELECT ID, Name, TraverseDifficulty \nFROM Terrain\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve cities.");
			ex.printStackTrace();
			return new ArrayList<Terrain>();
		}

	}

	private ArrayList<Terrain> parseResults(ResultSet rs) {
		try {
			ArrayList<Terrain> terrains = new ArrayList<Terrain>();
			while (rs.next()) {
				Terrain terrain = new Terrain();
				terrain.ID = rs.getInt("ID");
				terrain.Name = rs.getString("Name");
				terrain.TraverseDifficulty = rs.getString("TraverseDifficulty");

				terrains.add(terrain);
			}
			return terrains;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "An error ocurred while retrieving terrains. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<Terrain>();
		}

	}

}
