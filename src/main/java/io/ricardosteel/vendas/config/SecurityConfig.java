package io.ricardosteel.vendas.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.ricardosteel.vendas.serviceimpl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UsuarioServiceImpl usuarioServiceImpl;
	private final BCryptPasswordEncoder passwordEncoder;

	private static final String ADMIN = "ADMIN";

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().passwordEncoder(encoder()).withUser("fulano").password(encoder().encode("123"))
//				.roles("USER", ADMIN);
		auth.userDetailsService(usuarioServiceImpl).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/api/clientes/**").hasAnyRole("USER", ADMIN)
				.antMatchers("/api/produtos/**").hasRole(ADMIN).antMatchers("/api/pedidos/**").hasAnyRole("USER", ADMIN)
				.and().httpBasic();
	}
}
