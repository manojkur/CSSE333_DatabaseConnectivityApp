package tables;

public class Heir {
	public int ID;
	public int PID;
	public int KID;
	public String TitleStart;
	public String TitleEnd;
	public String ShortTitle;

	public Object[] getRow() {
		Object[] o = { new Integer(ID), new Integer(PID), new Integer(KID), TitleStart, TitleEnd, ShortTitle };
		return o;
	}
}
