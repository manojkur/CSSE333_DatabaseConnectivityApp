package tables;

public class ConqueredUsing {
	public int ID;
	public int KID;
	public int CMID;

	public Object[] getRow() {
		Object[] o = { ID, KID, CMID };
		return o;
	}
}
