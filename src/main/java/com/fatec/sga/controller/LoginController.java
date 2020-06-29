package com.fatec.sga.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.sga.model.Usuario;

@Controller
public class LoginController {

	Logger logger = LogManager.getLogger(UsuarioController.class);

	@GetMapping("/")
	public ModelAndView homePage(Usuario usuario) {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@GetMapping("/home")
	public ModelAndView homePage() {
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}
}
