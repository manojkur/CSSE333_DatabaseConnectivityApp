package Views;


public class FunctionsUsingResourceCity {
	public String CityName;
	public int Population;
	public String ResourceName;
	public int Quantity;

	public Object[] getRow() {
		Object[] o = { CityName, new Integer(Population), ResourceName, new Integer(Quantity)};
		return o;
	}
}