package tables;

public class ConqueredUsing {
	public int ID;
	public int KID;
	public int CMID;

	public Object[] getRow() {
		Object[] o = { new Integer(ID), new Integer(KID), new Integer(CMID) };
		return o;
	}
}
