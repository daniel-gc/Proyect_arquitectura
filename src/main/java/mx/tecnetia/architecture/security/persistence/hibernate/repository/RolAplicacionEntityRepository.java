package mx.tecnetia.architecture.security.persistence.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.tecnetia.architecture.security.persistence.hibernate.entity.RolAplicacionEntity;

@Repository
public interface RolAplicacionEntityRepository extends JpaRepository<RolAplicacionEntity, Integer> {
	RolAplicacionEntity findByNombre(String nombre);

}
