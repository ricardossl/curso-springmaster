package io.ricardosteel.vendas.serviceimpl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ricardosteel.vendas.domain.entity.Usuario;
import io.ricardosteel.vendas.domain.repository.UsuarioRepository;
import io.ricardosteel.vendas.exceptions.SenhaInvalidaException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UserDetailsService {
	private final BCryptPasswordEncoder passwordEncoder;
	private final UsuarioRepository repository;

	public UserDetails autenticate(Usuario usuario) {
		UserDetails user = loadUserByUsername(usuario.getUsername());
		boolean passwordValid = passwordEncoder.matches(usuario.getPassword(), user.getPassword());

		if (passwordValid)
			return user;

		throw new SenhaInvalidaException();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

		String[] roles = usuario.isAdmin() ? new String[] { "ADMIN", "USER" } : new String[] { "USER" };

		return User.builder().username(usuario.getUsername()).password(usuario.getPassword()).roles(roles).build();
	}

	@Transactional
	public Usuario save(Usuario usuario) {
		return repository.save(usuario);
	}

}
