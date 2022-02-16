package io.ricardosteel.vendas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.ricardosteel.vendas.domain.entity.Cliente;
import io.ricardosteel.vendas.domain.repository.ClienteRepository;

@SpringBootApplication
public class VendasApplication {

	@Bean
	public CommandLineRunner commandLineRunner(@Autowired ClienteRepository repository) {
		return args -> {
			Cliente cliente = Cliente.builder().nome("Ricardo").build();
			repository.save(cliente);
			
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
