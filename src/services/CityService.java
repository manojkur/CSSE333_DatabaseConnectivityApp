package services;

import java.awt.CardLayout;
import java.awt.Dimension;
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

import tables.City;

public class CityService implements Services {
	private DatabaseConnectionService dbService = null;
	private JComponent view;

	public CityService(DatabaseConnectionService dbService) {
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
		JLabel insertKIDLabel = new JLabel("KID: ");
		insert.add(insertKIDLabel);
		JTextField insertKIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertKIDText);

		JLabel insertTIDLabel = new JLabel("TID: ");
		insert.add(insertTIDLabel);
		JTextField insertTIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertTIDText);

		JLabel insertNameLabel = new JLabel("Name: ");
		insert.add(insertNameLabel);
		JTextField insertNameText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertNameText);

		JLabel insertPopulationLabel = new JLabel("Population: ");
		insert.add(insertPopulationLabel);
		JTextField insertPopulationText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertPopulationText);

		JLabel insertCoordinatesLabel = new JLabel("Coordinates: ");
		insert.add(insertCoordinatesLabel);
		JTextField insertCoordinatesText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertCoordinatesText);

		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				City k = new City();
				try {
					k.KID = Integer.parseInt(insertKIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.TID = Integer.parseInt(insertTIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.Population = Integer.parseInt(insertPopulationText.getText());
				} catch (NumberFormatException e) {

				}
				k.Coordinates = insertCoordinatesText.getText();
				k.Name = insertNameText.getText();
				addCity(k);

				insertKIDText.setText("");
				insertTIDText.setText("");
				insertPopulationText.setText("");
				insertNameText.setText("");
				insertCoordinatesText.setText("");

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

		JLabel updateIDLabel = new JLabel("ID: ");
		update.add(updateIDLabel);
		JTextField updateIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateIDText);

		JLabel updateKIDLabel = new JLabel("KID: ");
		update.add(updateKIDLabel);
		JTextField updateKIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateKIDText);

		JLabel updateTIDLabel = new JLabel("TID: ");
		update.add(updateTIDLabel);
		JTextField updateTIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateTIDText);

		JLabel updateNameLabel = new JLabel("Name: ");
		update.add(updateNameLabel);
		JTextField updateNameText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateNameText);

		JLabel updatePopulationLabel = new JLabel("Population: ");
		update.add(updatePopulationLabel);
		JTextField updatePopulationText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updatePopulationText);

		JLabel updateCoordinatesLabel = new JLabel("Coordinates: ");
		update.add(updateCoordinatesLabel);
		JTextField updateCoordinatesText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateCoordinatesText);

		updateIDText.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				modifyText();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				modifyText();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				modifyText();
			}

			private void modifyText() {
				try {
					int ID = Integer.parseInt(updateIDText.getText());
					List<City> citys = getCitys();
					City k = null;
					for (City city : citys) {
						if (city.ID == ID)
							k = city;
					}
					if (k != null) {
						updateTIDText.setText(Integer.toString(k.TID));
						updateKIDText.setText(Integer.toString(k.KID));
						updateNameText.setText(k.Name);
						updateCoordinatesText.setText(k.Coordinates);
						updatePopulationText.setText(Integer.toString(k.Population));
					} else {
						updateTIDText.setText("");
						updateKIDText.setText("");
						updateNameText.setText("");
						updateCoordinatesText.setText("");
						updatePopulationText.setText("");
					}
				} catch (NumberFormatException e) {
					updateTIDText.setText("");
					updateKIDText.setText("");
					updateNameText.setText("");
					updateCoordinatesText.setText("");
					updatePopulationText.setText("");
				}
			}
		});

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				City k = new City();
				try {
					k.ID = Integer.parseInt(updateIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.TID = Integer.parseInt(updateTIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.KID = Integer.parseInt(updateKIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.Population = Integer.parseInt(updatePopulationText.getText());
				} catch (NumberFormatException e) {

				}
				k.Name = updateNameText.getText();
				k.Coordinates = updateCoordinatesText.getText();
				updateCity(k);

				updateTIDText.setText("");
				updateKIDText.setText("");
				updateNameText.setText("");
				updateCoordinatesText.setText("");
				updatePopulationText.setText("");

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
					deleteCity(id);
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
		String[] columnNames = { "ID", "KID", "TID", "Name", "Population", "Coordinates" };
		ArrayList<City> citys = getCitys();
		Object[][] data = new Object[citys.size()][5];
		for (int i = 0; i < citys.size(); i++) {
			City k = citys.get(i);
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
					return Integer.class;
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

	public boolean addCity(City k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Insert_City(?, ?, ?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, k.KID);
			cs.setInt(3, k.TID);
			cs.setString(4, k.Name);
			cs.setString(5, k.Coordinates);
			cs.setInt(6, k.Population);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a name");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a Coordinates");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "Please provide a Kingdom ID");
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "Please provide a Terrain ID");
				break;
			case 5:
				JOptionPane.showMessageDialog(null, "Please provide a population greater than or equal to 10");
				break;
			case 6:
				JOptionPane.showMessageDialog(null, "The kingdom ID " + k.KID + " does not exist");
				break;
			case 7:
				JOptionPane.showMessageDialog(null, "The terrain ID " + k.TID + " does not exist");
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

	public boolean updateCity(City k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Update_City(?, ?, ?, ?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, k.ID);
			cs.setInt(3, k.KID);
			cs.setInt(4, k.TID);
			cs.setString(5, k.Name);
			cs.setString(6, k.Coordinates);
			cs.setInt(7, k.Population);
			cs.execute();

			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a valid ID");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a valid Kingdom ID");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "Please provide a valid Terrain ID");
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "Please provide a population greater than or equalt to 10");
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

	public boolean deleteCity(int ID) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.Delete_City(?) }");
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

	public ArrayList<String> getCityNames() {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> sodas = new ArrayList<String>();
		try {
			stmt = dbService.getConnection().createStatement();
			stmt.execute("select distinct name from City");
			rs = stmt.getResultSet();
			while (rs.next()) {
				sodas.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sodas;
	}

	public ArrayList<City> getCitys() {
		try {
			String query = "SELECT ID, KID, TID, Name, Population, Coordinates \nFROM City\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve cities.");
			ex.printStackTrace();
			return new ArrayList<City>();
		}

	}

	private ArrayList<City> parseResults(ResultSet rs) {
		try {
			ArrayList<City> citys = new ArrayList<City>();
			while (rs.next()) {
				City city = new City();
				city.ID = rs.getInt("ID");
				city.KID = rs.getInt("KID");
				city.TID = rs.getInt("TID");
				city.Name = rs.getString("Name");
				city.Population = rs.getInt("Population");
				city.Coordinates = rs.getString("Coordinates");

				citys.add(city);
			}
			return citys;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "An error ocurred while retrieving citys. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<City>();
		}

	}

}
