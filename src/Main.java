import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import services.DatabaseConnectionService;
import services.HeirService;
import services.KingdomBuiltOnTopOfService;
import services.KingdomCityService;
import services.KingdomConqueredUsingService;
import services.KingdomMilitaryService;
import services.KingdomRulerService;
import services.KingdomService;
import services.PersonService;
import services.RulerService;

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
			Boolean connectionBool = dbcs.connect(user, password);

			KingdomService ks = new KingdomService(dbcs);
			PersonService person = new PersonService(dbcs);
			RulerService ruler = new RulerService(dbcs);
			KingdomBuiltOnTopOfService kingdomBuiltOnTopOfService = new KingdomBuiltOnTopOfService(dbcs);
			KingdomCityService kingdomCityService = new KingdomCityService(dbcs);
			KingdomConqueredUsingService kingdomConqueredUsingService = new KingdomConqueredUsingService(dbcs);
			KingdomMilitaryService kingdomMilitaryService = new KingdomMilitaryService(dbcs);

			KingdomRulerService kingdomRulerService = new KingdomRulerService(dbcs);

			HeirService heir = new HeirService(dbcs);

			JFrame tableFrame = new JFrame();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

			// get 2/3 of the height, and 2/3 of the width
			int height = screenSize.height * 2 / 3;
			int width = screenSize.width * 2 / 3;

			// set the jframe height and width
			tableFrame.setPreferredSize(new Dimension(width, height));

			JPanel kingdomCards = new JPanel(new BorderLayout());

			JPanel comboBoxPane = new JPanel(); // use FlowLayout
			String comboBoxItems[] = { "Kingdom", "Person", "Ruler", "Heir", "KingdomBuiltOnTopOfView",
					"KingdomCityView", "KingdomConqueredUsingView", "KingdomMilitaryView", "KingdomRulerView" };
			JComboBox cb = new JComboBox(comboBoxItems);
			cb.setEditable(false);

			JPanel cards = new JPanel(new CardLayout());
			cards.add(ks.getJPanel(), comboBoxItems[0]);
			cards.add(person.getJPanel(), comboBoxItems[1]);
			cards.add(ruler.getJPanel(), comboBoxItems[2]);
			cards.add(heir.getJPanel(), comboBoxItems[3]);
			cards.add(kingdomBuiltOnTopOfService.getScrollableTable(), comboBoxItems[4]);
			cards.add(kingdomCityService.getScrollableTable(), comboBoxItems[5]);
			cards.add(kingdomConqueredUsingService.getScrollableTable(), comboBoxItems[6]);
			cards.add(kingdomMilitaryService.getScrollableTable(), comboBoxItems[7]);
			cards.add(kingdomRulerService.getScrollableTable(), comboBoxItems[8]);

			cb.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					CardLayout cl = (CardLayout) (cards.getLayout());
					cl.show(cards, (String) evt.getItem());
				}
			});
			comboBoxPane.add(cb);
			kingdomCards.add(comboBoxPane, BorderLayout.PAGE_START);
			kingdomCards.add(cards, BorderLayout.CENTER);

			tableFrame.add(kingdomCards);
			tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			tableFrame.pack();
			tableFrame.setVisible(true);

			tableFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					dbcs.closeConnection();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
