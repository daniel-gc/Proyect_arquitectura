package mx.tecnetia.architecture.security.utils.afiliacion;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.AfiliadoParaFamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoAfiliadoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoFamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoMiembroDTO;
import mx.tecnetia.architecture.security.dto.NuevoMiembroArquitecturaDTO;
import mx.tecnetia.architecture.security.dto.NuevoUsuarioArquitecturaDTO;

@Component
public class AfiliadoUtilComponent {
	public NuevoAfiliadoDTO copyFromUsuarioArquitecturaToDTO(NuevoUsuarioArquitecturaDTO us) {
		NuevoAfiliadoDTO dto = new NuevoAfiliadoDTO();

		try {
			BeanUtils.copyProperties(dto, us);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dto;
	}

	public NuevoFamiliarDTO copyFromNuevoFamiliarDTOToDTO(NuevoFamiliarDTO us) {
		NuevoFamiliarDTO dto = new NuevoFamiliarDTO();

		try {
			BeanUtils.copyProperties(dto, us);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dto;
	}

	public AfiliadoParaFamiliarDTO copyFromNuevoAfiliadoDTO(NuevoAfiliadoDTO dto) {
		var nuevoDTO = new AfiliadoParaFamiliarDTO();

		try {
			BeanUtils.copyProperties(nuevoDTO, dto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nuevoDTO;
	}
	
	public NuevoMiembroDTO copyFromMiembroArquitecturaToDTO(NuevoMiembroArquitecturaDTO us) {
		NuevoMiembroDTO dto = new NuevoMiembroDTO();

		try {
			BeanUtils.copyProperties(dto, us);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dto;
	}
}
