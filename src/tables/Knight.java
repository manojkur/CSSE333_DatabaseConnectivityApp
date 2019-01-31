package tables;

public class Knight {
	public int ID;
	public int PID;
	public int MID;
	public int KillCount;

	public Object[] getRow() {
		Object[] o = { ID, PID, MID, KillCount};
		return o;
	}
}
