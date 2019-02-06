package services;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import tables.ConqueredUsing;

public class ConqueredUsingService implements Services {
	private DatabaseConnectionService dbService = null;
	private JComponent view;

	public ConqueredUsingService(DatabaseConnectionService dbService) {
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

		JLabel insertCMIDLabel = new JLabel("CMID: ");
		insert.add(insertCMIDLabel);
		JTextField insertCMIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertCMIDText);

		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ConqueredUsing p = new ConqueredUsing();
				try {
					p.KID = Integer.parseInt(insertKIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					p.CMID = Integer.parseInt(insertCMIDText.getText());
				} catch (NumberFormatException e) {

				}

				addConqueredUsing(p);

				insertKIDText.setText("");
				insertCMIDText.setText("");

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

		JLabel updateCMIDLabel = new JLabel("CMID: ");
		update.add(updateCMIDLabel);
		JTextField updateCMIDText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateCMIDText);

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
					List<ConqueredUsing> conqueredUsings = getConqueredUsings();
					ConqueredUsing p = null;
					for (ConqueredUsing conqueredUsing : conqueredUsings) {
						if (conqueredUsing.ID == ID)
							p = conqueredUsing;
					}
					if (p != null) {
						Integer KID = p.KID;
						Integer CMID = p.CMID;

						updateKIDText.setText(KID.toString());
						updateCMIDText.setText(CMID.toString());
					} else {
						updateKIDText.setText("");
						updateCMIDText.setText("");
					}
				} catch (NumberFormatException e) {
					updateKIDText.setText("");
					updateCMIDText.setText("");
				}
			}
		});

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ConqueredUsing p = new ConqueredUsing();
				try {
					p.ID = Integer.parseInt(updateIDText.getText());
				} catch (NumberFormatException e) {

				}

				try {
					p.KID = Integer.parseInt(updateKIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					p.CMID = Integer.parseInt(updateCMIDText.getText());
				} catch (NumberFormatException e) {

				}

				updateConqueredUsing(p);

				updateIDText.setText("");
				updateKIDText.setText("");
				updateCMIDText.setText("");

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
					deleteConqueredUsing(id);
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
		String[] columnNames = { "ID", "KID", "CMID" };
		ArrayList<ConqueredUsing> conqueredUsings = getConqueredUsings();
		Object[][] data = new Object[conqueredUsings.size()][5];
		for (int i = 0; i < conqueredUsings.size(); i++) {
			ConqueredUsing k = conqueredUsings.get(i);
			data[i] = k.getRow();
		}
		JTable table = new JTable(data, columnNames);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);

		return scrollPane;
	}

	public boolean addConqueredUsing(ConqueredUsing p) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Insert_ConqueredUsing(?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, p.KID);
			cs.setInt(3, p.CMID);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a Kingdom ID");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "Please provide a conquer method ID");
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "Please provide a city ID");
				break;
			case 5:
				JOptionPane.showMessageDialog(null, "Please provide a conquer Method ID");
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

	public boolean updateConqueredUsing(ConqueredUsing p) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Update_ConqueredUsing(?,?,?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, p.ID);
			cs.setInt(3, p.KID);
			cs.setInt(4, p.CMID);

			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "The ID " + p.ID + " does not exist");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "The city ID doesn't exist");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "The conquer method ID doesn't exist");
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

	public boolean deleteConqueredUsing(int ID) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Delete_ConqueredUsing(?) }");
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

	public ArrayList<ConqueredUsing> getConqueredUsings() {
		try {
			String query = "SELECT ID, KID, CMID \nFROM ConqueredUsing\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve conqueredUsings.");
			ex.printStackTrace();
			return new ArrayList<ConqueredUsing>();
		}

	}

	private ArrayList<ConqueredUsing> parseResults(ResultSet rs) {
		try {
			ArrayList<ConqueredUsing> conqueredUsings = new ArrayList<ConqueredUsing>();
			while (rs.next()) {
				ConqueredUsing conqueredUsing = new ConqueredUsing();
				conqueredUsing.ID = rs.getInt("ID");
				conqueredUsing.KID = rs.getInt("KID");
				conqueredUsing.CMID = rs.getInt("CMID");

				conqueredUsings.add(conqueredUsing);
			}
			return conqueredUsings;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving conqueredUsings. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<ConqueredUsing>();
		}

	}
}