package in.pfe.elearning.payload.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String id;
	private String email;
	private List<String> roles;
	private String firstName;
	private String lastName;

	public JwtResponse(String accessToken, String id, String email, List<String> roles, String firstname, String lastname) {
		this.token = accessToken;
		this.id = id;

		this.email = email;
		this.roles = roles;
		this.firstName = firstname;
		this.lastName = lastname;
		
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setLastname(String lastname) {
		this.lastName = lastname;
	}
	public String getLastname() {
		return lastName;
	}
	public void setFirstname(String firstname) {
		this.firstName = firstname;
	}
	public String getFirstname() {
		return firstName;
	}

}
