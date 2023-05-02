package com.gerenciadordetarefas.beans;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.gerenciadordetarefas.dao.GenericDAO;
import com.gerenciadordetarefas.dao.GenericDAOImpl;

public abstract class AbstractCRUDController<T> {
	private T obj;

	private EntityManager entitymanager;

	private GenericDAO<T> genericDAO;

	public AbstractCRUDController() {}
	
	public AbstractCRUDController(Class<T> clazz) {
		obj = newObj();

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("gerenciadordetarefas");
		entitymanager = emfactory.createEntityManager();

		genericDAO = new GenericDAOImpl<>(clazz);
		((GenericDAOImpl<T>) genericDAO).setEm(entitymanager);
	}

	public abstract T newObj();

	public abstract String getFormulario();

	public abstract String getLista();

	public abstract boolean isUpdating();

	public boolean validate() {
		return true;
	}

	public boolean validateDelete(T t) {
		return true;
	}

	public void onBeforeConfirm() {
	}

	public void onCreate() {
	}

	public void onUpdate() {
	}

	public void onDelete() {
	}

	public void afterConfirm() {
	}

	public String confirmar() throws IOException {
		onBeforeConfirm();

		if (!validate()) {
			return redirect(getFormulario());
		}

		if (isUpdating()) {
			genericDAO.update(obj);
			onUpdate();
		} else {
			genericDAO.create(obj);
			onCreate();
		}

		afterConfirm();

		return redirect(getLista());
	}

	public String excluir(T t) throws IOException {
		if (!validateDelete(t)) {
			return redirect(getLista());
		}

		genericDAO.delete(t);
		onDelete();

		return redirect(getLista());
	}

	public String irCadastrar() {
		setObj(newObj());
		return redirect(getFormulario());
	}

	public String irEditar(T t) {
		setObj(t);
		return redirect(getFormulario());
	}

	public String cancelar() {
		return redirect(getLista());
	}

	public void addInfoMessage(String message) {
		addMessage(FacesMessage.SEVERITY_INFO, message);
	}

	public void addErrorMessage(String message) {
		addMessage(FacesMessage.SEVERITY_ERROR, message);
	}

	public void addMessage(Severity severity, String message) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Flash flash = facesContext.getExternalContext().getFlash();
		flash.setKeepMessages(true);

		facesContext.addMessage(null, new FacesMessage(severity, message, ""));
	}

	public List<T> getAll() {
		return genericDAO.findAll();
	}

	public String redirect(String r) {
		return r + "?faces-redirect=true";
	}

	public String getNomeConfirmar() {
		return isUpdating() ? "Alterar" : "Cadastrar";
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public EntityManager getEntitymanager() {
		return entitymanager;
	}

	public GenericDAO<T> getGenericDAO() {
		return genericDAO;
	}

}
