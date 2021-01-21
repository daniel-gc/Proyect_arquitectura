package mx.tecnetia.architecture.security.service.producto;

import java.util.List;
import java.util.Optional;

import mx.tecnetia.architecture.security.persistence.hibernate.entity.Producto;

public interface ProductoService {
	public List<Producto> obtenerTodos();

	public Optional<Producto> obtenerPorId(Long id);

	public Optional<Producto> obtenerPorNombre(String np);

	public void guardar(Producto producto);

	public void borrar(Long id);

	public boolean existePorNombre(String np);

	public boolean existePorId(Long id);

}
