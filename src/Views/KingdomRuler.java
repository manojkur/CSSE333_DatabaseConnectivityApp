package Views;

import java.sql.Date;

public class KingdomRuler {
	public String Name;
	public String ShortName;
	public Date DateConquered;
	public long GDP;
	public String Succession;
	public String Type;
	public int YearsOfExperience;
	public String title;
	public String dynasty;
	public String firstName;
	public String lastName;
	public char gender;
	public String otherNames;
	public String suffix;

	public Object[] getRow() {
		Object[] o = { Name, ShortName, DateConquered, new Long(GDP), Succession, Type, new Integer(YearsOfExperience),
				title, dynasty, firstName, lastName, new Character(gender), otherNames, suffix };
		return o;
	}
}