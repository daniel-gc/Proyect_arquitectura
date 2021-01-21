package mx.tecnetia.architecture.security.aplicacion.negocio.service.bitacora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tecnetia.architecture.security.aplicacion.negocio.component.AplicacionVariablesComponent;
import mx.tecnetia.architecture.security.service.modulo.ModuloService;

@Service
public class BitacoraServiceImpl implements BitacoraService {
	
	@Autowired
	private AplicacionVariablesComponent aplicacionVariablesComponent;
	@Autowired
	private ModuloService moduloService;

	@Override
	@Transactional(readOnly = true)
	public String getURLMetodosConfig() {
		String codigoModuloBitacora = aplicacionVariablesComponent.getCodigoModuloBitacora();
		String codigoBitacoraMetodosConfig = aplicacionVariablesComponent.getCodigoBitacoraMetodosConfig();
		String url = moduloService.getURL(codigoModuloBitacora, codigoBitacoraMetodosConfig);

		return url;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String getURLGuardarBitacora() {
		String codigoModuloBitacora = aplicacionVariablesComponent.getCodigoModuloBitacora();
		String codigoBitacoraGuardar = aplicacionVariablesComponent.getCodigoBitacoraGuardar();
		String url = moduloService.getURL(codigoModuloBitacora, codigoBitacoraGuardar);

		return url;
	}

}
