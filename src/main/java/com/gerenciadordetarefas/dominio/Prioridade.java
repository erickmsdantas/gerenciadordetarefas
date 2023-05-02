package com.gerenciadordetarefas.dominio;

public enum Prioridade {
	ALTA("Alta"), MEDIA("Média"), BAIXA("Baixa");

	private String nome;

	private Prioridade(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
