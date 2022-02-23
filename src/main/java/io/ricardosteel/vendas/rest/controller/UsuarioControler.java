package io.ricardosteel.vendas.rest.controller;

import javax.validation.Valid;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ricardosteel.vendas.domain.entity.Usuario;
import io.ricardosteel.vendas.rest.dto.CredenciaisDTO;
import io.ricardosteel.vendas.rest.dto.TokenDTO;
import io.ricardosteel.vendas.security.jwt.JwtService;
import io.ricardosteel.vendas.serviceimpl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioControler {
	private final UsuarioServiceImpl service;
	private final BCryptPasswordEncoder encoder;
	private final JwtService jwtService;

	@PostMapping
	public Usuario salvar(@RequestBody @Valid Usuario usuario) {
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		return service.save(usuario);
	}

	@PostMapping("/auth")
	public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {
		Usuario usuario = Usuario.builder().username(credenciais.getUsername()).password(credenciais.getPassword())
				.build();

		UserDetails usuarioAutenticado = service.autenticate(usuario);
		
		String token = jwtService.gerarToken(usuario);
		
		return new TokenDTO(usuarioAutenticado.getUsername(), token);
	}

}
