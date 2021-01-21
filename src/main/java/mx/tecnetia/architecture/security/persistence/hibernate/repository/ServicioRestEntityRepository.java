package mx.tecnetia.architecture.security.persistence.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.tecnetia.architecture.security.persistence.hibernate.entity.ModuloEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.ServicioRestEntity;

@Repository
public interface ServicioRestEntityRepository extends JpaRepository<ServicioRestEntity, Integer> {

	public ServicioRestEntity findByCodigoAndIdModulo(String codigo, ModuloEntity idModulo);

	public List<ServicioRestEntity> findByIdModulo(ModuloEntity idModulo);
}
