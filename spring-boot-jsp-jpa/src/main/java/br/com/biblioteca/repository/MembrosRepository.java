package br.com.biblioteca.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.biblioteca.entity.Membros;

public interface MembrosRepository  extends JpaRepository<Membros, Long>{

	@Query(" FROM Membros membros WHERE id_projeto = :idProjeto")
	public List<Membros> findByIdProjeto(@Param("idProjeto") BigDecimal idProjeto);
}
