package tables;

public class Military {
	public int ID;
	public int KID;
	public String Name;
	public long Budget;

	public Object[] getRow() {
		Object[] o = { new Integer(ID), new Integer(KID), Name, new Long(Budget) };
		return o;
	}
}
