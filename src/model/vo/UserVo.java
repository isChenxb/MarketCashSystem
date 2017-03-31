package model.vo;

//用户对象的数据封装
public class UserVo {
	private String userAccount;
	private String password;
	private String chrName;
	private String role;
	
	
	public UserVo(String userAccount, String password, String chrName, String role) {
		super();
		this.userAccount = userAccount;
		this.password = password;
		this.chrName = chrName;
		this.role = role;
	}
	
	
	public UserVo() {
		super();
	}


	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getChrName() {
		return chrName;
	}
	public void setChrName(String chrName) {
		this.chrName = chrName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	
	
	
}