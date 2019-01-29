package tables;

public class Person {
	public int ID;
	public String FirstName;
	public String LastName;
	public String OtherNames;
	public String Suffix;
	public String Gender;

	public Object[] getRow() {
		Object[] o = { ID, FirstName, LastName, OtherNames, Suffix, Gender };
		return o;
	}
}
