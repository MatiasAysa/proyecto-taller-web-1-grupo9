package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.RepositorioPlanificador;
import com.tallerwebi.dominio.Usuario;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioPlanificador")
public class RepositorioPlanificadorImpl implements RepositorioPlanificador {

  private SessionFactory sessionFactory;

  @Autowired
  public RepositorioPlanificadorImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public List<Alimento> obtenerAlimentosDisponibles() {
    return this.sessionFactory.getCurrentSession()
      .createQuery("FROM Alimento", Alimento.class)
      .getResultList();
  }

  @Override
  public Usuario buscarUsuarioPorId(Long id) {
    return this.sessionFactory.getCurrentSession().get(Usuario.class, id);
  }
}
