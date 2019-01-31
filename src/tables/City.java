package tables;

public class City {
	public int ID;
	public int KID;
	public int TID;
	public String Name;
	public int Population;
	public String Coordinates;

	public Object[] getRow() {
		Object[] o = { ID, KID, TID, Name, Population, Coordinates };
		return o;
	}
}
