package com.tallerwebi.dominio;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioPerfilRestriccionImp implements RepositorioPerfilRestriccion {

  private final SessionFactory sessionFactory;

  @Autowired
  public RepositorioPerfilRestriccionImp(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void guardar(PerfilRestriccion perfilRestriccion) {
    sessionFactory.getCurrentSession().save(perfilRestriccion);
  }
}
