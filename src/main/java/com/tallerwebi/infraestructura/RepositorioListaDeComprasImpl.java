package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Comida;
import com.tallerwebi.dominio.RepositorioListaDeCompras;
import java.util.List;
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
  public List<Comida> obtenerListaComidas() {
    return sessionFactory
      .getCurrentSession()
      .createQuery("select distinct c from Comida c left join fetch c.ingredientes", Comida.class)
      .getResultList();
  }

  @Override
  public Comida buscarComidaPorId(Long id) {
    return sessionFactory.getCurrentSession().get(Comida.class, id);
  }
}
