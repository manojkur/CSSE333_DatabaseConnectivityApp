package Views;

import java.sql.Date;

public class KingdomConqueredUsingMethod {
	public String KingdomName;
	public Date DateConquered;
	public String ConquerMethodName;
	public String Effectiveness;

	public Object[] getRow() {
		Object[] o = { KingdomName, DateConquered, ConquerMethodName, Effectiveness};
		return o;
	}
}