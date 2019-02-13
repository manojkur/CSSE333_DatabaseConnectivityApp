package tables;

public class Resource {
	public int ID;
	public String Name;

	public Object[] getRow() {
		Object[] o = { new Integer(ID), Name };
		return o;
	}
}
