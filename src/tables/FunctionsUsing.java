package tables;

public class FunctionsUsing {
	public int ID;
	public int RID;
	public int CID;
	public int Quantity;

	public Object[] getRow() {
		Object[] o = { ID, RID, CID, Quantity };
		return o;
	}
}
