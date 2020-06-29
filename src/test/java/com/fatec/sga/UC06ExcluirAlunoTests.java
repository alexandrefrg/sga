package com.fatec.sga;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.sga.model.Aluno;
import com.fatec.sga.model.AlunoRepository;

@SpringBootTest
class UC06ExcluirAlunoTests {

	@Autowired
	AlunoRepository repository;

	@Test
	void ct01_quando_exclui_usuario_consulta_retorna_null() {

		String CPF = "12345678901";
		// clean repository
		repository.deleteAll();
		Aluno aluno = new Aluno(CPF, "Novo Aluno A", LocalDate.now(), true, true);

		// save object
		repository.save(aluno);

		// check object
		Optional<Aluno> ro = repository.findByCPF(CPF);

		// delete saved object
		repository.deleteById(ro.get().getId());

		// check again
		ro = repository.findByCPF(CPF);
		assertFalse(ro.isPresent());
	}
}
