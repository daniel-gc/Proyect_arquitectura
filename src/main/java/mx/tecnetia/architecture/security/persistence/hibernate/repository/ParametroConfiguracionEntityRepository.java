package mx.tecnetia.architecture.security.persistence.hibernate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.tecnetia.architecture.security.persistence.hibernate.entity.ParametroConfiguracionEntity;

public interface ParametroConfiguracionEntityRepository extends JpaRepository<ParametroConfiguracionEntity, Integer> {
	
	public Optional<ParametroConfiguracionEntity> findByIdParametro(Integer idParametro);
	
	public Optional<ParametroConfiguracionEntity> findByClave(String clave);
	
	public Optional<List<ParametroConfiguracionEntity>> findByTipo(String tipo);
	
	public Optional<List<ParametroConfiguracionEntity>> findByActivo(Boolean activo);
}
