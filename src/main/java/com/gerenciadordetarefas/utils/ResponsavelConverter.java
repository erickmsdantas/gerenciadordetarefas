package com.gerenciadordetarefas.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.gerenciadordetarefas.dao.GenericDAO;
import com.gerenciadordetarefas.dao.GenericDAOImpl;
import com.gerenciadordetarefas.dominio.Responsavel;

@FacesConverter(value = "responsavelConverter", forClass = Responsavel.class)
public class ResponsavelConverter implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty() && !string.equals("Selecione")) {
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("gerenciadordetarefas");
        	EntityManager entitymanager = emfactory.createEntityManager();
       	    		
            return entitymanager.find(Responsavel.class, Integer.parseInt(string));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && (o instanceof Responsavel)) {
            return String.valueOf(((Responsavel) o).getId());
        }

        return null;
    }

}
