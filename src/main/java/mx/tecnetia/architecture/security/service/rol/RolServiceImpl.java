package mx.tecnetia.architecture.security.service.rol;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tecnetia.architecture.security.persistence.hibernate.entity.RolAplicacionEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.RolAplicacionEntityRepository;

@Service
@Transactional
public class RolServiceImpl implements RolService {

	@Autowired
	RolAplicacionEntityRepository rolRepository;

	@Override
	public Optional<RolAplicacionEntity> getByRolNombre(String rolNombre) {
		RolAplicacionEntity rol = rolRepository.findByNombre(rolNombre);

		return Optional.ofNullable(rol);
	}

}
