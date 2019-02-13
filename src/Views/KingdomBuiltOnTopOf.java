package Views;

import java.sql.Date;

public class KingdomBuiltOnTopOf {
	public String Name;
	public String ShortName;
	public Date DateConquered;
	public long GDP;
	public String Succession;
	public String cityName;
	public String Type;
	public String terrainName;
	public String traverseDifficulty;

	public Object[] getRow() {
		Object[] o = { Name, ShortName, DateConquered, new Long(GDP), Succession, Type, cityName, terrainName,
				traverseDifficulty };
		return o;
	}
}