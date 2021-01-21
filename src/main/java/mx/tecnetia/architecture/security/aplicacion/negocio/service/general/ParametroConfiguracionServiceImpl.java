package mx.tecnetia.architecture.security.aplicacion.negocio.service.general;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.ParametroConfiguracionEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.ParametroConfiguracionEntityRepository;

@Service
@Log4j2
public class ParametroConfiguracionServiceImpl implements ParametroConfiguracionService {
	@Autowired
	private ParametroConfiguracionEntityRepository parametroConfiguracionEntityRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, String> getParametros() {
		Optional<List<ParametroConfiguracionEntity>> listaParametros = parametroConfiguracionEntityRepository.findByActivo(true);
		Map<String, String> parametros = new HashMap<String, String>();
		transformarLista(parametros, listaParametros.get());
		return parametros;
	}
	
	public void transformarLista(Map<String, String> parametros, List<ParametroConfiguracionEntity> listaParametros){
		for(ParametroConfiguracionEntity registro : listaParametros) {
			parametros.put(registro.getClave(), registro.getValor());
		}
	}
}
