
public class MenuBean {
	private String id;
	private String storeName;
	private String menuName;
	private int price;
	private int sidedishCnt;
	private String taste;
	private String mainIngredient;
	
	
	
	public MenuBean(String id, String storeName, String menuName, int price, int sidedishCnt, String taste,
			String mainIngredient) {
		super();
		this.id = id;
		this.storeName = storeName;
		this.menuName = menuName;
		this.price = price;
		this.sidedishCnt = sidedishCnt;
		this.taste = taste;
		this.mainIngredient = mainIngredient;
	}

	public MenuBean(String menuName, int price, int sidedishCnt, String taste, String mainIngredient) {
		super();
		this.menuName = menuName;
		this.price = price;
		this.sidedishCnt = sidedishCnt;
		this.taste = taste;
		this.mainIngredient = mainIngredient;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getSidedishCnt() {
		return sidedishCnt;
	}
	public void setSidedishCnt(int sidedishCnt) {
		this.sidedishCnt = sidedishCnt;
	}
	public String getTaste() {
		return taste;
	}
	public void setTaste(String taste) {
		this.taste = taste;
	}
	public String getMainIngredient() {
		return mainIngredient;
	}
	public void setMainIngredient(String mainIngredient) {
		this.mainIngredient = mainIngredient;
	}
	
	
}
