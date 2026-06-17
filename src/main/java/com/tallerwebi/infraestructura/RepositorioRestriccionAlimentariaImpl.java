package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioRestriccionAlimentaria;
import com.tallerwebi.dominio.RestriccionAlimentaria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioRestriccionAlimentariaImpl implements RepositorioRestriccionAlimentaria {

  private final SessionFactory sessionFactory;

  @Autowired
  public RepositorioRestriccionAlimentariaImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void guardar(RestriccionAlimentaria restriccion) {
    sessionFactory.getCurrentSession().save(restriccion);
  }

  public RestriccionAlimentaria buscarPorNombre(String nombre) {
    return (RestriccionAlimentaria) sessionFactory
      .getCurrentSession()
      .createCriteria(RestriccionAlimentaria.class)
      .add(Restrictions.eq("nombre", nombre))
      .uniqueResult();
  }
}
