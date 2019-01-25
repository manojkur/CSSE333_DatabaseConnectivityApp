package Views;

import java.sql.Date;

public class KingdomBuiltOnTopOf {
	public String Name;
	public String ShortName;
	public Date DateConquered;
	public long GDP;
	public String Succession;
	public String Type;
	public String terrainName;
	public String traverseDifficulty;
	
	public Object[] getKingdomBuiltOnTopOf() {
		Object[] o = { Name, ShortName, DateConquered, GDP, Succession, Type, terrainName, traverseDifficulty};
		return o;
	}
}
