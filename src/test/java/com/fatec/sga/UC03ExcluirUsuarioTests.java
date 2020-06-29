package com.fatec.sga;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.sga.model.Usuario;
import com.fatec.sga.model.UsuarioRepository;

@SpringBootTest
class UC03ExcluirUsuarioTests {

	@Autowired
	UsuarioRepository repository;

	@Test
	void ct01_quando_exclui_usuario_consulta_retorna_null() {
		// set local variables
		String login = "Alexandre";
		String password = "passwd";

		// clean repository
		repository.deleteAll();
		Usuario usuario = new Usuario(login, password);

		// save object
		repository.save(usuario);

		// check object
		Optional<Usuario> ro = repository.validate(login, password);

		// delete saved object
		repository.deleteById(ro.get().getId());

		// check again
		ro = repository.validate(login, password);
		assertFalse(ro.isPresent());
	}

}
