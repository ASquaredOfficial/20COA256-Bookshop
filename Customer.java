
public class Customer extends User{
	public Customer(int userId, String userNo, String username, Address userAddr) {
		super(userId, userNo, username, userAddr);
	}
	
	//Getter Methods
	@Override
	public int getUserId() {
		return super.getUserId();
	} @Override
	public String getUserNo() {
		return super.getUserNo();
	} @Override
	public String getUsername() {
		return super.getUsername();
	} @Override
	public Address getUserAddr() {
		return super.getUserAddr();
	}
	
}
