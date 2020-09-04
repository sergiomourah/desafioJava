package br.com.biblioteca.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.com.biblioteca.entity.Membros;
import br.com.biblioteca.entity.Pessoa;
import br.com.biblioteca.entity.Projeto;
import br.com.biblioteca.entity.enums.Status;
import br.com.biblioteca.repository.MembrosRepository;
import br.com.biblioteca.repository.PessoaRepository;
import br.com.biblioteca.repository.ProjetoRepository;

@Controller
public class ProjetoController {

	@Autowired
	ProjetoRepository projetoRepository;
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	@Autowired
	MembrosRepository membrosRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value="/inicio", method = RequestMethod.GET)
	public ModelAndView home() {
		
		ModelAndView modelAndView = new ModelAndView("index");
		
		List<Projeto> listProjeto = projetoRepository.findAll();
		modelAndView.addObject("listProjeto",listProjeto);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/formProjeto", method = RequestMethod.GET)
	public ModelAndView form() {
		
		ModelAndView modelAndView = new ModelAndView("projeto/formProjeto");
		
		try {
			
			List<Pessoa> listPessoa = pessoaRepository.findAllByFuncionario(true);
			modelAndView.addObject("listPessoas", listPessoa);
			
			Projeto projeto = new Projeto();
			modelAndView.addObject("projeto", projeto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}

	@RequestMapping(value = "/doFormProjeto", method = RequestMethod.POST)
	public RedirectView salvar(Projeto projeto, BindingResult result) {
		
		try {
			
			Pessoa pessoa = pessoaRepository.findById(projeto.getPessoa().getId());
			projeto.setPessoa(pessoa);
			projetoRepository.save(projeto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new RedirectView("/inicio", true);
	}
	
	@RequestMapping(value="/editProjeto", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam BigDecimal idProjeto) {
		
		ModelAndView modelAndView = new ModelAndView("projeto/formProjeto");
		try {
			
			Projeto projeto = new Projeto();
			if(!Objects.isNull(idProjeto)) {
				
				projeto = projetoRepository.findById(idProjeto);
				
				if(projeto != null) {
					List<Membros> listMembros = membrosRepository.findByIdProjeto(projeto.getId());
					modelAndView.addObject("listMembros", listMembros);
				}
			} 
			
			List<Pessoa> listPessoa = pessoaRepository.findAllByFuncionario(true);
			modelAndView.addObject("listPessoas", listPessoa);
			modelAndView.addObject("projeto", projeto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteProjeto", method = RequestMethod.GET)
	public RedirectView delete(@RequestParam BigDecimal idProjeto) {
		
		Projeto projeto = projetoRepository.findById(idProjeto);
		if (projeto.getStatus().equals(Status.INICIADO) ||
			projeto.getStatus().equals(Status.EM_ANDAMENTO) ||
			projeto.getStatus().equals(Status.ENCERRADO)) {
			
			this.messageSource.getMessage("projeto.validation.delete", null, LocaleContextHolder.getLocale());
		}
		try {
			
			if(projeto != null) {
				
				projetoRepository.delete(projeto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new RedirectView("/inicio", true);
	}
	
	@RequestMapping(value="/cancelaProjeto", method = RequestMethod.GET)
	public RedirectView cancelar(@RequestParam BigDecimal idProjeto) {
		
		Projeto projeto = projetoRepository.findById(idProjeto);
		
		try {
			
			if(projeto != null) {
				
				if (projeto.getStatus().equals(Status.CANCELADO)) {
					
					this.messageSource.getMessage("projeto.validation.cancelado", null, LocaleContextHolder.getLocale());
				}
				if (projeto.getStatus().equals(Status.ENCERRADO)) {
					
					this.messageSource.getMessage("projeto.validation.encerrado", null, LocaleContextHolder.getLocale());
				}
				projeto.setStatus(Status.CANCELADO);
				projetoRepository.save(projeto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new RedirectView("/inicio", true);
	}
	
	@RequestMapping(value="/encerraProjeto", method = RequestMethod.GET)
	public RedirectView encerrar(@RequestParam BigDecimal idProjeto) {
		
		Projeto projeto = projetoRepository.findById(idProjeto);
		
		try {
			
			if(projeto != null) {
				
				if (projeto.getStatus().equals(Status.CANCELADO)) {
					
					this.messageSource.getMessage("projeto.validation.encerrar.cancelado", null, LocaleContextHolder.getLocale());
				}
				if (projeto.getStatus().equals(Status.ENCERRADO)) {
					
					this.messageSource.getMessage("projeto.validation.encerrar.encerrado", null, LocaleContextHolder.getLocale());
				}
				projeto.setStatus(Status.ENCERRADO);
				long millis = System.currentTimeMillis();  
				projeto.setDataFim(new Date(millis));
				projetoRepository.save(projeto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new RedirectView("/inicio", true);
	}
	
	@RequestMapping(value = "/doFind", method = RequestMethod.POST)
	public ModelAndView doFind(String filter) {
		
		ModelAndView modelView = new ModelAndView("index");
		
		List<Projeto> listProjeto= projetoRepository.listProjetoNome(filter);
		
		modelView.addObject("listProjeto",listProjeto);
		return modelView;
	}
	
	@RequestMapping(value="/formMembros", method = RequestMethod.POST)
	public ModelAndView addMembros(@RequestParam BigDecimal idProjeto) {
		
		ModelAndView modelAndView = new ModelAndView("membros/formMembros");
		
		try {
			
			List<Pessoa> listPessoa = pessoaRepository.findAllByFuncionario(true);
			modelAndView.addObject("listPessoas", listPessoa);
			
			Membros membro = new Membros();
			Projeto projeto = projetoRepository.findById(membro.getProjeto().getId());
			membro.setProjeto(projeto);
			modelAndView.addObject("membro", membro);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/doformMembros", method = RequestMethod.POST)
	public RedirectView salvar(Membros menbros, BindingResult result) {
		
		try {
			
			///nao deu tempo de finalizar
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new RedirectView("/inicio", true);
	}

}
