package tables;

public class Terrain {
	public int ID;
	public String TraverseDifficulty;
	public String Name;

	public Object[] getRow() {
		Object[] o = { new Integer(ID), Name, TraverseDifficulty };
		return o;
	}
}
