package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.RepositorioAlimento;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioAlimento")
public class RepositorioAlimentoImpl implements RepositorioAlimento {

  private SessionFactory sessionFactory;

  @Autowired
  public RepositorioAlimentoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Alimento buscarPorId(Long id) {
    return sessionFactory.getCurrentSession().get(Alimento.class, id);
  }
}
