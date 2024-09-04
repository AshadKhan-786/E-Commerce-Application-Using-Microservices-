package in.ashokit.bindings;

public class UserResponse {

	private Integer status;
	private String validLogin;
	private String token;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getValidLogin() {
		return validLogin;
	}
	public void setValidLogin(String validLogin) {
		this.validLogin = validLogin;
	}
	
}
