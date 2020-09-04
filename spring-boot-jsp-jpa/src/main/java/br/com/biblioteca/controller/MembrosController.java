package br.com.biblioteca.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;


import br.com.biblioteca.entity.Membros;
import br.com.biblioteca.entity.Projeto;
import br.com.biblioteca.repository.MembrosRepository;
import br.com.biblioteca.repository.ProjetoRepository;


@Controller
public class MembrosController {
	
	@Autowired
	MembrosRepository membrosRepository;
	
	@Autowired
	ProjetoRepository projetoRepository;

	@RequestMapping(value="/formMembro", method = RequestMethod.GET)
	public ModelAndView form(@RequestParam BigDecimal idProjeto) {
		
		ModelAndView modelAndView = new ModelAndView("membro/form");
		
		try {
			
			Membros membro = new Membros();
			modelAndView.addObject("membro", membro);
			modelAndView.addObject("idProjeto", idProjeto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/doFormMembro", method = RequestMethod.POST)
	public RedirectView salvar(Membros membro, BindingResult result) {
		
		try {
			
			Projeto projeto = projetoRepository.findById(membro.getProjeto().getId());
			membro.setProjeto(projeto);
			membrosRepository.save(membro);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new RedirectView("/editProjeto?idProjeto=" + membro.getProjeto().getId(), true);
	}
}
