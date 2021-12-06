
public class Transaction {
	private int userId;
	private String postcode;
	private String ISBN;
	private float retailPrice;
	private int quantity;
	
	public Transaction(int userId, String postcode, String ISBN, float retailPrice, int quantity) {
		this.userId = userId;
		this.postcode = postcode;
		this.ISBN = ISBN;
		this.retailPrice = retailPrice;
		this.quantity = quantity;
	}
	
	//Getter Methods
	public int getUserId() {
		return userId;
	} public String getISBN() {
		return ISBN;
	} public String getPostcode() {
		return postcode;
	} public float getRetailPrice() {
		return retailPrice;
	} public int getQuantity() {
		return quantity;
	}

}
