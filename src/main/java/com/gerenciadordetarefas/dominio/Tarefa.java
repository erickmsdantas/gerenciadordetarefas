package com.gerenciadordetarefas.dominio;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tarefa", uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
public class Tarefa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", nullable=false, unique=true, length=11)
	private int id;
	
	@Column(nullable = false)
	private String titulo;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name="responsavel_id", nullable=false)
	private Responsavel responsavel;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)	
	private Prioridade prioridade;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date deadline;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Situacao situacao;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel resposavel) {
		this.responsavel = resposavel;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deadline, descricao, id, prioridade, responsavel, situacao, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarefa other = (Tarefa) obj;
		return Objects.equals(deadline, other.deadline) && Objects.equals(descricao, other.descricao) && id == other.id
				&& prioridade == other.prioridade && Objects.equals(responsavel, other.responsavel)
				&& situacao == other.situacao && Objects.equals(titulo, other.titulo);
	}

}
