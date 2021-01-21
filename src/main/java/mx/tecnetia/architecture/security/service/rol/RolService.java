package mx.tecnetia.architecture.security.service.rol;

import java.util.Optional;

import mx.tecnetia.architecture.security.persistence.hibernate.entity.RolAplicacionEntity;

public interface RolService {

	public Optional<RolAplicacionEntity> getByRolNombre(String rolNombre);
}
