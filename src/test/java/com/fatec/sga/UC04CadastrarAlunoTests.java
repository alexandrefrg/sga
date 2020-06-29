package com.fatec.sga;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.sga.model.Aluno;
import com.fatec.sga.model.AlunoRepository;

@SpringBootTest
class UC04CadastrarAlunoTests {

	@Autowired
	AlunoRepository repository;
	private Validator validator;
	private ValidatorFactory validatorFactory;

	@Test
	void ct01_quando_dados_validos_aluno_nao_cadastrado_retorna1() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		// clean repository
		repository.deleteAll();
		Aluno aluno = new Aluno("12345679801", "novo Usuario", LocalDate.now(), true, true);

		// check validations
		Set<ConstraintViolation<Aluno>> violations = validator.validate(aluno);
		assertTrue(violations.isEmpty());

		// save object
		repository.save(aluno);

		// check repository
		assertEquals(1, repository.count());
	}

	@Test
	void ct02_quando_aluno_nao_informa_CPF_retorna_msg_CPF_invalido() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		// clean repository
		repository.deleteAll();
		Aluno aluno = new Aluno("", "novo Usuario", LocalDate.now(), true, true);

		// check validations
		Set<ConstraintViolation<Aluno>> violations = validator.validate(aluno);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals("O CPF deve conter 11 numeros", violations.iterator().next().getMessage());
	}

	@Test
	void ct03_quando_aluno_informa_CPF_nulo_retorna_msg_CPF_nulo_invalido() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		// clean repository
		repository.deleteAll();
		Aluno aluno = new Aluno(null, "novo Usuario", LocalDate.now(), true, true);

		// check validations
		Set<ConstraintViolation<Aluno>> violations = validator.validate(aluno);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals("CPF não pode ser nulo", violations.iterator().next().getMessage());
	}

	@Test
	void ct04_quando_aluno_nao_informa_nome_retorna_msg_nome_invalido() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		// clean repository
		repository.deleteAll();
		Aluno aluno = new Aluno("12345679801", "", LocalDate.now(), true, true);

		// check validations
		Set<ConstraintViolation<Aluno>> violations = validator.validate(aluno);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals("O nome precisa ter no mínimo 10 caracteres e no máximo 50",
				violations.iterator().next().getMessage());
	}

	@Test
	void ct05_quando_aluno_informa_nome_nulo_retorna_msg_nome_nulo_invalido() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		// clean repository
		repository.deleteAll();
		Aluno aluno = new Aluno("12345679801", null, LocalDate.now(), true, true);

		// check validations
		Set<ConstraintViolation<Aluno>> violations = validator.validate(aluno);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals("Nome não pode ser nulo", violations.iterator().next().getMessage());
	}

	@Test
	void ct06_quando_aluno_nao_informa_data_retorna_msg_data_invalida() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		// clean repository
		repository.deleteAll();
		Aluno aluno = new Aluno("12345679801", "novo Usuario", null, true, true);

		// check validations
		Set<ConstraintViolation<Aluno>> violations = validator.validate(aluno);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals("Data de matricula não pode ser nulo", violations.iterator().next().getMessage());
	}

	@Test
	void ct07_quando_aluno_informa_data_nula_retorna_msg_nulo_invalido() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		// clean repository
		repository.deleteAll();
		Aluno aluno = new Aluno("12345679801", "novo Usuario", null, true, true);

		// check validations
		Set<ConstraintViolation<Aluno>> violations = validator.validate(aluno);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals("Data de matricula não pode ser nulo", violations.iterator().next().getMessage());
	}
}
