package tables;

public class FunctionsUsing {
	public int ID;
	public int RID;
	public int CID;
	public int Quantity;

	public Object[] getRow() {
		Object[] o = { new Integer(ID), new Integer(RID), new Integer(CID), new Integer(Quantity) };
		return o;
	}
}
