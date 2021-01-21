package mx.tecnetia.architecture.security.utils.servicio_rest;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import mx.tecnetia.architecture.security.aplicacion.dto.ServicioRestDTO;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.ServicioRestEntity;

@Component
public class ServicioRestUtilComponent {

	public ServicioRestDTO copyFromEntityToDto(ServicioRestEntity ent) {
		ServicioRestDTO dto = new ServicioRestDTO();
		try {
			BeanUtils.copyProperties(dto, ent);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		dto.setCodigoMetodoServicio(ent.getCodigoMetodoServicio().getCodigoMetodoServicio());

		return dto;
	}

	public List<ServicioRestDTO> copyFromEntityListToDtoList(List<ServicioRestEntity> entList) {
		List<ServicioRestDTO> dtoList = new ArrayList<>();

		entList.forEach(ent -> {
			ServicioRestDTO dto = this.copyFromEntityToDto(ent);
			dtoList.add(dto);
		});

		return dtoList;
	}

}
