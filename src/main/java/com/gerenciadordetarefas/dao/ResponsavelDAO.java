package com.gerenciadordetarefas.dao;

import javax.persistence.TypedQuery;

import com.gerenciadordetarefas.dominio.Responsavel;

public class ResponsavelDAO extends GenericDAOImpl<Responsavel> {
	
	public ResponsavelDAO() {
		super(Responsavel.class);
	}

	public boolean hasTarefa(Responsavel responsavel) {
		TypedQuery<Long> query = getEm().createQuery("SELECT COUNT(t) FROM Tarefa t WHERE t.responsavel = :responsavel", Long.class);
	    query.setParameter("responsavel", responsavel);
	    Long count = query.getSingleResult();

		return count > 0;
	}

	public boolean hasResponsavel(String nome) {
		TypedQuery<Long> query = getEm().createQuery("SELECT COUNT(r) FROM Responsavel r WHERE r.nome = :nome", Long.class);
		query.setParameter("nome", nome);
		Long count = query.getSingleResult();
		
		return count > 0;
	}
	
}
