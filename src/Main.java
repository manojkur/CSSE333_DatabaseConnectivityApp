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
//			ResultSet rs;
//			kingdoms = new ArrayList<String>();
//			stmt = dbcs.getConnection().createStatement();
//			stmt.execute("select * from Kingdom");
//			rs = stmt.getResultSet();
//			while (rs.next()) {
//				kingdoms.add(rs.getString("Name"));
//			}
			KingdomService ks = new KingdomService(dbcs);
			ArrayList<Kingdom> kingdoms = ks.getKingdoms();
			for (int i = 0; i < kingdoms.size(); i++) {
				System.out.println(kingdoms.get(i).Name);
			}

			String[] columnNames = { "Name", "ShortName", "GDP", "Succession", "Type" };

			Object[][] data = new Object[kingdoms.size()][5];
			System.out.println(kingdoms.size());
			for (int i = 0; i < kingdoms.size(); i++) {
				Kingdom k = kingdoms.get(i);
				Object[] o = { k.Name, k.ShortName, k.GDP, k.Succession, k.Type };
				System.out.println(i);
				data[i] = o;
			}
			for (Object[] o : data) {
				for (Object ob : o) {
					System.out.print(ob + "    ");
				}
				System.out.println();
			}

			JTable table = new JTable(data, columnNames);
			JScrollPane scrollPane = new JScrollPane(table);
			JFrame tableFrame = new JFrame();
			tableFrame.add(scrollPane);
			tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			tableFrame.pack();
			tableFrame.setVisible(true);

//			SwingUtilities.invokeLater(new Runnable() {
//				@Override
//				public void run() {
//					JTable table = new JTable(data, columnNames);
//					JScrollPane scrollPane = new JScrollPane(table);
//					JFrame tableFrame = new JFrame();
//					tableFrame.add(scrollPane);
//					tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//					tableFrame.pack();
//					tableFrame.setVisible(true);
//				}
//			});

		} catch (Exception e) {
			e.printStackTrace();
		}
//		for (String kingdom : kingdoms) {
//			System.out.println(kingdom);
//		}
		dbcs.closeConnection();
	}

}
