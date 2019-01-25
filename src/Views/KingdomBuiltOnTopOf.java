<<<<<<< HEAD
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
	
	public Object[] getRow() {
		Object[] o = { Name, ShortName, DateConquered, GDP, Succession, Type, terrainName, traverseDifficulty};
		return o;
	}
}
||||||| parent of 0ce460f... added services and classes for the views.
=======
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
>>>>>>> 0ce460f... added services and classes for the views.
