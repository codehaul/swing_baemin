
public class StoreBean {
	private String id;
	private String storeName;
	private String foodType;
	private int priceAvg;
	private String storeLocation;
	private String callNum;
	private int reviewCnt;
	private String img;
	
	

	public StoreBean(String id, String storeName, String foodType, int priceAvg, String storeLocation, String callNum,
			int reviewCnt) {
		super();
		this.id = id;
		this.storeName = storeName;
		this.foodType = foodType;
		this.priceAvg = priceAvg;
		this.storeLocation = storeLocation;
		this.callNum = callNum;
		this.reviewCnt = reviewCnt;
	}

	public StoreBean(String id, String storeName, String foodType, int priceAvg, String storeLocation, String callNum,
			int reviewCnt, String img) {
		super();
		this.id = id;
		this.storeName = storeName;
		this.foodType = foodType;
		this.priceAvg = priceAvg;
		this.storeLocation = storeLocation;
		this.callNum = callNum;
		this.reviewCnt = reviewCnt;
		this.img = img;
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

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	public int getPriceAvg() {
		return priceAvg;
	}

	public void setPriceAvg(int priceAvg) {
		this.priceAvg = priceAvg;
	}

	public String getStoreLocation() {
		return storeLocation;
	}

	public void setStoreLocation(String storeLocation) {
		this.storeLocation = storeLocation;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	public int getReviewCnt() {
		return reviewCnt;
	}

	public void setReviewCnt(int reviewCnt) {
		this.reviewCnt = reviewCnt;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	


}
