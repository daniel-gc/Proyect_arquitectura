package mx.tecnetia.architecture.security.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import mx.tecnetia.architecture.security.model.basic.BsicAuthenticationEntryPoint;

@EnableWebSecurity
@Configuration
@Order(1)
public class WebSecurityBasic extends WebSecurityConfigurerAdapter {

	public static final String REALM_NAME = "PLIIS";

	@Autowired
	BsicAuthenticationEntryPoint bsicAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		csrf().disable()
		.authorizeRequests().antMatchers("/notificacion/**").authenticated().and().httpBasic() //anyRequest().permitAll().and()
				.realmName(REALM_NAME).authenticationEntryPoint(bsicAuthenticationEntryPoint)
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("PLIIS").password(
				"$2a$10$PVpPx3TGbJL8IHoHXO2HE.vPOSwuvyhrjhXkLZQLgqAJxbo94Vti." //5InDiKt0_PL115
				).roles("USER");
	}
}
