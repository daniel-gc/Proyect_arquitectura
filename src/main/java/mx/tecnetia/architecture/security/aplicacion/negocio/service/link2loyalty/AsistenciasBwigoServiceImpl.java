package mx.tecnetia.architecture.security.aplicacion.negocio.service.link2loyalty;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aplicacion.dto.afiliacion.NuevoFamiliarDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.ActivaUsuariosResponseBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.BeneficiarioBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.BoletoBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.BoletoResponseBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.CargaUsuariosResponceDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.TokenBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty.UsuarioBwigoDTO;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.general.ParametroConfiguracionService;
import mx.tecnetia.architecture.security.dto.NuevoMiembroArquitecturaDTO;
import mx.tecnetia.architecture.security.dto.NuevoUsuarioArquitecturaDTO;

@Service
@Log4j2
public class AsistenciasBwigoServiceImpl implements AsistenciasBwigoService {
	
	@Autowired
	private ParametroConfiguracionService parametroConfiguracionService;
	
	@Override
	public TokenBwigoDTO generaToken(String endpointGeneraTokenBWIGO, String genTokenUsuario, String genTokenPassword, TokenBwigoDTO tokenBwigoDTO){
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("usr", genTokenUsuario);
			map.add("psw", genTokenPassword);
			
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
			
			ResponseEntity<TokenBwigoDTO> response = restTemplate.exchange(endpointGeneraTokenBWIGO, HttpMethod.POST,entity,TokenBwigoDTO.class);
			
			if (response.getStatusCode() == HttpStatus.OK) {
				tokenBwigoDTO = response.getBody();
				
				Integer err = tokenBwigoDTO.getErr();
				String men = tokenBwigoDTO.getMen();
				
				log.info("Respuesta de servicio de ReactivaFolios BWIGO, error: {}, mensaje: {}", err, men);
				return tokenBwigoDTO;
			}else {
				return null;
			}
		}catch (Exception e) {
			log.error("Al intentar generar el token de BWIGO, ocurrió el siguiente error: {}", e.getLocalizedMessage());			
			return null;
		}
	}
	

	@Override
	public CargaUsuariosResponceDTO cargaUsuarioABwigo(String endpointCargaUsuariosBWIGO, TokenBwigoDTO tokenBwigoDTO, String jsonCargausuarios, CargaUsuariosResponceDTO cargaUsuariosResponceDTO){
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("token", tokenBwigoDTO.getSes());
			map.add("cad", jsonCargausuarios);
			
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
			
			ResponseEntity<String> response = restTemplate.exchange(endpointCargaUsuariosBWIGO, HttpMethod.POST,entity,String.class);
			
			if (response.getStatusCode() == HttpStatus.OK) {
				JSONObject boletoBwigoJson = new JSONObject(response.getBody());

				Integer err = boletoBwigoJson.getInt("err");
				String men = boletoBwigoJson.getString("men");
				cargaUsuariosResponceDTO = new CargaUsuariosResponceDTO();//response.getBody();
				
				//Integer err = cargaUsuariosResponceDTO.getErr();
				//String men = cargaUsuariosResponceDTO.getMen();
				
				log.info("Respuesta de servicio de ReactivaFolios BWIGO, error: {}, mensaje: {}", err, men);
				return cargaUsuariosResponceDTO;
			}else {
				return null;
			}
		}catch (Exception e) {
			log.error("Al intentar cargar el usuario a BWIGO, ocurrió el siguiente error: {}", e.getLocalizedMessage());			
			return null;
		}
	}
	
	@Override
	public ActivaUsuariosResponseBwigoDTO activaUsuariosBwigo(String endpointReactivaFoliosBWIGO, TokenBwigoDTO tokenBwigoDTO, Integer idUsuarioArq){
		ActivaUsuariosResponseBwigoDTO activaUsuariosResponseBwigoDTO = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("token", tokenBwigoDTO.getSes());
			map.add("cad", idUsuarioArq.toString());
			
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
			
			ResponseEntity<ActivaUsuariosResponseBwigoDTO> response = restTemplate.exchange(endpointReactivaFoliosBWIGO, HttpMethod.POST, entity, ActivaUsuariosResponseBwigoDTO.class);
			
			if (response.getStatusCode() == HttpStatus.OK) {
				activaUsuariosResponseBwigoDTO = response.getBody();
				Integer err = activaUsuariosResponseBwigoDTO.getErr();
				String men = activaUsuariosResponseBwigoDTO.getMen();
				
				log.info("Respuesta de servicio de ReactivaFolios BWIGO, error: {}, mensaje: {}", err, men);
			}
			
			return activaUsuariosResponseBwigoDTO;
		}catch (Exception e) {
			log.error("Al intentar activar el usuario en BWIGO, ocurrió el siguiente error: {}", e.getLocalizedMessage());			
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public BoletoResponseBwigoDTO canjeBoletoBwigo(String endpointCanjeBoletoBWIGO, String tokenCanjeBoletoBWIGO, Integer idUsuarioArq, LocalDate fechaNacimiento) {
		BoletoResponseBwigoDTO boletoResponseBwigoDTO = null;
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		//map.add("fol", "203");
//		map.add("ono", "1910-01-01");
		
		map.add("fol", idUsuarioArq.toString());
		map.add("ono", fechaNacimiento.toString());
		map.add("ky", tokenCanjeBoletoBWIGO);
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(endpointCanjeBoletoBWIGO, HttpMethod.POST, entity, String.class);
		//ResponseEntity<BoletoResponseBwigoDTO> response = restTemplate.exchange(endpointCanjeBoletoBWIGO, HttpMethod.POST, entity, BoletoResponseBwigoDTO.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			JSONObject boletoBwigoJson = new JSONObject(response.getBody());
			Integer err = boletoBwigoJson.getInt("err");
			String men = boletoBwigoJson.getString("men");
			Boolean val = boletoBwigoJson.getBoolean("val");
			String ses = boletoBwigoJson.getString("ses");
			Integer ite = boletoBwigoJson.getInt("ite");
			
			JSONObject Canje = boletoBwigoJson.getJSONObject("Canje");
			String fondo = Canje.getString("fondo");
			String folio = Canje.getString("folio");
			String codigo = Canje.getString("codigo");
			String cbarras = Canje.getString("cbarras");
			String vigenciaStr = Canje.getString("vigencia");
			String ultcanjeStr = Canje.getString("ultcanje");
			LocalDate vigencia = null;
			LocalDate ultcanje = null;
			if(vigenciaStr != "null" && ultcanjeStr != "null") {
				vigencia = LocalDate.parse(vigenciaStr);
				ultcanje = LocalDate.parse(ultcanjeStr);
			}
			
			boletoResponseBwigoDTO = new BoletoResponseBwigoDTO();
			boletoResponseBwigoDTO.setErr(err);
			boletoResponseBwigoDTO.setMen(men);
			boletoResponseBwigoDTO.setVal(val);
			boletoResponseBwigoDTO.setSes(ses);
			boletoResponseBwigoDTO.setIte(ite);
			
			BoletoBwigoDTO boletoBwigoDTO = new BoletoBwigoDTO();
			boletoBwigoDTO.setFondo(fondo);
			boletoBwigoDTO.setFolio(folio);
			boletoBwigoDTO.setCodigo(codigo);
			boletoBwigoDTO.setCbarras(cbarras);
			boletoBwigoDTO.setVigencia(vigencia);
			boletoBwigoDTO.setUltcanje(ultcanje);
			
			if(boletoResponseBwigoDTO.getErr() != 0) {
				boletoBwigoDTO.setError(err);
				boletoBwigoDTO.setMsgError(men);
			}
			
			boletoResponseBwigoDTO.setCanje(boletoBwigoDTO);
			
			log.info("Respuesta de servicio de CanjeBoleto BWIGO, error: {}, mensaje: {}", err, men);
			
			return boletoResponseBwigoDTO;
		} else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
			log.error("No se pudo realizar la solicitud del boleto de cine.");
			throw new IllegalArgumentException("No se pudo realizar la solicitud del boleto de cine.");
		}
		return null;
