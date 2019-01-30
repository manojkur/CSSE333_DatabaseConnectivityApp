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

import tables.Person;

public class PersonService {
	private DatabaseConnectionService dbService = null;
	private JComponent view;

	public PersonService(DatabaseConnectionService dbService) {
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
		JLabel insertFirstNameLabel = new JLabel("FirstName: ");
		insert.add(insertFirstNameLabel);
		JTextField insertFirstNameText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertFirstNameText);

		JLabel insertLastNameLabel = new JLabel("LastName: ");
		insert.add(insertLastNameLabel);
		JTextField insertLastNameText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertLastNameText);

		JLabel insertOtherNamesLabel = new JLabel("OtherNames: ");
		insert.add(insertOtherNamesLabel);
		JTextField insertOtherNamesText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertOtherNamesText);

		JLabel insertSuffixLabel = new JLabel("Suffix: ");
		insert.add(insertSuffixLabel);
		JTextField insertSuffixText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertSuffixText);

		JLabel insertGenderLabel = new JLabel("Gender: ");
		insert.add(insertGenderLabel);
		JTextField insertGenderText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		insert.add(insertGenderText);

		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Person p = new Person();
				p.FirstName = insertFirstNameText.getText();
				p.LastName = insertLastNameText.getText();
				p.OtherNames = insertOtherNamesText.getText();
				p.Suffix = insertSuffixText.getText();
				p.Gender = insertGenderText.getText();

				addPerson(p);

				insertFirstNameText.setText("");
				insertLastNameText.setText("");
				insertOtherNamesText.setText("");
				insertSuffixText.setText("");
				insertGenderText.setText("");

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

		JLabel updateFirstNameLabel = new JLabel("FirstName: ");
		update.add(updateFirstNameLabel);
		JTextField updateFirstNameText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateFirstNameText);

		JLabel updateLastNameLabel = new JLabel("LastName: ");
		update.add(updateLastNameLabel);
		JTextField updateLastNameText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateLastNameText);

		JLabel updateOtherNamesLabel = new JLabel("OtherNames: ");
		update.add(updateOtherNamesLabel);
		JTextField updateOtherNamesText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateOtherNamesText);

		JLabel updateSuffixLabel = new JLabel("Suffix: ");
		update.add(updateSuffixLabel);
		JTextField updateSuffixText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateSuffixText);

		JLabel updateGenderLabel = new JLabel("Gender: ");
		update.add(updateGenderLabel);
		JTextField updateGenderText = (new JTextField() {
			public JTextField setMaxSize(Dimension d) {
				setMaximumSize(d);
				return this;
			}
		}).setMaxSize(new Dimension(width, height));
		update.add(updateGenderText);

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
					List<Person> persons = getPersons();
					Person p = null;
					for (Person person : persons) {
						if (person.ID == ID)
							p = person;
					}
					if (p != null) {
						updateFirstNameText.setText(p.FirstName);
						updateLastNameText.setText(p.LastName);
						updateOtherNamesText.setText(p.OtherNames);
						updateSuffixText.setText(p.Suffix);
						updateGenderText.setText(p.Gender);
					} else {
						updateFirstNameText.setText("");
						updateLastNameText.setText("");
						updateOtherNamesText.setText("");
						updateSuffixText.setText("");
						updateGenderText.setText("");
					}
				} catch (NumberFormatException e) {
					updateFirstNameText.setText("");
					updateLastNameText.setText("");
					updateOtherNamesText.setText("");
					updateSuffixText.setText("");
					updateGenderText.setText("");
				}
			}
		});

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Person p = new Person();
				try {
					p.ID = Integer.parseInt(updateIDText.getText());
				} catch (NumberFormatException e) {

				}

				p.FirstName = updateFirstNameText.getText();
				p.LastName = updateLastNameText.getText();
				p.OtherNames = updateOtherNamesText.getText();
				p.Suffix = updateSuffixText.getText();
				p.Gender = updateGenderText.getText();

				updatePerson(p);

				updateIDText.setText("");
				updateFirstNameText.setText("");
				updateLastNameText.setText("");
				updateOtherNamesText.setText("");
				updateSuffixText.setText("");
				updateGenderText.setText("");

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
					deletePerson(id);
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
		String[] columnNames = { "ID", "FirstName", "LastName", "OtherNames", "Suffix", "Gender" };
		ArrayList<Person> persons = getPersons();
		Object[][] data = new Object[persons.size()][5];
		for (int i = 0; i < persons.size(); i++) {
			Person k = persons.get(i);
			data[i] = k.getRow();
		}
		JTable table = new JTable(data, columnNames);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);

		return scrollPane;
	}

	public boolean addPerson(Person p) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Insert_Person(?, ?, ?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, p.FirstName);
			cs.setString(3, p.LastName);
			cs.setString(4, p.OtherNames);
			cs.setString(5, p.Suffix);
			cs.setString(6, p.Gender);
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

	public boolean updatePerson(Person p) {
		try {
			CallableStatement cs = this.dbService.getConnection()
					.prepareCall("{ ? = call dbo.Update_Person(?,?,?,?,?,?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, p.ID);
			cs.setString(3, p.FirstName);
			cs.setString(4, p.LastName);
			cs.setString(5, p.OtherNames);
			cs.setString(6, p.Suffix);
			cs.setString(7, p.Gender);

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

	public boolean deletePerson(int ID) {
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.Delete_Person(?) }");
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

	public ArrayList<Person> getPersons() {
		try {
			String query = "SELECT ID, FirstName, LastName, OtherNames, Suffix, Gender \nFROM Person\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			return parseResults(rs);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve persons.");
			ex.printStackTrace();
			return new ArrayList<Person>();
		}

	}

	private ArrayList<Person> parseResults(ResultSet rs) {
		try {
			ArrayList<Person> persons = new ArrayList<Person>();
			while (rs.next()) {
				Person person = new Person();
				person.ID = rs.getInt("ID");
				person.FirstName = rs.getString("FirstName");
				person.LastName = rs.getString("LastName");
				person.OtherNames = rs.getString("OtherNames");
				person.Suffix = rs.getString("Suffix");
				person.Gender = rs.getString("Gender");

				persons.add(person);
			}
			return persons;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "An error ocurred while retrieving persons. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<Person>();
		}

	}
}
