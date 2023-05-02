package com.gerenciadordetarefas.dominio;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Situacao {
	EM_ANDAMENTO("Em Andamento"), CONCLUIDA("Concluída");

	private String nome;
	
    private static final Map<String,Situacao> ENUM_MAP;

	private Situacao(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	static {
        Map<String,Situacao> map = new ConcurrentHashMap<String, Situacao>();
        for (Situacao instance : Situacao.values()) {
            map.put(instance.getNome().toLowerCase(),instance);
        }
        
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static Situacao fromString(String name) {
        return ENUM_MAP.get(name.toLowerCase());
    }


}
