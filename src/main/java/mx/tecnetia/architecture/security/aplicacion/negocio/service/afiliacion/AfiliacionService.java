package mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;

import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.AfiliadoParaFamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.EmpleadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FiniquitoCalculoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.FiniquitoDetalleDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoAfiliadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoFamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.BoletoBwigoDTO;

public interface AfiliacionService {
	public EmpleadoDTO getEmpleado(Integer idEmpresa, String contrato);

	String getURLExisteEmpleado();

	String getURLExisteAfiliadoRedSocial();

	String getURLAfiliadoRedSocial();

	public NuevoAfiliadoDTO getAfiliadoRedSocial(String idFacebook, String idWhatsapp);

	public boolean nuevoFamiliar(NuevoFamiliarDTO nuevoFamiliarDTO);

	public boolean confirmarFamiliar(String fam, boolean conf);

	public ResponseEntity<List<FamiliarDTO>> todosLosFamiliares(Integer arqIdUsuario);

	public boolean cancelarFamiliar(Integer idVinculoFamiliar);

	public void enviaEmailNuevoAfiliado(String email, Integer numeroAfiliado);

	public AfiliadoParaFamiliarDTO getAfiliadoIdAfiliado(@NotNull Integer idAfiliado);
	
	public FiniquitoDetalleDTO calcularFiniquito(FiniquitoCalculoDTO finiquitoCalculoDTO);
	
	public BoletoBwigoDTO solicitarBoletoCine(String tipo, Integer arqIdUsuario, String arqEmailUsuario);
	
	public Boolean enviaEmailBoletoCine(String arqEmailUsuario, String folio, String vigencia);
	public NuevoAfiliadoDTO getAfiliadoPorCurp(String curp);
	public NuevoAfiliadoDTO getAfiliadoPorIdUserArq(Integer idUserArq);
}
