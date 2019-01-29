package tables;

public class Ruler {
	public int ID;
	public int PID;
	public int KID;
	public int HID;
	public int YearsOfExperience;
	public String Title;
	public String Dynasty;

	public Object[] getRow() {
		Object[] o = { ID, PID, KID, HID, YearsOfExperience, Title, Dynasty };
		return o;
	}
}
