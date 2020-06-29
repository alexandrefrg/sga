package com.fatec.sga;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.sga.model.Aluno;
import com.fatec.sga.model.AlunoRepository;

@SpringBootTest
class UC05ConsultarAlunoTests {

	@Autowired
	AlunoRepository repository;

	@Test
	void ct01_quando_consulta_aluno_cadastrado_pelo_CPF_retorna_dados() {
		String CPF = "12345678901";

		// clean repository
		repository.deleteAll();
		Aluno aluno = new Aluno(CPF, "Novo Aluno", LocalDate.now(), true, true);

		// save object
		repository.save(aluno);

		// check presence
		Optional<Aluno> ro = repository.findByCPF(CPF);
		assertTrue(ro.isPresent());
	}

	@Test
	void ct02_quando_consulta_aluno_nao_cadastrado_retorna_null() {
		// clean repository
		repository.deleteAll();

		// check presence
		Optional<Aluno> ro = repository.findByCPF("1234578901");
		assertFalse(ro.isPresent());
	}

	@Test
	void ct03_quando_consulta_nome_parcial_aluno_retorna3_ordenado() {
		// clean repository
		repository.deleteAll();

		Aluno aluno = new Aluno("12345678901", "Novo Aluno B", LocalDate.now(), true, true);
		repository.save(aluno);
		aluno = new Aluno("12345678901", "Novo Aluno A", LocalDate.now(), true, true);
		repository.save(aluno);
		aluno = new Aluno("12345678901", "Novo Aluno Z", LocalDate.now(), true, true);
		repository.save(aluno);
		aluno = new Aluno("12345678901", "Z Aluno Velho", LocalDate.now(), true, true);
		repository.save(aluno);

		Set<Aluno> ro = repository.finByNameLike("Novo Aluno");
		assertThat(ro.size()).isEqualTo(3);
		assertThat(ro.iterator().next().getName()).isEqualTo("Novo Aluno A");
	}
}
