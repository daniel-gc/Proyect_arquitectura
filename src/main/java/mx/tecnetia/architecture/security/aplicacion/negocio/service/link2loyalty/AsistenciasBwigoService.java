package mx.tecnetia.architecture.security.aplicacion.negocio.service.link2loyalty;

import java.time.LocalDate;

import org.json.JSONObject;

import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoFamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.ActivaUsuariosResponseBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.BoletoResponseBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.CargaUsuariosResponceDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.TokenBwigoDTO;
import mx.tecnetia.architecture.security.dto.NuevoMiembroArquitecturaDTO;
import mx.tecnetia.architecture.security.dto.NuevoUsuarioArquitecturaDTO;

public interface AsistenciasBwigoService {
	public TokenBwigoDTO generaToken(String endpointGeneraTokenBWIGO, String genTokenUsuario, String genTokenPassword, TokenBwigoDTO tokenBwigoDTO);
	
	public String generaJsonUsuarioBwigo(Integer idUsuarioArq, String tipo, NuevoUsuarioArquitecturaDTO nuevoUsuario, NuevoFamiliarDTO nuevoFamiliarDTO,NuevoMiembroArquitecturaDTO nuevoMiembro);
	
	public CargaUsuariosResponceDTO cargaUsuarioABwigo(String endpointCargaUsuariosBWIGO, TokenBwigoDTO tokenBwigoDTO, String jsonCargausuarios, CargaUsuariosResponceDTO cargaUsuariosResponceDTO);
	
	public ActivaUsuariosResponseBwigoDTO activaUsuariosBwigo(String endpointReactivaFoliosBWIGO, TokenBwigoDTO tokenBwigoDTO, Integer idUsuarioArq);
	
	public Boolean updateBeneficiarioBwigo(String endpointUpdBeneficiariosBWIGO, TokenBwigoDTO tokenBwigoDTO, Integer idUsuarioArq, NuevoFamiliarDTO nuevoFamiliarDTO);
	
	public BoletoResponseBwigoDTO canjeBoletoBwigo(String endpointCanjeBoletoBWIGO, String tokenCanjeBoletoBWIGO, Integer idUsuarioArq, LocalDate fechaNacimiento);
}
