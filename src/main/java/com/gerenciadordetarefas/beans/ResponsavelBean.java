package com.gerenciadordetarefas.beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.gerenciadordetarefas.dao.ResponsavelDAO;
import com.gerenciadordetarefas.dominio.Responsavel;
import java.io.Serializable;

@Named
@SessionScoped
public class ResponsavelBean extends AbstractCRUDController<Responsavel> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3159459074195781103L;
	
	public static final String LISTA = "/responsavel/lista.xhtml";
	public static final String FORMULARIO = "/responsavel/formulario.xhtml";
	
	private ResponsavelDAO responsavelDAO;
	
	public ResponsavelBean() {
		super(Responsavel.class);
		
		responsavelDAO = new ResponsavelDAO();
		responsavelDAO.setEm(getEntitymanager());
	}

	public void onCreate() {
		addInfoMessage("Responsável Cadastrado com sucesso!");
	}
	
	public void onUpdate() {
		addInfoMessage("Responsável atualizado com sucesso!");
	}
	
	@Override
	public boolean validate() {
		if (getObj().getNome().isBlank()) {
			addErrorMessage("Insira o nome do Responsável");
			return false;
		}
		
		if (responsavelDAO.hasResponsavel(getObj().getNome())) {
			addErrorMessage("Não foi possível " +  getNomeConfirmar() + " o responsável. Já existe um responsável cadastrado com esse nome.");
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean validateDelete(Responsavel r) {
		if (responsavelDAO.hasTarefa(r)) {
			addErrorMessage("Não é possível excluir o responsável pois ele possui tarefas associadas");
			return false;
		}
		
		return true;
	}
	
	public void onDelete() {
		addInfoMessage("Responsável excluído com sucesso!");
	}
	
	@Override
	public Responsavel newObj() {
		return new Responsavel();
	}

	@Override
	public String getFormulario() {
		return FORMULARIO;
	}

	@Override
	public String getLista() {
		return LISTA;
	}

	@Override
	public boolean isUpdating() {
		return getObj().getId() > 0;
	}

}
