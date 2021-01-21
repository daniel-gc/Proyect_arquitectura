package mx.tecnetia.architecture.security.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtDTO {
	private String token;
	private String bearer = "Bearer";
	private String nick;
	private Collection<? extends GrantedAuthority> authorities;

	public JwtDTO(String token, String nick, Collection<? extends GrantedAuthority> authorities) {
		this.token = token;
		this.nick = nick;
		this.authorities = authorities;
	}

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

	public String getNick() {
		return nick;
	}

	public void setNick(String nombreUsuario) {
		this.nick = nombreUsuario;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}