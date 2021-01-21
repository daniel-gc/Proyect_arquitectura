package mx.tecnetia.architecture.security.persistence.hibernate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.tecnetia.architecture.security.persistence.hibernate.entity.UsuarioEntity;

@Repository
public interface UsuarioEntityRepository extends JpaRepository<UsuarioEntity, Integer> {
	UsuarioEntity findByNick(String nick);

	boolean existsByNick(String nick);

	boolean existsByEmail(String email);

	Optional<UsuarioEntity> findByEmail(String email);

}