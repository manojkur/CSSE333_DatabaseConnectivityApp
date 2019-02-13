package tables;

public class City {
	public int ID;
	public int KID;
	public int TID;
	public String Name;
	public int Population;
	public String Coordinates;

	public Object[] getRow() {
		Object[] o = { new Integer(ID), new Integer(KID), new Integer(TID), Name, new Integer(Population),
				Coordinates };
		return o;
	}
}
