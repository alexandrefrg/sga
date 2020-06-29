package com.fatec.sga.controller;

import java.util.Optional;

import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.sga.model.Usuario;
import com.fatec.sga.model.UsuarioRepository;

@Controller
@RequestMapping(path = "/usuarios")
public class UsuarioController {

	Logger logger = LogManager.getLogger(UsuarioController.class);

	@Autowired
	UsuarioRepository repository;

	@PostMapping("/login")
	public ModelAndView login(@Valid Usuario usr, BindingResult result) {
		ModelAndView mv = new ModelAndView("home");
		if (result.hasErrors()) {
			mv.setViewName("login");
			return mv;
		} else {
			Optional<Usuario> validUsr = repository.validate(usr.getLogin(), usr.getPassword());
			if (validUsr.isPresent()) {
				return mv;
			} else {
				mv.setViewName("login");
				mv.addObject("msgErroLogin", "Usuário ou Senha inválido");
			}
		}
		return mv;
	}

	@GetMapping("/logout")
	public ModelAndView logout(Usuario usuario) {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@GetMapping("/cadastrar")
	public ModelAndView retornaFormCadastrarUsuario(Usuario usuario) {
		ModelAndView mv = new ModelAndView("cadastrarUsuario");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@PostMapping("/cadastrar")
	public ModelAndView cadastrarUsuario(@Valid Usuario usuario, BindingResult result) {
		ModelAndView mv = new ModelAndView("home");
		if (result.hasErrors()) {
			mv.setViewName("cadastrarUsuario");
			return mv;
		} else {
			repository.save(usuario);
			mv.setViewName("home");
			mv.addObject("msgSucessoHome", "Usuario cadastrado com Sucesso!");
		}
		return mv;
	}
}
