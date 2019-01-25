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
	
	public Object[] getKingdomRuler() {
		Object[] o = { Name, ShortName, DateConquered, GDP, Succession, Type, YearsOfExperience, title, dynasty, firstName, lastName, gender, otherNames, suffix};
		return o;
	}
}
