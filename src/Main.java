import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import services.CityService;
import services.ConqueredMethodService;
import services.ConqueredUsingService;
import services.DatabaseConnectionService;
import services.FunctionsUsingResourceCityService;
import services.FunctionsUsingService;
import services.HeirService;
import services.KingdomBuiltOnTopOfService;
import services.KingdomCityService;
import services.KingdomConqueredUsingMethodService;
import services.KingdomConqueredUsingService;
import services.KingdomMilitaryService;
import services.KingdomRulerService;
import services.KingdomService;
import services.KnightPersonMilitaryViewService;
import services.KnightService;
import services.MilitaryService;
import services.PersonService;
import services.ResourceService;
import services.RulerService;
import services.Services;
import services.TerrainService;
import services.ViewServices;

public class Main {

	public static void main(String[] args) {
		DatabaseConnectionService dbcs = new DatabaseConnectionService("golem.csse.rose-hulman.edu", "KingdomDB");

		JFrame frame = new JFrame("Credentials");
		String user = JOptionPane.showInputDialog(frame, "Please enter your username");
		String password = "";
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPasswordField pf = new JPasswordField();
		panel.add(new JLabel("Please enter your password"));
		panel.add(pf);
		JOptionPane pane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION) {
			@Override
			public void selectInitialValue() {
				pf.requestFocusInWindow();
			}
		};

		pane.createDialog(null, "Please enter your password").setVisible(true);
		// int pass = JOptionPane.showConfirmDialog(null, pf, "Please enter your
		// password", JOptionPane.OK_CANCEL_OPTION,
		// JOptionPane.PLAIN_MESSAGE);
//		 if (pass == JOptionPane.OK_OPTION) {
		password = pf.getPassword().length == 0 ? null : new String(pf.getPassword());
//		 }

