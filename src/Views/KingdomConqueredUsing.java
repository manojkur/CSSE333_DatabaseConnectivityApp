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
		Object[] o = { Name, ShortName, DateConquered, new Long(GDP), Succession, Type, ConqueredMethodName,
				effectiveness };
		return o;
	}
}