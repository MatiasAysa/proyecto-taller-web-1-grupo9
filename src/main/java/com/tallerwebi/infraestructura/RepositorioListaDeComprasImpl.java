package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Comida;
import com.tallerwebi.dominio.RepositorioListaDeCompras;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioListaDeComprasImpl implements RepositorioListaDeCompras {

  private SessionFactory sessionFactory;

  @Autowired
  public RepositorioListaDeComprasImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Comida buscarComidaPorId(Long id) {
    return sessionFactory.getCurrentSession().get(Comida.class, id);
  }
}
