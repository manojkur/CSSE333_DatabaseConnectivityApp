import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;

import services.DatabaseConnectionService;
import services.KingdomService;
import tables.Kingdom;

public class Main {

	public static void main(String[] args) {
		DatabaseConnectionService dbcs = new DatabaseConnectionService("golem.csse.rose-hulman.edu", "KingdomDB");
		Statement stmt;

//		JFrame frame = new JFrame("Credentials");
//		String user = JOptionPane.showInputDialog(frame, "Please enter your username");
//		String password = "";
//		JPasswordField pf = new JPasswordField();
//		int pass = JOptionPane.showConfirmDialog(null, pf, "Please enter your password", JOptionPane.OK_CANCEL_OPTION,
//				JOptionPane.PLAIN_MESSAGE);
//		if (pass == JOptionPane.OK_OPTION) {
//			password = new String(pf.getPassword());
//		}

		try {
			// Boolean connectionBool = dbcs.connect(user, password);
			Boolean connectionBool = dbcs.connect("kurapam", "csse333pass");
			KingdomService ks = new KingdomService(dbcs);
			ArrayList<Kingdom> kingdoms = ks.getKingdoms();

		} catch (Exception e) {
			e.printStackTrace();
		}
		dbcs.closeConnection();
	}

}
