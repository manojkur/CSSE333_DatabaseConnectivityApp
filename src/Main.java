import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import services.DatabaseConnectionService;
import services.KingdomService;
import tables.Kingdom;

public class Main {

	public static void main(String[] args) {
		DatabaseConnectionService dbcs = new DatabaseConnectionService("golem.csse.rose-hulman.edu", "KingdomDB");
		Statement stmt;

		JFrame frame = new JFrame("Credentials");
		String user = JOptionPane.showInputDialog(frame, "Please enter your username");
		String password = "";
		JPasswordField pf = new JPasswordField();
		int pass = JOptionPane.showConfirmDialog(null, pf, "Please enter your password", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (pass == JOptionPane.OK_OPTION) {
			password = new String(pf.getPassword());
		}

		try {
			// Boolean connectionBool = dbcs.connect(user, password);
			Boolean connectionBool = dbcs.connect("kurapam", "csse333pass");
			KingdomService ks = new KingdomService(dbcs);
			ArrayList<Kingdom> kingdoms = ks.getKingdoms();

			String[] columnNames = { "ID", "Name", "ShortName", "DateConquered", "GDP", "Succession", "Type" };

			Object[][] data = new Object[kingdoms.size()][5];
			for (int i = 0; i < kingdoms.size(); i++) {
				Kingdom k = kingdoms.get(i);
				data[i] = k.getKingdom();
			}

			String[] Actions = { "View", "Insert", "Update", "Delete" };

//			JComboBox petList = new JComboBox(petStrings);
//			petList.setSelectedIndex(4);
//			petList.addActionListener(this);

			JTable table = new JTable(data, columnNames);
			table.setAutoCreateRowSorter(true);
			JScrollPane scrollPane = new JScrollPane(table);
			JFrame tableFrame = new JFrame();
			tableFrame.add(scrollPane);
//			tableFrame.remove(scrollPane);
			tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			tableFrame.pack();
			tableFrame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		dbcs.closeConnection();
	}

}
