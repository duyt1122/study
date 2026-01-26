package vn.hoidanit.jobhunter.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;

import vn.hoidanit.jobhunter.util.SecurityUtil;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

	private final SecurityUtil securityUtil;

	public SecurityConfiguration(SecurityUtil securityUtil) {
		this.securityUtil = securityUtil;
	}

	@Value("${hoidanit.jwt.base64-secret}")
	private String jwtKey;

	@Value("${hoidanit.jwt.token-validity-in-seconds}")
	private String jwtExpiration;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
//
//	@Bean
//	public UserDetailsService userDetailsService(UserService userService) {
//		return new UserDetailsCustom(userService);
//	}
//
//	@Bean
//	public DaoAuthenticationProvider authDao(PasswordEncoder passwordEncoder, UserDetailsCustom userDetailsCustom) {
//		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
//		dao.setUserDetailsService(userDetailsCustom);
//		dao.setPasswordEncoder(passwordEncoder);
//		return dao;
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(f -> f.disable())
				.authorizeHttpRequests(authz -> authz.requestMatchers("/").permitAll().anyRequest().permitAll())

				.formLogin(f -> f.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));// thông
																												// báo
		return http.build();
	}

	private SecretKey getSecretKey() {
		byte[] keyBytes = Base64.from(jwtKey).decode();
		return new SecretKeySpec(keyBytes, 0, keyBytes.length, securityUtil.JWT_ALGORITHM.getName());
	}

	@Bean
	public JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
	}

}
