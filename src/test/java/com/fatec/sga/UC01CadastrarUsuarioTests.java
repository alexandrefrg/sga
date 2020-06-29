package com.fatec.sga;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.sga.model.Usuario;
import com.fatec.sga.model.UsuarioRepository;

@SpringBootTest
class UC01CadastrarUsuarioTests {

	@Autowired
	UsuarioRepository repository;
	private Validator validator;
	private ValidatorFactory validatorFactory;

	@Test
	void ct01_quando_dados_validos_usuario_nao_cadastrado_retorna1() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		// clean repository
		repository.deleteAll();
		Usuario usuario = new Usuario("Alexandre", "passwd");

		// check validations
		Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
		assertTrue(violations.isEmpty());

		// save object
		repository.save(usuario);

		// check repository
		assertEquals(1, repository.count());
	}

	@Test
	void ct02_quando_usuario_nao_informa_login_retorna_msg_login_invalido() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		// clean repository
		repository.deleteAll();
		Usuario usuario = new Usuario("", "passwd");

		// check validations
		Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals("Login deve ter pelo menos 4 caracteres e no máximo 20",
				violations.iterator().next().getMessage());
	}

	@Test
	void ct03_quando_usuario_nao_informa_password_retorna_msg_password_invalido() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		// clean repository
		repository.deleteAll();
		Usuario usuario = new Usuario("Alexandre", "");

		// check validations
		Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals("Senha deve conter ao menos 4 caracteres", violations.iterator().next().getMessage());
	}
}
