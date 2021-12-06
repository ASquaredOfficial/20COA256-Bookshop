import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Purchased extends Transaction{
	private PaymentType paytype;
	private LocalDate releaseDate = LocalDate.now();
	
	public Purchased(int userId, String postcode, String ISBN, float retailPrice, int quantity, PaymentType paytype) {
		super(userId, postcode, ISBN, retailPrice, quantity);
		this.paytype = paytype;
	}
	
	public PaymentType getPaytype() {
		return paytype;
	} public LocalDate getReleaseDate() {
		return releaseDate;
	}
	
	public String paymentTypeToString(){
		String string = null;
		if (this.paytype == PaymentType.CreditCard) {
			string = "Credit Card";
		} else if (this.paytype ==  PaymentType.PayPal) {
			string = "PayPal";
		} 
		return string;
	}
	
	@Override
	public String toString(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return getUserId() +", "+ getPostcode() +", "+ getISBN() +", "+ getRetailPrice() +", "+getQuantity() +", purchased, "+ paymentTypeToString() +", "+ formatter.format(getReleaseDate());
	}
}
