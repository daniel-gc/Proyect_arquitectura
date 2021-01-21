package mx.tecnetia.architecture.security.service.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tecnetia.architecture.security.model.UsuarioPrincipal;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.UsuarioEntity;
import mx.tecnetia.architecture.security.service.usuario.UsuarioService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UsuarioService usuarioService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
		UsuarioEntity usuario = usuarioService.getByNick(nombreUsuario).get();
		return UsuarioPrincipal.build(usuario);
	}

}
