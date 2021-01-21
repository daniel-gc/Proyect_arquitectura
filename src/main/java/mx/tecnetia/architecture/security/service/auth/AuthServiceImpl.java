package mx.tecnetia.architecture.security.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import mx.tecnetia.architecture.security.dto.JwtBotDTO;
import mx.tecnetia.architecture.security.dto.JwtDTO;
import mx.tecnetia.architecture.security.dto.LoginUsuarioDTO;
import mx.tecnetia.architecture.security.dto.NuevoUsuarioArquitecturaDTO;
import mx.tecnetia.architecture.security.model.jwt.JwtProvider;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.UsuarioEntityRepository;
import mx.tecnetia.architecture.security.utils.AES;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private UsuarioEntityRepository usuarioEntityRepository;
	@Autowired
	JwtProvider jwtProvider;
	
	private String user = "1t6O9W79baf5rVRS2ThURoxQOsjI7YuTeoZGBZ5eECA=";//user pablo
	private String pws = "7vVk0Nbq3aHzspF/UkibwA==";// pass-pablo

	@Override
	public void crearNuevoUsuario(NuevoUsuarioArquitecturaDTO nuevoUsuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public JwtDTO login(LoginUsuarioDTO loginUsuario) {

		try {
			var usuarioEntity = this.usuarioEntityRepository.findByNick(loginUsuario.getNick());

			if (usuarioEntity != null && usuarioEntity.getActivo()) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginUsuario.getNick(), loginUsuario.getPassw()));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtProvider.generateToken(authentication);
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				JwtDTO jwtDTO = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());

				return jwtDTO;
			}

			return null;
		}catch (Exception e) {
			return null;
		}
		
	}
	
	@Override
	public JwtBotDTO getNewToken() {
		String secret = "estelll!!!!!!gdt65";
		
		try {
			
			var usuarioEntity = this.usuarioEntityRepository.findByNick(AES.decrypt(user, secret));

			if (usuarioEntity != null && usuarioEntity.getActivo()) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(AES.decrypt(user, secret), AES.decrypt(pws, secret)));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtProvider.generateToken(authentication);
				JwtBotDTO jwtDTO = new JwtBotDTO();
				jwtDTO.setToken(jwt);

				return jwtDTO;
			}

			return null;
		}catch (Exception e) {
			return null;
		}
		
	}

}
