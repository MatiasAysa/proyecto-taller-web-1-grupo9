package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Presupuesto;
import com.tallerwebi.dominio.RepositorioPresupuesto;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioPresupuesto")
public class RepositorioPresupuestoImpl implements RepositorioPresupuesto {

  SessionFactory sessionFactory;
  RepositorioUsuario repositorioUsuario;

  @Autowired
  public RepositorioPresupuestoImpl(
    SessionFactory sessionFactory,
    RepositorioUsuario repositorioUsuario
  ) {
    this.sessionFactory = sessionFactory;
    this.repositorioUsuario = repositorioUsuario;
  }

  @Override
  public void guardarPresupuesto(Presupuesto presupuesto, String email) {
    Usuario usuario = repositorioUsuario.buscar(email);
    presupuesto.setUsuario(usuario);
    Presupuesto presupuestoExistente = (Presupuesto) this.sessionFactory.getCurrentSession()
      .createCriteria(Presupuesto.class)
      .add(Restrictions.eq("usuario", usuario))
      .uniqueResult();

    if (presupuestoExistente != null) {
      presupuestoExistente.setMonto(presupuesto.getMonto());
      presupuestoExistente.setIntervalo(presupuesto.getIntervalo());
      presupuestoExistente.setFecha(presupuesto.getFecha());
      this.sessionFactory.getCurrentSession().update(presupuestoExistente);
    } else {
      this.sessionFactory.getCurrentSession().save(presupuesto);
    }
  }

  @Override
  public Presupuesto buscarPresupuesto(String email) {
    Usuario usuario = repositorioUsuario.buscar(email);
    return (Presupuesto) this.sessionFactory.getCurrentSession()
      .createCriteria(Presupuesto.class)
      .add(Restrictions.eq("usuario", usuario))
      .uniqueResult();
  }
}
