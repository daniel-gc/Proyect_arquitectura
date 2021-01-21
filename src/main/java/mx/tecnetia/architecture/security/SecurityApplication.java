package mx.tecnetia.architecture.security;

import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion.CreditoService;
import mx.tecnetia.architecture.security.aplicacion.negocio.service.afiliacion.MiembroService;

@SpringBootApplication
@EnableScheduling
public class SecurityApplication {
	
	@Autowired
	private MiembroService miembroService;
	@Autowired
	private CreditoService creditoService;
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Cron cada minuto
	//@Scheduled(cron = "0 * * * * ?", zone="America/Mexico_City")
	//Cron cada dia a las 00:00:01 AM
	@Scheduled(cron = "1 0 0 * * ?", zone="America/Mexico_City")
    public void validarSuscripciones() {
        miembroService.validarSuscripciones();
    }
	
	@Scheduled(cron = "1 1 0 * * ?", zone="America/Mexico_City")
    public void enviaConcentradoSolicitudes() {
		creditoService.enviaConsentradoSolicitudesCredito();
    }
}
