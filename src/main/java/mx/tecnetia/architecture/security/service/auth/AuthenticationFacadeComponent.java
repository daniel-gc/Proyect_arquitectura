package mx.tecnetia.architecture.security.service.auth;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacadeComponent {
	Authentication getAuthentication();
}
