package mx.tecnetia.architecture.security.persistence.hibernate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.tecnetia.architecture.security.persistence.hibernate.entity.AplicacionEntity;

@Repository
public interface AplicacionEntityRepository extends JpaRepository<AplicacionEntity, Integer> {
	public Optional<AplicacionEntity> findByCodigo(String codigo);
}
