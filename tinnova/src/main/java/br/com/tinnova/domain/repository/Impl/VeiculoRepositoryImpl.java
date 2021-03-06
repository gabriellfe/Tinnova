package br.com.tinnova.domain.repository.Impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.tinnova.domain.model.Veiculo;
import br.com.tinnova.domain.repository.VeiculoRepositoryQueries;

/**
 * Classe responsável pela implementação de novas Queries para VeiculoRepository
 * @author Gabriell Marques de Felipe {11/12/2021}
 * @version 1.0
 */


public class VeiculoRepositoryImpl implements VeiculoRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	
	/**
	 * Metodo responsável por buscar um veiculo de acordo com os atributos recebidos
	 * @author Gabriell Marques de Felipe {11/12/2021}
	 */


	@Override
	public List<Veiculo> find(Integer ano, LocalDate created, String descricao, String marca, LocalDate updated,
			String veiculo, Boolean vendido) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Veiculo> criteria = builder.createQuery(Veiculo.class);
		Root<Veiculo> root = criteria.from(Veiculo.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.hasText(veiculo)) {
			String veiculoUpper = veiculo.toUpperCase();
			predicates.add(builder.equal(root.get("veiculo"),  veiculoUpper )); 
		}
		if (StringUtils.hasText(marca)) {
			String marcaUpper = marca.toUpperCase();
			predicates.add(builder.like(root.get("marca"), "%" + marcaUpper + "%"));
		}
		if (ano != null) {
			predicates.add(builder.equal(root.get("ano"),ano));
		}
		if (created != null) {
			predicates.add(builder.equal(root.get("created"),  created ));
		}
		if (StringUtils.hasText(descricao)) {
			String descricaoUpper = descricao.toUpperCase();
			predicates.add(builder.like(root.get("descricao"), "%" + descricaoUpper + "%"));
		}
		if (updated != null) {
			predicates.add(builder.equal(root.get("updated"),updated));
		}	
		if (vendido == Boolean.TRUE) {	
			predicates.add(builder.equal(root.get("vendido"),vendido ));
		}	
		if (vendido == Boolean.FALSE) {
			predicates.add(builder.equal(root.get("vendido"),vendido ));
		}			
		criteria.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Veiculo> query = manager.createQuery(criteria);

		return query.getResultList();
	}

}
