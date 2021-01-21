package mx.tecnetia.architecture.security.dto;

import lombok.Data;

@Data
public class JwtBotDTO {
	
	private String token;
	private String bearer = "Bearer";
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getBearer() {
		return bearer;
	}
	public void setBearer(String bearer) {
		this.bearer = bearer;
	}
	
	
}
