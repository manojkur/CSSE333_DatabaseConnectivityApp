package Views;

public class KnightPersonMilitaryView {
	public String FirstName;
	public String LastName;
	public char Gender;
	public String Name;
	public int KillCount;

	public Object[] getRow() {
		Object[] o = { FirstName, LastName, new Character(Gender), Name, new Integer(KillCount) };
		return o;
	}
}