package mx.tecnetia.architecture.security.api_rest_controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnetia.architecture.security.dto.MensajeDTO;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.Producto;
import mx.tecnetia.architecture.security.service.producto.ProductoService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoRestController {

	@Autowired
	ProductoService productoService;

	@GetMapping("/lista")
	public ResponseEntity<List<Producto>> getLista() {
		List<Producto> lista = productoService.obtenerTodos();
		return new ResponseEntity<List<Producto>>(lista, HttpStatus.OK);
	}

	@GetMapping("/detalle/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		if (!productoService.existePorId(id))
			return new ResponseEntity<MensajeDTO>(new MensajeDTO("no existe ese producto"), HttpStatus.NOT_FOUND);
		Producto producto = productoService.obtenerPorId(id).get();
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}

	@PostMapping("nuevo")
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public ResponseEntity<?> create(@RequestBody Producto producto) {
		if (StringUtils.isBlank(producto.getNombreProducto()))
			return new ResponseEntity<Object>(new MensajeDTO("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		if ((Integer) producto.getPrecio() == null || producto.getPrecio() == 0)
			return new ResponseEntity<Object>(new MensajeDTO("el precio es obligatorio"), HttpStatus.BAD_REQUEST);
		if (productoService.existePorNombre(producto.getNombreProducto()))
			return new ResponseEntity<Object>(new MensajeDTO("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
		productoService.guardar(producto);
		return new ResponseEntity<Object>(new MensajeDTO("producto guardado"), HttpStatus.CREATED);
	}

	@PutMapping("/actualizar/{id}")
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public ResponseEntity<MensajeDTO> update(@RequestBody Producto producto, @PathVariable("id") Long id) {
		if (!productoService.existePorId(id))
			return new ResponseEntity<MensajeDTO>(new MensajeDTO("no existe ese producto"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(producto.getNombreProducto()))
			return new ResponseEntity<MensajeDTO>(new MensajeDTO("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		if ((Integer) producto.getPrecio() == null || producto.getPrecio() == 0)
			return new ResponseEntity<MensajeDTO>(new MensajeDTO("el precio es obligatorio"), HttpStatus.BAD_REQUEST);
		if (productoService.existePorNombre(producto.getNombreProducto())
				&& productoService.obtenerPorNombre(producto.getNombreProducto()).get().getId() != id)
			return new ResponseEntity<MensajeDTO>(new MensajeDTO("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
		Producto prodUpdate = productoService.obtenerPorId(id).get();
		prodUpdate.setNombreProducto(producto.getNombreProducto());
		prodUpdate.setPrecio(producto.getPrecio());
		productoService.guardar(prodUpdate);
		return new ResponseEntity<MensajeDTO>(new MensajeDTO("producto actualizado"), HttpStatus.CREATED);
	}

	@DeleteMapping("/borrar/{id}")
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public ResponseEntity<MensajeDTO> delete(@PathVariable Long id) {
		if (!productoService.existePorId(id))
			return new ResponseEntity<MensajeDTO>(new MensajeDTO("no existe ese producto"), HttpStatus.NOT_FOUND);
		productoService.borrar(id);
		return new ResponseEntity<MensajeDTO>(new MensajeDTO("producto eliminado"), HttpStatus.OK);
	}
}
