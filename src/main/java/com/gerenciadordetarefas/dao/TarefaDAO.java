package com.gerenciadordetarefas.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.gerenciadordetarefas.dominio.Responsavel;
import com.gerenciadordetarefas.dominio.Situacao;
import com.gerenciadordetarefas.dominio.Tarefa;

public class TarefaDAO extends GenericDAOImpl<Tarefa> {
	public TarefaDAO() {
		super(Tarefa.class);
	}

	public List<Tarefa> findBy(Map<String, Object> filtro) {
		getEm().clear();
		
		CriteriaBuilder cb = getEm().getCriteriaBuilder();

		CriteriaQuery<Tarefa> q = cb.createQuery(Tarefa.class);
		Root<Tarefa> root = q.from(Tarefa.class);
		q.select(root);

		List<Predicate> predicates = new ArrayList<>();
		
		if (filtro.containsKey("numero") && filtro.get("numero") != null && !((String) filtro.get("numero")).isBlank()) {
			predicates.add(cb.equal(root .get("id"), (Integer.parseInt((String) filtro.get("numero")))));
		}

		if (filtro.containsKey("titulo") && filtro.get("titulo") != null && !((String) filtro.get("titulo")).isBlank()) {
			String texto = "%" + filtro.get("titulo") + "%";
			predicates.add(cb.or(cb.like(root.get("titulo"), texto), cb.like(root.get("descricao"), texto)));
		}
		
		if (filtro.containsKey("responsavel") && filtro.get("responsavel") != null) {
		    Join<Tarefa, Responsavel> responsavelJoin = root.join("responsavel", JoinType.INNER);
			predicates.add(cb.equal(responsavelJoin.get("id"), ((Responsavel) filtro.get("responsavel")).getId()));
		}
		
		if (filtro.containsKey("situacao")) {
			predicates.add(cb.equal(root.get("situacao"), Situacao.valueOf((String) filtro.get("situacao"))));
		}

		q.select(root).where(predicates.toArray(new Predicate[predicates.size()])).orderBy(cb.asc(root.get("id")));

		return getEm().createQuery(q).getResultList();
	}
	
}
