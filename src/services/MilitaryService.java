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

import tables.Military;

public class MilitaryService implements Services {
	private DatabaseConnectionService dbService = null;
	private JComponent view;

	public MilitaryService(DatabaseConnectionService dbService) {
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

		JLabel insertNameLabel = new JLabel("Name: ");
		insert.add(insertNameLabel);
		JTextField insertNameText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertNameText);

		JLabel insertBudgetLabel = new JLabel("Budget: ");
		insert.add(insertBudgetLabel);
		JTextField insertBudgetText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertBudgetText);

		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Military k = new Military();
				try {
					k.KID = Integer.parseInt(insertKIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.Budget = Integer.parseInt(insertBudgetText.getText());
				} catch (NumberFormatException e) {

				}
				k.Name = insertNameText.getText();
				addMilitary(k);

				insertKIDText.setText("");
				insertNameText.setText("");
				insertBudgetText.setText("");

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

		JLabel updateNameLabel = new JLabel("Name: ");
		update.add(updateNameLabel);
		JTextField updateNameText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateNameText);

		JLabel updateBudgetLabel = new JLabel("Budget: ");
		update.add(updateBudgetLabel);
		JTextField updateBudgetText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateBudgetText);

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
					List<Military> militarys = getMilitarys();
					Military k = null;
					for (Military military : militarys) {
						if (military.ID == ID)
							k = military;
					}
					if (k != null) {
						Long budget = k.Budget;
						updateKIDText.setText(Integer.toString(k.KID));
						updateNameText.setText(k.Name);
						updateBudgetText.setText(budget.toString());
					} else {
						insertKIDText.setText("");
						insertNameText.setText("");
						insertBudgetText.setText("");
					}
				} catch (NumberFormatException e) {
					insertKIDText.setText("");
					insertNameText.setText("");
					insertBudgetText.setText("");
				}
			}
		});

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Military k = new Military();
				try {
					k.ID = Integer.parseInt(updateIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.Budget = Integer.parseInt(updateBudgetText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					k.KID = Integer.parseInt(updateKIDText.getText());
				} catch (NumberFormatException e) {

				}
				k.Name = updateNameText.getText();
				updateMilitary(k);

				insertKIDText.setText("");
				insertNameText.setText("");
				insertBudgetText.setText("");

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
					deleteMilitary(id);
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
		String[] columnNames = { "ID", "KID", "Name", "Budget" };
		ArrayList<Military> militarys = getMilitarys();
		Object[][] data = new Object[militarys.size()][5];
		for (int i = 0; i < militarys.size(); i++) {
			Military k = militarys.get(i);
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
					return String.class;
				case 3:
					return Long.class;
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

	public boolean addMilitary(Military k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Insert_Military(?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, k.KID);
			cs.setString(3, k.Name);
			cs.setLong(4, k.Budget);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a Kingdom ID");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a name");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "Please provide a budget");
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

	public boolean updateMilitary(Military k) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Update_Military(?, ?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, k.ID);
			cs.setInt(3, k.KID);
			cs.setString(4, k.Name);
			cs.setLong(5, k.Budget);
			cs.execute();

			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a valid ID");
				break;
			case 2:
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

	public boolean deleteMilitary(int ID) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.Delete_Military(?) }");
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

	public ArrayList<String> getMilitaryNames() {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> sodas = new ArrayList<String>();
		try {
			stmt = dbService.getConnection().createStatement();
			stmt.execute("select distinct name from Military");
			rs = stmt.getResultSet();
			while (rs.next()) {
				sodas.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sodas;
	}

	public ArrayList<Military> getMilitarys() {
		try {
			String query = "SELECT ID, KID, Name, Budget \nFROM Military\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve militarys.");
			ex.printStackTrace();
			return new ArrayList<Military>();
		}

	}

	private ArrayList<Military> parseResults(ResultSet rs) {
		try {
			ArrayList<Military> militarys = new ArrayList<Military>();
			while (rs.next()) {
				Military military = new Military();
				military.ID = rs.getInt("ID");
				military.KID = rs.getInt("KID");
				military.Name = rs.getString("Name");
				military.Budget = rs.getLong("Budget");

				militarys.add(military);
			}
			return militarys;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving militarys. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<Military>();
		}

	}

}
