
public class User {
	private int userId;
	private String userNo;
	private String username;
	private Address userAddr;
	
	public User(int userId, String userNo, String username, Address userAddr) {
		this.userId = userId;
		this.username =username;
		this.userNo = userNo;
		this.userAddr = userAddr;
	}
	
	//Getter Methods
	public int getUserId() {
		return userId;
	} public String getUserNo() {
		return userNo;
	} public String getUsername() {
		return username;
	} public Address getUserAddr() {
		return userAddr;
	}
	
	@Override
	public String toString() {
		return userId + ", " + userNo + ", " + username+ ", " + userAddr;
	}
}
