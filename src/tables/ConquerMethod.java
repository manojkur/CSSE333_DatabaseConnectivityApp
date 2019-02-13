package tables;

public class ConquerMethod {
	public int ID;
	public String Effectiveness;
	public String Name;

	public Object[] getRow() {
		Object[] o = { new Integer(ID), Name, Effectiveness };
		return o;
	}
}
