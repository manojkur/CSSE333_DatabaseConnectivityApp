package tables;

import java.sql.Date;

public class Military {
	public int ID;
	public int KID;
	public String Name;
	public long Budget;

	public Object[] getRow() {
		Object[] o = { ID, KID, Name, Budget };
		return o;
	}
}
