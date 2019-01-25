package Views;

import java.sql.Date;

public class KingdomMilitary {
	public String Name;
	public String ShortName;
	public Date DateConquered;
	public long GDP;
	public String Succession;
	public String Type;
	public String MilitaryName;
	public int budget;
	
	public Object[] getKingdomMilitary() {
		Object[] o = { Name, ShortName, DateConquered, GDP, Succession, Type, MilitaryName, budget};
		return o;
	}
}
