/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenciadordetarefas.beans;

import javax.inject.Named;
import com.gerenciadordetarefas.dao.ResponsavelDAO;
import com.gerenciadordetarefas.dao.TarefaDAO;
import com.gerenciadordetarefas.dominio.Prioridade;
import com.gerenciadordetarefas.dominio.Responsavel;
import com.gerenciadordetarefas.dominio.Situacao;
import com.gerenciadordetarefas.dominio.Tarefa;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author erick
 */
@Named
@SessionScoped
public class TarefaBean extends AbstractCRUDController<Tarefa> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1166414661988448696L;

	public static final String LISTA = "/tarefa/lista.xhtml";
	public static final String FORMULARIO = "/tarefa/formulario.xhtml";
	public static final String DETALHES = "/tarefa/detalhes.xhtml";
	
	Map<String, Object> filtro = new HashMap<>();
	
	TarefaDAO tarefaDAO;
	ResponsavelDAO responsavelDAO;

	public TarefaBean() {
		super(Tarefa.class);
		
		tarefaDAO = new TarefaDAO();
		tarefaDAO.setEm(getEntitymanager());

		responsavelDAO = new ResponsavelDAO();
		responsavelDAO.setEm(getEntitymanager());
		
		resetFiltro();
	}

	@Override
	public void onBeforeConfirm() {
		getObj().setSituacao(Situacao.EM_ANDAMENTO);
	}
	
	@Override
	public boolean validate() {
		if (getObj().getTitulo().isBlank()) {
			addErrorMessage( "Título é obrigatório!");
			return false;
		}
		
		if (getObj().getResponsavel() == null || getObj().getResponsavel().getId() <= 0) {
			addErrorMessage( "Por favor, selecione um responsável!");
			return false;
		}
		
		if (getObj().getDeadline() == null) {
			addErrorMessage( "Por favor, defina a data limite (deadline) para esta tarefa.");
			return false;
		}
		
		return true;
	}
	
	@Override
	public void onDelete() {
		addInfoMessage("Tarefa excluída com sucesso!");
	}
	
	@Override
	public void onUpdate() {
		addInfoMessage("Tarefa atualizada com sucesso!");
	}
	
	@Override
	public void onCreate() {
		addInfoMessage("Tarefa criada com sucesso!");
	}
		
	public String buscar() {
		return redirect(LISTA);
	}
	
	public String limparBuscar() {
		resetFiltro();
		return redirect(LISTA);
	}
	
	public void resetFiltro() {
		filtro = new HashMap<>();
		filtro.put("situacao", Situacao.EM_ANDAMENTO.toString());
	}
	
	public String concluirTarefa(Tarefa tarefa) {
		tarefa.setSituacao(Situacao.CONCLUIDA);
		tarefaDAO.update(tarefa);
		
		addInfoMessage("Tarefa concluída com sucesso!");
		
		return redirect(LISTA);
	}
	
	public String irDetalhes(Tarefa tarefa) {
		setObj(tarefa);
		return redirect(DETALHES);
	}

	@Override
	public List<Tarefa> getAll() {
		return tarefaDAO.findBy(filtro);
	}

	public Prioridade[] getPrioridades() {
		return Prioridade.values();
	}

	public Situacao[] getSituacoes() {
		return Situacao.values();
	}

	public List<Responsavel> getResponsaveis(){
		return responsavelDAO.findAll();
	}
	
	public Map<String, Object> getFiltro() {
		return filtro;
	}

	public void setFiltro(Map<String, Object> filtro) {
		this.filtro = filtro;
	}

	@Override
	public Tarefa newObj() {
		return new Tarefa();
	}

	@Override
	public String getFormulario() {
		return FORMULARIO;
	}

	@Override
	public String getLista() {
		return LISTA;
	}
	
	public boolean isUpdating() {
		return getObj().getId() > 0;
	}
	
}