//		ResponseEntity<BoletoResponseBwigoDTO> response = restTemplate.exchange(endpointCanjeBoletoBWIGO, HttpMethod.POST, entity, BoletoResponseBwigoDTO.class);
//		
//		if (response.getStatusCode() == HttpStatus.OK) {
//			boletoResponseBwigoDTO = response.getBody();
//			
//			if(boletoResponseBwigoDTO.getErr() != 0) {
//				log.error(boletoResponseBwigoDTO.getMen());
//				throw new IllegalArgumentException(boletoResponseBwigoDTO.getMen());
//			}else {
//				return boletoResponseBwigoDTO;
//			}
//		}else {
//			return null;
//		}
	}
	
	@Override
	public Boolean updateBeneficiarioBwigo(String endpointUpdBeneficiariosBWIGO, TokenBwigoDTO tokenBwigoDTO, Integer idUsuarioArq, NuevoFamiliarDTO nuevoFamiliarDTO){
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			
			String genero = obtenerGeneroBWIGO(nuevoFamiliarDTO.getIdSexo());
			String tipoFamiliar = this.obtenerParentescoBWIGO(nuevoFamiliarDTO.getIdRelacionFamiliar());
			
			map.add("token", tokenBwigoDTO.getSes());
			map.add("numben", "0");
			map.add("fol", idUsuarioArq.toString());
			map.add("idben", "0");
			map.add("nom", nuevoFamiliarDTO.getNombres());
			map.add("pat", nuevoFamiliarDTO.getApellidoPaterno());
			map.add("mat", nuevoFamiliarDTO.getApellidoMaterno());
			map.add("gen", genero);
			map.add("ono", nuevoFamiliarDTO.getFechaNacimiento().toString());
			map.add("tip", tipoFamiliar);
			map.add("por", "5");
			map.add("tel", nuevoFamiliarDTO.getTelefono());
			map.add("ema", nuevoFamiliarDTO.getEmail());
			
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
			
			ResponseEntity<String> response = restTemplate.exchange(endpointUpdBeneficiariosBWIGO, HttpMethod.POST, entity, String.class);
			
			if (response.getStatusCode() == HttpStatus.OK) {
				JSONObject boletoBwigoJson = new JSONObject(response.getBody());
				Integer err = boletoBwigoJson.getInt("err");
				String men = boletoBwigoJson.getString("men");
				
				log.info("Respuesta de servicio de UpdBeneficiarios BWIGO, error: {}, mensaje: {}", err, men);
				
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
			log.error("Al intentar Actualizar el beneficiario a BWIGO, ocurrió el siguiente error: {}", e.getLocalizedMessage());			
			return false;
		}
	}
	
	@Override
	public String generaJsonUsuarioBwigo(
		Integer idUsuarioArq, 
		String tipo, 
		NuevoUsuarioArquitecturaDTO nuevoUsuario,
		NuevoFamiliarDTO nuevoFamiliarDTO,
		NuevoMiembroArquitecturaDTO nuevoMiembro
	){
		try {
		
			JSONObject jsonObject = new JSONObject();
			String jsonCargausuarios = null;
			if(tipo == "AS" || tipo == "UE") {
				UsuarioBwigoDTO usuarioBwigoDTO = new UsuarioBwigoDTO();	
				
				if(nuevoUsuario != null) {
					String genero = obtenerGeneroBWIGO(nuevoUsuario.getIdSexo());
					
					usuarioBwigoDTO.setNumben(0);
					usuarioBwigoDTO.setFol(idUsuarioArq);
					usuarioBwigoDTO.setNom(nuevoUsuario.getNombres());
					usuarioBwigoDTO.setPat(nuevoUsuario.getApellidoPaterno());
					usuarioBwigoDTO.setMat(nuevoUsuario.getApellidoMaterno());
					usuarioBwigoDTO.setGen(genero);
					usuarioBwigoDTO.setOno(nuevoUsuario.getFechaNacimiento().toString());
					usuarioBwigoDTO.setRfc(nuevoUsuario.getRfc());
					usuarioBwigoDTO.setEma(nuevoUsuario.getEmail());
					usuarioBwigoDTO.setAlta(nuevoUsuario.getFechaRegistro().toString());
					usuarioBwigoDTO.setEmpresa(nuevoUsuario.getNombreSindicato());
				} else if(nuevoMiembro != null) {
					String genero = obtenerGeneroBWIGO(nuevoMiembro.getIdSexo());
					
					usuarioBwigoDTO.setNumben(0);
					usuarioBwigoDTO.setFol(idUsuarioArq);
					usuarioBwigoDTO.setNom(nuevoMiembro.getNombres());
					usuarioBwigoDTO.setPat(nuevoMiembro.getApellidoPaterno());
					usuarioBwigoDTO.setMat(nuevoMiembro.getApellidoMaterno());
					usuarioBwigoDTO.setGen(genero);
					usuarioBwigoDTO.setOno(nuevoMiembro.getFechaNacimiento().toString());
					usuarioBwigoDTO.setRfc(nuevoMiembro.getRfc());
					usuarioBwigoDTO.setEma(nuevoMiembro.getEmail());
					usuarioBwigoDTO.setAlta(nuevoMiembro.getFechaRegistro().toString());
					usuarioBwigoDTO.setEmpresa(nuevoMiembro.getNombreSindicato());
				}
				
//				jsonObject.put("numben", usuarioBwigoDTO.getNumben().toString());
//				jsonObject.put("fol", usuarioBwigoDTO.getFol());
//				jsonObject.put("nom", usuarioBwigoDTO.getNom());
//				jsonObject.put("pat", usuarioBwigoDTO.getPat());
//				jsonObject.put("mat", usuarioBwigoDTO.getMat());
//				jsonObject.put("gen", usuarioBwigoDTO.getGen());
//				jsonObject.put("ono", usuarioBwigoDTO.getOno());
//				jsonObject.put("rfc", usuarioBwigoDTO.getRfc());
//				jsonObject.put("ema", usuarioBwigoDTO.getEma());
//				jsonObject.put("alta", usuarioBwigoDTO.getAlta());
//				jsonObject.put("empresa", usuarioBwigoDTO.getEmpresa());
				
				jsonCargausuarios = 
						"{"
						+ "\"Usuarios\": ["
						+ "	{"
						+ "		\"numben\": 0,"
						+ "		\"fol\": \"" + usuarioBwigoDTO.getFol() + "\","
						+ "		\"nom\": \"" + usuarioBwigoDTO.getNom() + "\","
						+ "		\"pat\": \"" + usuarioBwigoDTO.getPat() + "\","
						+ "		\"mat\": \"" + usuarioBwigoDTO.getMat() + "\","
						+ "		\"gen\": \"" + usuarioBwigoDTO.getGen() + "\","
						+ "		\"ono\": \"" + usuarioBwigoDTO.getOno() + "\","
						+ "		\"rfc\": \"" + usuarioBwigoDTO.getRfc() + "\","
						+ "		\"ema\": \"" + usuarioBwigoDTO.getEma() + "\","
						+ "		\"alta\": \"" + usuarioBwigoDTO.getAlta() + "\","
						+ "		\"empresa\": \"" + usuarioBwigoDTO.getEmpresa() + "\""
						+ "	}"
						+ "]"
						+ "}";
				
			}else if(tipo == "FA") {
				BeneficiarioBwigoDTO beneficiarioBwigoDTO = new BeneficiarioBwigoDTO();
				
				String tipoFamiliar = this.obtenerParentescoBWIGO(nuevoFamiliarDTO.getIdRelacionFamiliar());
				String genero = obtenerGeneroBWIGO(nuevoFamiliarDTO.getIdSexo());
				
				beneficiarioBwigoDTO.setFol(nuevoFamiliarDTO.getIdAfiliado());
				beneficiarioBwigoDTO.setNom(nuevoFamiliarDTO.getNombres());
				beneficiarioBwigoDTO.setPat(nuevoFamiliarDTO.getApellidoPaterno());
				beneficiarioBwigoDTO.setMat(nuevoFamiliarDTO.getApellidoMaterno());
				beneficiarioBwigoDTO.setGen(genero);
				beneficiarioBwigoDTO.setOno(nuevoFamiliarDTO.getFechaNacimiento().toString());
				beneficiarioBwigoDTO.setTip(tipoFamiliar);
				beneficiarioBwigoDTO.setPor(5);
				beneficiarioBwigoDTO.setTel(nuevoFamiliarDTO.getTelefono());
				beneficiarioBwigoDTO.setEma(nuevoFamiliarDTO.getEmail());
				
				jsonObject.put("fol", beneficiarioBwigoDTO.getFol());
				jsonObject.put("nom", beneficiarioBwigoDTO.getNom());
				jsonObject.put("pat", beneficiarioBwigoDTO.getPat());
				jsonObject.put("mat", beneficiarioBwigoDTO.getMat());
				jsonObject.put("gen", beneficiarioBwigoDTO.getGen());
				jsonObject.put("ono", beneficiarioBwigoDTO.getOno());
				jsonObject.put("tip", beneficiarioBwigoDTO.getTip());
				jsonObject.put("por", beneficiarioBwigoDTO.getPor());
				jsonObject.put("tel", beneficiarioBwigoDTO.getTel());
				jsonObject.put("ema", beneficiarioBwigoDTO.getEma());
				
				JSONArray jsonArray = new JSONArray();
			    jsonArray.put(jsonObject);
			    
			    JSONObject mainObj = new JSONObject();
			    mainObj.put("beneficiarios", jsonArray);
			}
//			JSONArray jsonArray = new JSONArray();
//		    jsonArray.put(jsonObject);
//		    
//		    JSONObject mainObj = new JSONObject();
//		    mainObj.put("Usuarios", jsonArray);
		    
			return jsonCargausuarios;
		}catch (Exception e) {
			log.error("Al intentar armar el usuarioBwigoJsonObject para carga a BWIGO, ocurrió el siguiente error: {}", e.getLocalizedMessage());			
			return null;
		}
	}
	
	private String obtenerGeneroBWIGO(Integer idSexo) {
		String genero = null;
		switch (idSexo) {
			case 1://1	Masculino
				genero = "M";
				break;
			case 2://2	Femenino
				genero = "F";
				break;
			case 3://3	Otro
				genero = "O";
				break;
			case 4://4	Prefiero no decir
				genero = "P";
				break;
			default:
				genero = "";
				break;
		}
		
		return genero;
	}
	
	private String obtenerParentescoBWIGO(Integer idFamiliar) {
		String tipoFamiliar = null;
		switch (idFamiliar) {
			case 1://1	Cónyuge o concubino(a)
				tipoFamiliar = "ESPOSO(A)";
				break;
			case 2://2	Hijo(a) soltero(a)
				tipoFamiliar = "HIJO(A)";
				break;
			case 3://3	Padre
				tipoFamiliar = "PADRES";
				break;
			case 4://4	Madre
				tipoFamiliar = "PADRES";
				break;
			case 5://5	Suegra o hermano(a)
				tipoFamiliar = "HERMANO(A)";
				break;
			case 6://6	Suegro o hermano(a)
				tipoFamiliar = "HERMANO(A)";
				break;
			case 7://7	Yerno
				tipoFamiliar = "AMIGO(A)";
				break;
			case 8://8	Nuera
				tipoFamiliar = "AMIGO(A)";
				break;
			case 9://9	Primo(a)
				tipoFamiliar = "AMIGO(A)";
				break;
			default:
				tipoFamiliar = "";
				break;
		}
		return tipoFamiliar;
//		ID's BWIGO--------------------------IDFAMILIAR
////	{"id": 1, "des": "ESPOSO(A)"},		1
////	{"id": 2, "des": "HIJO(A)"},		2
////	{"id": 3, "des": "PADRES"},			3,4
////	{"id": 4, "des": "ABUELOS"},		
////	{"id": 5, "des": "HERMANO(A)"},		5,6
////	{"id": 6, "des": "TIO(A)"},			
////	{"id": 7, "des": "SOBRINO(A)"},		
////	{"id": 8, "des": "PRIMO(A)"},		9
////	{"id": 9, "des": "NIETO(A)"},		
////	{"id": 10, "des": "AMIGO(A)"},		7,8
////	{"id": 11, "des": "SIN PARENTESCO"}	
	}
	
//	@Override
//	@Transactional(readOnly = true)
//	public void obtenerParametrosBoletos() {
//		
//	}
}
