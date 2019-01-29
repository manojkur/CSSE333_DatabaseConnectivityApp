package services;

import java.awt.CardLayout;
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

import tables.Heir;
import tables.Heir;

public class HeirService {
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

		JPanel insert = new JPanel();
		insert.setLayout(new BoxLayout(insert, BoxLayout.Y_AXIS));

		JLabel insertPIDLabel = new JLabel("PID: ");
		insert.add(insertPIDLabel);
		JTextField insertPIDText = new JTextField();
		insert.add(insertPIDText);

		JLabel insertKIDLabel = new JLabel("KID: ");
		insert.add(insertKIDLabel);
		JTextField insertKIDText = new JTextField();
		insert.add(insertKIDText);

		JLabel insertTitleStartLabel = new JLabel("TitleStart: ");
		insert.add(insertTitleStartLabel);
		JTextField insertTitleStartText = new JTextField();
		insert.add(insertTitleStartText);

		JLabel insertTitleEndLabel = new JLabel("TitleEnd: ");
		insert.add(insertTitleEndLabel);
		JTextField insertTitleEndText = new JTextField();
		insert.add(insertTitleEndText);

		JLabel insertShortTitleLabel = new JLabel("ShortTitle: ");
		insert.add(insertShortTitleLabel);
		JTextField insertShortTitleText = new JTextField();
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
				k.ShortTitle= insertShortTitleText.getText();
				addHeir(k);

				insertPIDText.setText("");
				insertKIDText.setText("");
				insertTitleEndText.setText("");
				insertTitleStartText.setText("");
				insertShortTitleText.setText("");

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
			}
		});

		insert.add(insertButton);
		tabbedPane.addTab("Insert", insert);

		JPanel update = new JPanel();
		update.setLayout(new BoxLayout(update, BoxLayout.Y_AXIS));
		JLabel updateIDLabel = new JLabel("ID: ");
		update.add(updateIDLabel);
		JTextField updateIDText = new JTextField();
		update.add(updateIDText);

		JLabel updatePIDLabel = new JLabel("PID: ");
		update.add(updatePIDLabel);
		JTextField updatePIDText = new JTextField();
		update.add(updatePIDText);

		JLabel updateKIDLabel = new JLabel("KID: ");
		update.add(updateKIDLabel);
		JTextField updateKIDText = new JTextField();
		update.add(updateKIDText);

		JLabel updateTitleStartLabel = new JLabel("TitleStart: ");
		update.add(updateTitleStartLabel);
		JTextField updateTitleStartText = new JTextField();
		update.add(updateTitleStartText);

		JLabel updateTitleEndLabel = new JLabel("TitleEnd: ");
		update.add(updateTitleEndLabel);
		JTextField updateTitleEndText = new JTextField();
		update.add(updateTitleEndText);

		JLabel updateShortTitleLabel = new JLabel("ShortTitle: ");
		update.add(updateShortTitleLabel);
		JTextField updateShortTitleText = new JTextField();
		update.add(updateShortTitleText);

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
					List<Heir> heirs = getHeirs();
					Heir k = null;
					for (Heir heir : heirs) {
						if (heir.ID == ID)
							k = heir;
					}
					if (k != null) {
						updatePIDText.setText(Integer.toString(k.PID));
						updateKIDText.setText(Integer.toString(k.KID));
						updateTitleStartText.setText(k.TitleStart);
						updateTitleEndText.setText(k.TitleEnd);
						updateShortTitleText.setText(k.ShortTitle);
					} else {
						updatePIDText.setText("");
						updateKIDText.setText("");
						updateTitleStartText.setText("");
						updateTitleEndText.setText("");
						updateShortTitleText.setText("");
					}
				} catch (NumberFormatException e) {
					updatePIDText.setText("");
					updateKIDText.setText("");
					updateTitleStartText.setText("");
					updateTitleEndText.setText("");
					updateShortTitleText.setText("");
				}
			}
		});

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Heir k = new Heir();
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
				k.ShortTitle= updateShortTitleText.getText();
				addHeir(k);

				updatePIDText.setText("");
				updateKIDText.setText("");
				updateTitleEndText.setText("");
				updateTitleStartText.setText("");
				updateShortTitleText.setText("");

				tabbedPane.remove(view);
				view = getScrollableTable();
				tabbedPane.insertTab("View", null, view, "View", 0);
			}
		});

		update.add(updateButton);
		tabbedPane.addTab("Update", update);

		JPanel delete = new JPanel();
		delete.setLayout(new BoxLayout(delete, BoxLayout.Y_AXIS));

		JLabel deleteIDLabel = new JLabel("ID: ");
		delete.add(deleteIDLabel);
		JTextField deleteIDText = new JTextField();
		delete.add(deleteIDText);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					int id = Integer.parseInt(deleteIDText.getText());
					deleteHeir(id);
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
		String[] columnNames = { "ID","PID","KID","TitleStart","TitleEnd","ShortTitle"};
		ArrayList<Heir> heirs = getHeirs();
		Object[][] data = new Object[heirs.size()][5];
		for (int i = 0; i < heirs.size(); i++) {
			Heir k = heirs.get(i);
			data[i] = k.getRow();
		}
		JTable table = new JTable(data, columnNames);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);

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
				JOptionPane.showMessageDialog(null, "Please provide a Name");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Please provide a ShortName");
				break;
			case 4:
				JOptionPane.showMessageDialog(null, "Please provide a GDP");
				break;
			case 5:
				JOptionPane.showMessageDialog(null, "Please provide a Date that has happened");
				break;
			case 6:
				JOptionPane.showMessageDialog(null, "Please provide the Heir Type");
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
				JOptionPane.showMessageDialog(null, "Please provide a valid id");
				break;
			case 5:
				JOptionPane.showMessageDialog(null, "Please provide a Date that has happened");
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
