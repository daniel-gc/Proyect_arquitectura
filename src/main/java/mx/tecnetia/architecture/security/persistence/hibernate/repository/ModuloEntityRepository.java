package mx.tecnetia.architecture.security.persistence.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.tecnetia.architecture.security.persistence.hibernate.entity.ModuloEntity;

@Repository
public interface ModuloEntityRepository extends JpaRepository<ModuloEntity, Long> {
	public ModuloEntity findByCodigo(String codigo);
}