		try {
			Boolean connectionBool = dbcs.connect(user, password);
			PreparedStatement cs = dbcs.getConnection().prepareStatement(
					"SELECT DP1.name AS DatabaseRoleName, DP2.name AS DatabaseUserName FROM sys.database_role_members AS DRM "
							+ "RIGHT OUTER JOIN sys.database_principals AS DP1"
							+ " ON DRM.role_principal_id = DP1.principal_id "
							+ "LEFT OUTER JOIN sys.database_principals AS DP2 "
							+ "ON DRM.member_principal_id = DP2.principal_id" + " where dp2.name = ?");
			cs.setString(1, user);
			ResultSet rs = cs.executeQuery();
			boolean isOwner = true;
			while (rs.next()) {
				if (rs.getString("DatabaseRoleName").equals("db_datareader")) {
					isOwner = false;
				}
			}
			if (connectionBool) {
				// tables
				KingdomService ks = new KingdomService(dbcs, isOwner);
				PersonService person = new PersonService(dbcs, isOwner);
				RulerService ruler = new RulerService(dbcs, isOwner);
				HeirService heir = new HeirService(dbcs, isOwner);
				CityService city = new CityService(dbcs, isOwner);
				TerrainService terrainService = new TerrainService(dbcs, isOwner);
				KnightService knight = new KnightService(dbcs, isOwner);
				MilitaryService military = new MilitaryService(dbcs, isOwner);
				ResourceService resource = new ResourceService(dbcs, isOwner);
				ConqueredUsingService conqueredUsing = new ConqueredUsingService(dbcs, isOwner);
				ConqueredMethodService conquerMethod = new ConqueredMethodService(dbcs, isOwner);
				FunctionsUsingService functionsUsing = new FunctionsUsingService(dbcs, isOwner);

				// views
				KingdomBuiltOnTopOfService kingdomBuiltOnTopOfService = new KingdomBuiltOnTopOfService(dbcs);
				KingdomCityService kingdomCityService = new KingdomCityService(dbcs);
				KingdomConqueredUsingService kingdomConqueredUsingService = new KingdomConqueredUsingService(dbcs);
				KingdomMilitaryService kingdomMilitaryService = new KingdomMilitaryService(dbcs);
				KingdomRulerService kingdomRulerService = new KingdomRulerService(dbcs);
				KingdomConqueredUsingMethodService kingdomConqueredUsingMethodService = new KingdomConqueredUsingMethodService(
						dbcs);
				FunctionsUsingResourceCityService functionsUsingResourceCityService = new FunctionsUsingResourceCityService(
						dbcs);
				KnightPersonMilitaryViewService knightPersonMilitaryViewService = new KnightPersonMilitaryViewService(
						dbcs);

				JFrame tableFrame = new JFrame("Kingdom Database entries");
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

				Map<Services, String> services = new HashMap<>();
				services.put(ks, "Kingdom");
				services.put(person, "Person");
				services.put(ruler, "Ruler");
				services.put(heir, "Heir");
				services.put(city, "City");
				services.put(terrainService, "Terrain");
				services.put(knight, "Knight");
				services.put(military, "Military");
				services.put(resource, "Resource");
				services.put(conqueredUsing, "ConqueredUsing");
				services.put(conquerMethod, "ConquerMethod");
				services.put(functionsUsing, "FunctionsUsing");

				Map<ViewServices, String> viewServices = new HashMap<>();
				viewServices.put(kingdomBuiltOnTopOfService, "KingdomBuiltOnTopOfView");
				viewServices.put(kingdomCityService, "KingdomCityView");
				viewServices.put(kingdomConqueredUsingService, "KingdomConqueredUsingView");
				viewServices.put(kingdomMilitaryService, "KingdomMilitaryView");
				viewServices.put(kingdomRulerService, "KingdomRulerView");
				viewServices.put(kingdomConqueredUsingMethodService, "KingdomConqueredUsingMethodView");
				viewServices.put(functionsUsingResourceCityService, "FunctionsUsingResourceCityView");
				viewServices.put(knightPersonMilitaryViewService, "KnightPersonMilitaryViewService");

				// get 2/3 of the height, and 2/3 of the width
				int height = screenSize.height * 2 / 3;
				int width = screenSize.width * 2 / 3;

				// set the jframe height and width
				tableFrame.setPreferredSize(new Dimension(width, height));

				JPanel kingdomCards = new JPanel(new BorderLayout());

				JPanel comboBoxPane = new JPanel(); // use FlowLayout
				String comboBoxItems[] = { "Kingdom", "Person", "Ruler", "Heir", "City", "Terrain", "Knight",
						"Military", "Resource", "ConqueredUsing", "ConquerMethod", "FunctionsUsing",
						"KingdomBuiltOnTopOfView", "KingdomCityView", "KingdomConqueredUsingView",
						"KingdomMilitaryView", "KingdomRulerView", "KingdomConqueredUsingMethodView",
						"FunctionsUsingResourceCityView", "KnightPersonMilitaryViewService" };
				JComboBox cb = new JComboBox(comboBoxItems);
				cb.setEditable(false);

				JPanel cards = new JPanel(new CardLayout());
				for (Services service : services.keySet()) {
					cards.add(service.getJPanel(), services.get(service));
				}

				for (ViewServices service : viewServices.keySet()) {
					cards.add(service.getScrollableTable(), viewServices.get(service));
				}

				cb.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent evt) {
						CardLayout cl = (CardLayout) (cards.getLayout());
						String item = (String) evt.getItem();
						cards.removeAll();
						for (Services service : services.keySet()) {
							cards.add(service.getJPanel(), services.get(service));
						}

						for (ViewServices service : viewServices.keySet()) {
							cards.add(service.getScrollableTable(), viewServices.get(service));
						}
						cl.show(cards, item);
					}
				});
				CardLayout cl = (CardLayout) cards.getLayout();
				cl.show(cards, "Kingdom");
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
			}
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("Please enter a valid username and password");
		}

	}

}
