package tables;

import java.sql.Date;

public class Heir {
	public int ID;
	public int PID;
	public int KID;
	public String TitleStart;
	public String TitleEnd;
	public String ShortTitle;

	public Object[] getRow() {
		Object[] o = { ID,PID,KID,TitleStart,TitleEnd,ShortTitle };
		return o;
	}
}
