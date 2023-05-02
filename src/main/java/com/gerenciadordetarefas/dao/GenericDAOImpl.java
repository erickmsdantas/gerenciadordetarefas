package com.gerenciadordetarefas.dao;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class GenericDAOImpl<T> implements GenericDAO<T> {
	private EntityManager em;

	private Class<T> clazz;
	
	public GenericDAOImpl(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public void create(T t) {
		executeAction(em -> em.persist(t));
	}

	public void update(T t) {
		executeAction(em -> em.merge(t));
	}

	public void delete(T t) {
		executeAction(em -> em.remove(em.contains(t) ? t :  em.merge(t)));
	}

	public T findById(Long id) {
		return em.find(clazz, id);
	}

	public List<T> findAll() {
		return em.createQuery("FROM " + clazz.getSimpleName(), clazz).getResultList();
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	private void executeAction(Consumer<EntityManager> action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit(); 
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
