import java.util.ArrayList;


public class ShopItem {
	private String name;
	private String current_price;
	private ArrayList<String> description = new ArrayList<String>();
	private String href;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(String current_price) {
		this.current_price = current_price;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	public ArrayList<String> getDescription() {
		return description;
	}
	public void setDescription(ArrayList<String> description) {
		this.description = description;
	}
	public ShopItem(String name, String current_price,
			ArrayList<String> description, String href) {
		super();
		this.name = name;
		this.current_price = current_price;
		this.description = description;
		this.href = href;
	}
}
