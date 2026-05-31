package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PerfilAlimentarioUsuario;
import com.tallerwebi.dominio.RepositorioPerfilAlimentarioUsuario;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioPerfilAlimentarioUsuarioImpl
  implements RepositorioPerfilAlimentarioUsuario {

  private final SessionFactory sessionFactory;

  @Autowired
  public RepositorioPerfilAlimentarioUsuarioImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void guardar(PerfilAlimentarioUsuario perfilAlimentario) {
    this.sessionFactory.getCurrentSession().save(perfilAlimentario);
  }
}
