package mx.tecnetia.architecture.security.service.producto;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tecnetia.architecture.security.persistence.hibernate.entity.Producto;
import mx.tecnetia.architecture.security.persistence.hibernate.repository.ProductoRepository;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	ProductoRepository productoRepository;

	@Override
	public List<Producto> obtenerTodos() {
		List<Producto> lista = productoRepository.findAll();
		return lista;
	}

	@Override
	public Optional<Producto> obtenerPorId(Long id) {
		return productoRepository.findById(id);
	}

	@Override
	public Optional<Producto> obtenerPorNombre(String np) {
		return productoRepository.findByNombreProducto(np);
	}

	@Override
	public void guardar(Producto producto) {
		productoRepository.save(producto);
	}

	@Override
	public void borrar(Long id) {
		productoRepository.deleteById(id);

	}

	@Override
	public boolean existePorNombre(String np) {
		return productoRepository.existsByNombreProducto(np);
	}

	@Override
	public boolean existePorId(Long id) {
		return productoRepository.existsById(id);
	}

}
