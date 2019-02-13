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
		Object[] o = { new Integer(ID), new Integer(PID), new Integer(KID), new Integer(HID),
				new Integer(YearsOfExperience), Title, Dynasty };
		return o;
	}
}
