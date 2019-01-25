<<<<<<< HEAD
package Views;

import java.sql.Date;

public class KingdomConqueredUsing {
	public String Name;
	public String ShortName;
	public Date DateConquered;
	public long GDP;
	public String Succession;
	public String Type;
	public String ConqueredMethodName;
	public String effectiveness;
	
	public Object[] getRow() {
		Object[] o = { Name, ShortName, DateConquered, GDP, Succession, Type, ConqueredMethodName, effectiveness};
		return o;
	}
}
||||||| parent of 0ce460f... added services and classes for the views.
=======
package Views;

import java.sql.Date;

public class KingdomConqueredUsing {
	public String Name;
	public String ShortName;
	public Date DateConquered;
	public long GDP;
	public String Succession;
	public String Type;
	public String ConqueredMethodName;
	public String effectiveness;
	
	public Object[] getKingdomConqueredUsing() {
		Object[] o = { Name, ShortName, DateConquered, GDP, Succession, Type, ConqueredMethodName, effectiveness};
		return o;
	}
}
>>>>>>> 0ce460f... added services and classes for the views.
