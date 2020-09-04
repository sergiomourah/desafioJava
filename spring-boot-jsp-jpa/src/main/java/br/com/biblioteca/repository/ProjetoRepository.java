package br.com.biblioteca.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.biblioteca.entity.Projeto;

public interface ProjetoRepository  extends JpaRepository<Projeto, Long>{
	
	public Projeto findById(BigDecimal id);
	
	@Query(" FROM Projeto projeto WHERE nome like %:nomeProjeto%")
	public List<Projeto> listProjetoNome( @Param("nomeProjeto") String filter);
}
