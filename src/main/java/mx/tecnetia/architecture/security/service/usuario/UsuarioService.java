package mx.tecnetia.architecture.security.service.usuario;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import mx.tecnetia.architecture.security.aplicacion.dto.general.OlvidePasswDTO;
import mx.tecnetia.architecture.security.dto.NuevoMiembroArquitecturaDTO;
import mx.tecnetia.architecture.security.dto.NuevoUsuarioArquitecturaDTO;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.UsuarioEntity;

public interface UsuarioService {
	public Optional<UsuarioEntity> getByNick(String nu);

	public boolean existePorNick(String nu);

	public boolean existePorEmail(String email);

	public Integer guardar(UsuarioEntity usuario);

	public boolean guardar(NuevoUsuarioArquitecturaDTO nuevoUsuario);
	
	public boolean guardar(NuevoMiembroArquitecturaDTO nuevoMiembro);

	public boolean existeRfc(String rfc);

	public boolean existeCurp(String curp);

	public boolean cambiaContrasena(Long idUsuario, String anterior, String nueva);

	public Boolean cambiaContrasena(@Valid OlvidePasswDTO olvidePasswDTO);

	public Boolean generaToken(@Email @NotEmpty String email);
	
	public Optional<UsuarioEntity> getByIdUser(Integer idUsuario);

}
