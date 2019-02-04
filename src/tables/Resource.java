package tables;

public class Resource {
	public int ID;
	public String Name;

	public Object[] getRow() {
		Object[] o = { ID, Name };
		return o;
	}
}
