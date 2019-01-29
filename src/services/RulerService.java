package services;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
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

import tables.Ruler;

public class RulerService {
	private DatabaseConnectionService dbService = null;
	private JComponent view;

	public RulerService(DatabaseConnectionService dbService) {
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

		JLabel insertHIDLabel = new JLabel("HID: ");
		insert.add(insertHIDLabel);
		JTextField insertHIDText = new JTextField();
		insert.add(insertHIDText);

		JLabel insertYearsOfExperienceLabel = new JLabel("YearsOfExperience: ");
		insert.add(insertYearsOfExperienceLabel);
		JTextField insertYearsOfExperienceText = new JTextField();
		insert.add(insertYearsOfExperienceText);

		JLabel insertTitleLabel = new JLabel("Title: ");
		insert.add(insertTitleLabel);
		JTextField insertTitleText = new JTextField();
		insert.add(insertTitleText);

		JLabel insertDynastyLabel = new JLabel("Dynasty: ");
		insert.add(insertDynastyLabel);
		JTextField insertDynastyText = new JTextField();
		insert.add(insertDynastyText);

		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Ruler p = new Ruler();
				try {
					p.PID = Integer.parseInt(insertPIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					p.KID = Integer.parseInt(insertKIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					p.HID = Integer.parseInt(insertHIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					p.YearsOfExperience = Integer.parseInt(insertYearsOfExperienceText.getText());
				} catch (NumberFormatException e) {

				}

				p.Title = insertTitleText.getText();
				p.Dynasty = insertDynastyText.getText();

				addRuler(p);

				insertPIDText.setText("");
				insertKIDText.setText("");
				insertHIDText.setText("");
				insertYearsOfExperienceText.setText("");
				insertTitleText.setText("");
				insertDynastyText.setText("");

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

		JLabel updateHIDLabel = new JLabel("HID: ");
		update.add(updateHIDLabel);
		JTextField updateHIDText = new JTextField();
		update.add(updateHIDText);

		JLabel updateYearsOfExperienceLabel = new JLabel("YearsOfExperience: ");
		update.add(updateYearsOfExperienceLabel);
		JTextField updateYearsOfExperienceText = new JTextField();
		update.add(updateYearsOfExperienceText);

		JLabel updateTitleLabel = new JLabel("Title: ");
		update.add(updateTitleLabel);
		JTextField updateTitleText = new JTextField();
		update.add(updateTitleText);

		JLabel updateDynastyLabel = new JLabel("Dynasty: ");
		update.add(updateDynastyLabel);
		JTextField updateDynastyText = new JTextField();
		update.add(updateDynastyText);

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
					List<Ruler> rulers = getRulers();
					Ruler p = null;
					for (Ruler ruler : rulers) {
						if (ruler.ID == ID)
							p = ruler;
					}
					if (p != null) {
						Integer PID = p.PID;
						Integer KID = p.KID;
						Integer HID = p.HID;
						Integer YearsOfExperience = p.YearsOfExperience;

						updatePIDText.setText(PID.toString());
						updateKIDText.setText(KID.toString());
						updateHIDText.setText(HID.toString());
						updateYearsOfExperienceText.setText(YearsOfExperience.toString());
						updateTitleText.setText(p.Title);
						updateDynastyText.setText(p.Dynasty);
					} else {
						updatePIDText.setText("");
						updateKIDText.setText("");
						updateHIDText.setText("");
						updateYearsOfExperienceText.setText("");
						updateTitleText.setText("");
						updateDynastyText.setText("");
					}
				} catch (NumberFormatException e) {
					updatePIDText.setText("");
					updateKIDText.setText("");
					updateHIDText.setText("");
					updateYearsOfExperienceText.setText("");
					updateTitleText.setText("");
					updateDynastyText.setText("");
				}
			}
		});

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Ruler p = new Ruler();
				try {
					p.ID = Integer.parseInt(updateIDText.getText());
				} catch (NumberFormatException e) {

				}

				try {
					p.PID = Integer.parseInt(updatePIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					p.KID = Integer.parseInt(updateKIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					p.HID = Integer.parseInt(updateHIDText.getText());
				} catch (NumberFormatException e) {

				}
				try {
					p.YearsOfExperience = Integer.parseInt(updateYearsOfExperienceText.getText());
				} catch (NumberFormatException e) {

				}

				p.Title = updateTitleText.getText();
				p.Dynasty = updateDynastyText.getText();
				p.Dynasty = updateDynastyText.getText();

				updateRuler(p);

				updateIDText.setText("");
				updatePIDText.setText("");
				updateKIDText.setText("");
				updateHIDText.setText("");
				updateYearsOfExperienceText.setText("");
				updateTitleText.setText("");
				updateDynastyText.setText("");

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
					deleteRuler(id);
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
		String[] columnNames = { "ID", "PID", "KID", "HID", "YearsOfExperience", "Title", "Dynasty" };
		ArrayList<Ruler> rulers = getRulers();
		Object[][] data = new Object[rulers.size()][5];
		for (int i = 0; i < rulers.size(); i++) {
			Ruler k = rulers.get(i);
			data[i] = k.getRow();
		}
		JTable table = new JTable(data, columnNames);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);

		return scrollPane;
	}

	public boolean addRuler(Ruler p) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Insert_Ruler(?, ?, ?, ?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, p.PID);
			cs.setInt(3, p.KID);
			cs.setInt(4, p.HID);
			cs.setInt(5, p.YearsOfExperience);
			cs.setString(6, p.Title);
			cs.setString(7, p.Dynasty);
			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "Please provide a First Name");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Gender markers include: M F X. Please choose one");
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

	public boolean updateRuler(Ruler p) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Update_Ruler(?,?,?,?,?,?,?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, p.ID);
			cs.setInt(3, p.PID);
			cs.setInt(4, p.KID);
			cs.setInt(5, p.HID);
			cs.setInt(6, p.YearsOfExperience);
			cs.setString(7, p.Title);
			cs.setString(8, p.Dynasty);

			cs.execute();
			int returnVal = cs.getInt(1);
			switch (returnVal) {
			case 1:
				JOptionPane.showMessageDialog(null, "The ID " + p.ID + " does not exist");
				break;
			case 5:
				JOptionPane.showMessageDialog(null, "Gender markers include: M F X. Please choose one");
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

	public boolean deleteRuler(int ID) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.Delete_Ruler(?) }");
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

	public ArrayList<Ruler> getRulers() {
		try {
			String query = "SELECT ID, PID, KID, HID, YearsOfExperience, Title, Dynasty \nFROM Ruler\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve rulers.");
			ex.printStackTrace();
			return new ArrayList<Ruler>();
		}

	}

	private ArrayList<Ruler> parseResults(ResultSet rs) {
		try {
			ArrayList<Ruler> rulers = new ArrayList<Ruler>();
			while (rs.next()) {
				Ruler ruler = new Ruler();
				ruler.ID = rs.getInt("ID");
				ruler.PID = rs.getInt("PID");
				ruler.KID = rs.getInt("KID");
				ruler.HID = rs.getInt("HID");
				ruler.YearsOfExperience = rs.getInt("YearsOfExperience");
				ruler.Title = rs.getString("Title");
				ruler.Dynasty = rs.getString("Dynasty");

				rulers.add(ruler);
			}
			return rulers;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "An error ocurred while retrieving rulers. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<Ruler>();
		}

	}
}
