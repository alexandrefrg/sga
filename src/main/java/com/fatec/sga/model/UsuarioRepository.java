package com.fatec.sga.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	@Query(value = "SELECT u FROM Usuario u WHERE u.login = :login and u.password = :password")
	Optional<Usuario> validate(@Param("login") String login, @Param("password") String password);
}
