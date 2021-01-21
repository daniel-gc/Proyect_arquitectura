package mx.tecnetia.architecture.security.service.modulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tecnetia.architecture.security.persistence.hibernate.entity.ModuloEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.ServicioRestEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.ModuloEntityRepository;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.ServicioRestEntityRepository;

@Service
public class ModuloServiceImpl implements ModuloService {
	@Autowired
	private ModuloEntityRepository moduloEntityRepository;
	@Autowired
	private ServicioRestEntityRepository servicioRestEntityRepository;

	@Override
	@Transactional(readOnly = true)
	public String getURL(String codigoModulo, String codigoServicioRest) {
		ModuloEntity moduloEntity = moduloEntityRepository.findByCodigo(codigoModulo.trim());
		ServicioRestEntity servicioRestEntity = servicioRestEntityRepository
				.findByCodigoAndIdModulo(codigoServicioRest.trim(), moduloEntity);
		StringBuilder url = new StringBuilder();

		url.append(moduloEntity.getHttp()).append("://").append(moduloEntity.getIp()).append(":")
				.append(moduloEntity.getPort()).append(moduloEntity.getUri()).append(servicioRestEntity.getUri());

		return url.toString();
	}

}
