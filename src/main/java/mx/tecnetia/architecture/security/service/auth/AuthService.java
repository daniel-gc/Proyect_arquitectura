package mx.tecnetia.architecture.security.service.auth;

import mx.tecnetia.architecture.security.dto.JwtBotDTO;
import mx.tecnetia.architecture.security.dto.JwtDTO;
import mx.tecnetia.architecture.security.dto.LoginUsuarioDTO;
import mx.tecnetia.architecture.security.dto.NuevoUsuarioArquitecturaDTO;

public interface AuthService {
	public void crearNuevoUsuario(NuevoUsuarioArquitecturaDTO nuevoUsuario);

	public JwtDTO login(LoginUsuarioDTO loginUsuario);
	
	public JwtBotDTO getNewToken();
}
