package com.fatec.sga.model;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long> {

	@Query(value = "SELECT a FROM Aluno a WHERE a.name LIKE %:name% ORDER BY a.name")
	Set<Aluno> findByNameLike(@Param("name") String name);

	Optional<Aluno> findByCPF(@Param("CPF") String CPF);

	@Query(value = "SELECT a FROM Aluno a WHERE a.paid = :paid ORDER BY a.name")
	Set<Aluno> findUnpaid(@Param("paid") boolean paid);
}
