package io.ricardosteel.vendas.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.ricardosteel.vendas.VendasApplication;
import io.ricardosteel.vendas.domain.entity.Usuario;

@Service
public class JwtService {
	@Value("${security.jwt.expiracao}")
	private String expiracao;

	@Value("${security.jwt.chave-assinatura}")
	private String chaveAssinatura;

	public String gerarToken(Usuario usuario) {
		long expString = Long.parseLong(expiracao);

		LocalDateTime expiracaoDataHora = LocalDateTime.now().plusMinutes(expString);

		Date data = Date.from(expiracaoDataHora.atZone(ZoneId.systemDefault()).toInstant());
		
		HashMap<String, Object> claims = new HashMap<>();
		claims.put("email", "ricardo.saul@gmail.com");
		claims.put("roles", "admin");

		return Jwts.builder()
				.setSubject(usuario.getUsername())
				.setExpiration(data)
				.signWith(SignatureAlgorithm.HS512, chaveAssinatura)
				//.setClaims(claims)
				.compact();
	}
	
	private Claims obterClaims(String token) throws ExpiredJwtException{
		return Jwts.parser()
				.setSigningKey(chaveAssinatura)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String obterLoginUsuario(String token) throws ExpiredJwtException{
		return obterClaims(token).getSubject();
	}
	
	public boolean tokenValido(String token) {
		try {
			Date dataExpiracao = obterClaims(token).getExpiration();
			
			LocalDateTime date = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			
			return !LocalDateTime.now().isAfter(date);
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) {
//		ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
//		JwtService jwtService = contexto.getBean(JwtService.class);
//		Usuario usuario = Usuario.builder().username("fulano").build();
//		
//		String token = jwtService.gerarToken(usuario);
//		System.out.println(jwtService.tokenValido(token));
//		System.out.println(jwtService.obterLoginUsuario(token));
	}
}
