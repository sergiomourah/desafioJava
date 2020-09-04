package br.com.biblioteca.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.com.biblioteca.entity.Pessoa;
import br.com.biblioteca.repository.PessoaRepository;

@Controller
public class PessoaController {

	@Autowired PessoaRepository PessoaRepository;
	
	
	@RequestMapping(value="/formPessoa", method = RequestMethod.GET)
	public ModelAndView form() {
		
		ModelAndView modelAndView = new ModelAndView("pessoa/formPessoa");
		
		try {
			
			Pessoa pessoa = new Pessoa();
			modelAndView.addObject("pessoa", pessoa);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/doFormPessoa", method = RequestMethod.POST)
	public RedirectView salvar(Pessoa pessoa, BindingResult result) {
		
		try {
			
			PessoaRepository.save(pessoa);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new RedirectView("/inicio", true);
	}	
}
