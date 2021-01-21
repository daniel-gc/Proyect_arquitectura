package mx.tecnetia.architecture.security.aplicacion.negocio.service.comunicaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tecnetia.architecture.security.aplicacion.negocio.component.AplicacionVariablesComponent;
import mx.tecnetia.architecture.security.service.modulo.ModuloService;

@Service
public class CatalogosComunicacionesServiceImpl implements CatalogosComunicacionesService {
	@Autowired
	private AplicacionVariablesComponent aplicacionVariablesComponent;
	@Autowired
	private ModuloService moduloService;

	@Override
	@Transactional(readOnly = true)
	public String getURLCatalogoTodosTiposPanico() {
		String codigoModuloComunicaciones = aplicacionVariablesComponent.getCodigoModuloComunicaciones();
		String codigoComunicacionesTodoTipoPanico = aplicacionVariablesComponent
				.getCodigoComunicacionesTodoTipoPanico();
		String url = moduloService.getURL(codigoModuloComunicaciones, codigoComunicacionesTodoTipoPanico);

		return url;
	}

	@Override
	@Transactional(readOnly = true)
	public String getURLCrearPanico() {
		String codigoModuloComunicaciones = aplicacionVariablesComponent.getCodigoModuloComunicaciones();
		String codigoComunicacionesCrearPanico = aplicacionVariablesComponent
				.getCodigoComunicacionesPanicoCrearPanico();
		String url = moduloService.getURL(codigoModuloComunicaciones, codigoComunicacionesCrearPanico);

		return url;
	}

	@Override
	public String getURLEnviarEmailChat() {
		String codigoModuloComunicaciones = aplicacionVariablesComponent.getCodigoModuloComunicaciones();
		String codigoComunicacionesEnviarEmailChat = aplicacionVariablesComponent
				.getCodigoComunicacionesEnviarEmailChat();
		String url = moduloService.getURL(codigoModuloComunicaciones, codigoComunicacionesEnviarEmailChat);

		return url;
	}

}
