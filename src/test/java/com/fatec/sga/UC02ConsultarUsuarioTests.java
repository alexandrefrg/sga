package com.fatec.sga;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.sga.model.Usuario;
import com.fatec.sga.model.UsuarioRepository;

@SpringBootTest
class UC02ConsultarUsuarioTests {

	@Autowired
	UsuarioRepository repository;

	@Test
	void ct01_quando_consulta_usuario_cadastrado_retorna_dados() {
		// clean repository
		repository.deleteAll();
		Usuario usuario = new Usuario("Alexandre", "passwd");

		// save object
		repository.save(usuario);

		// check presence
		Optional<Usuario> ro = repository.validate(usuario.getLogin(), usuario.getPassword());
		assertTrue(ro.isPresent());
	}

	@Test
	void ct02_quando_consulta_usuario_nao_cadastrado_retorna_null() {

		// clean repository
		repository.deleteAll();

		// check presence
		Optional<Usuario> ro = repository.validate("Novo", "1234");
		assertFalse(ro.isPresent());
	}
}
