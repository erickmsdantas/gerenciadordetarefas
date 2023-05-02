package com.gerenciadordetarefas.dao;

import java.util.List;

public interface GenericDAO<T> {
	void create(T t);

	void update(T t);

	void delete(T t);

	List<T> findAll();
}
