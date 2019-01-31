package tables;

public class Terrain {
	public int ID;
	public String TraverseDifficulty;
	public String Name;

	public Object[] getRow() {
		Object[] o = { ID, TraverseDifficulty, Name };
		return o;
	}
}
