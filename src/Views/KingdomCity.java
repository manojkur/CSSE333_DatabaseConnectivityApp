package Views;

import java.sql.Date;

public class KingdomCity {
	public String Name;
	public String ShortName;
	public Date DateConquered;
	public long GDP;
	public String Succession;
	public String Type;
	public String cityName;
	public int latitude;
	public int longitude;
	public int population;
	
	public Object[] getRow() {
		Object[] o = { Name, ShortName, DateConquered, GDP, Succession, Type, cityName, latitude, longitude, population};
		return o;
	}
}
