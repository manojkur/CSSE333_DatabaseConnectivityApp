package tables;

import java.sql.Date;

public class Kingdom {
	public int ID;
	public String Name;
	public String ShortName;
	public Date DateConquered;
	public long GDP;
	public String Succession;
	public String Type;

	public Object[] getRow() {
		Object[] o = { new Integer(ID), Name, ShortName, DateConquered, new Long(GDP), Succession, Type };
		return o;
	}
}
