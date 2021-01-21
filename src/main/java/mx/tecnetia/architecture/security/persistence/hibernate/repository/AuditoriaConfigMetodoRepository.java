package mx.tecnetia.architecture.security.persistence.hibernate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.tecnetia.architecture.security.persistence.hibernate.entity.aop.AuditoriaConfigMetodoEntity;

@Repository
public interface AuditoriaConfigMetodoRepository extends JpaRepository<AuditoriaConfigMetodoEntity, Integer> {
	public Optional<List<AuditoriaConfigMetodoEntity>> findByActivo(Boolean activo);
}
