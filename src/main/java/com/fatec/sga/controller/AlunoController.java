package com.fatec.sga.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.sga.model.Aluno;
import com.fatec.sga.model.AlunoRepository;

@Controller
@RequestMapping(path = "/alunos")
public class AlunoController {

	Logger logger = LogManager.getLogger(AlunoController.class);

	@Autowired
	AlunoRepository repository;

	@GetMapping("/cadastrar")
	public ModelAndView retornaFormCadastrarAluno(Aluno aluno) {
		ModelAndView mv = new ModelAndView("cadastrarAluno");
		mv.addObject("aluno", aluno);
		return mv;
	}

	@PostMapping("/cadastrar")
	public ModelAndView cadastrarAluno(@Valid Aluno aluno, BindingResult result) {
		ModelAndView mv = new ModelAndView("cadastrarAluno");
		if (result.hasErrors()) {
			return mv;
		} else {
			repository.save(aluno);
			mv.setViewName("home");
			mv.addObject("msgSucessoHome", "Aluno cadastrado com Sucesso!");
		}
		return mv;
	}

	@GetMapping("/consultar")
	public ModelAndView retornaFormConsultarAluno(Aluno aluno) {
		ModelAndView mv = new ModelAndView("consultarAluno");
		return mv;
	}

	@PostMapping("/consultar")
	public ModelAndView consultarAluno(Aluno aluno) {
		ModelAndView mv = new ModelAndView("consultarAluno");
		mv.addObject("aluno", aluno);

		if (aluno.getCPF().isEmpty() && aluno.getName().isEmpty()) {

			mv.addObject("msgErroConsulta", "Aluno não encontrado");
			return mv;

		} else if (!aluno.getCPF().isEmpty()) {
			Optional<Aluno> alunoRepo = repository.findByCPF(aluno.getCPF());

			if (alunoRepo.isPresent()) {

				Set<Aluno> listaAlunos = new HashSet<>();
				listaAlunos.add(alunoRepo.get());
				mv.addObject("listaAlunos", listaAlunos);

			} else {

				mv.addObject("msgErroConsulta", "Aluno não encontrado");
				return mv;
			}
		} else {

			Set<Aluno> listaAlunos = repository.findByNameLike(aluno.getName());

			if (listaAlunos.size() > 0) {

				mv.addObject("listaAlunos", listaAlunos);

			} else {

				mv.addObject("msgErroConsulta", "Aluno não encontrado");
				return mv;
			}
		}
		return mv;
	}

	@GetMapping("/editar/{id}")
	public ModelAndView retornaFormEditarAluno(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("editarAluno");
		Optional<Aluno> validAluno = repository.findById(id);
		if (validAluno.isPresent()) {
			mv.addObject("aluno", validAluno.get());
			return mv;
		} else {
			mv.setViewName("home");
			mv.addObject("msgErroHome", "Aluno não encontrado na base de dados!");
		}
		return mv;
	}

	@PostMapping("/editar/{id}")
	public ModelAndView atualizarAluno(@PathVariable("id") Long id, @Valid Aluno aluno, BindingResult result) {
		ModelAndView mv = new ModelAndView("home");
		if (result.hasErrors()) {
			aluno.setId(id);
			mv.setViewName("editarAluno");
			mv.addObject("aluno", aluno);
			return mv;
		} else {
			Optional<Aluno> alunoRepo = repository.findById(id);
			if (alunoRepo.isPresent()) {
				alunoRepo.get().setCPF(aluno.getCPF());
				alunoRepo.get().setName(aluno.getName());
				alunoRepo.get().setEnrollmentDate(aluno.getEnrollmentDate());
				alunoRepo.get().setMedicalCertificate(aluno.isMedicalCertificate());

				repository.save(alunoRepo.get());
				mv.addObject("msgSucessoHome", "Aluno atualizado com Sucesso!");
			} else {
				mv.addObject("msgErroHome", "Aluno não encontrado!");
			}
		}
		return mv;
	}

	@GetMapping("/lancarPagamento/{id}")
	public ModelAndView lancarPagamentoAluno(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("home");
		Optional<Aluno> alunoRepo = repository.findById(id);
		if (alunoRepo.isPresent()) {
			alunoRepo.get().setPaid(true);
			repository.save(alunoRepo.get());
			mv.addObject("msgSucessoHome", "Pagamento lançado com Sucesso!");
		} else {
			mv.addObject("msgErroHome", "Aluno não encontrado!");
		}
		return mv;
	}

	@GetMapping("/deletar/{id}")
	public ModelAndView deletarAluno(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("home");
		Optional<Aluno> alunoRepo = repository.findById(id);
		if (alunoRepo.isPresent()) {
			repository.deleteById(id);
			mv.addObject("msgSucessoHome", "Aluno deletado com Sucesso!");
		} else {
			mv.addObject("msgErroHome", "Aluno não encontrado!");
		}
		return mv;
	}

	@GetMapping("/relatorio")
	public ModelAndView gerarRelatorio() {
		ModelAndView mv = new ModelAndView("relatorioInadimplencia");
		mv.addObject("listaAlunos", repository.findUnpaid(false));
		return mv;
	}
}
