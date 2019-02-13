package tables;

public class Knight {
	public int ID;
	public int PID;
	public int MID;
	public int KillCount;

	public Object[] getRow() {
		Object[] o = { new Integer(ID), new Integer(PID), new Integer(MID), new Integer(KillCount) };
		return o;
	}
}
