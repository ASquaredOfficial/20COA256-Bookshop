

public class Address {
	private int houseNo;
	private String postcode;
	private String city;
	
	public Address(int houseNo, String postcode, String city) {
		this.houseNo = houseNo;
		this.postcode = postcode;		
		this.city = city;
	}
	public String getCity() {
		return city;
	} public int getHouseNo() {
		return houseNo;
	} public String getPostcode() {
		return postcode;
	}
	
	@Override
	public String toString() {
		return houseNo + ", " + city + ", " + postcode;
	}
}
