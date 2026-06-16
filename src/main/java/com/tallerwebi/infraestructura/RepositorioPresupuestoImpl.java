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
  public void guardarPresupuesto(Presupuesto presupuesto, Usuario usuario) {
    if (this.buscarPresupuesto(usuario) != null) {
      modificarPresupuesto(this.buscarPresupuesto(usuario), presupuesto);
      return;
    }
    presupuesto.setUsuario(usuario);
    this.sessionFactory.getCurrentSession().save(presupuesto);
  }

  private void modificarPresupuesto(Presupuesto presupuestoActual, Presupuesto presupuestoNuevo) {
    presupuestoActual.setMonto(presupuestoNuevo.getMonto());
    presupuestoActual.setFecha(presupuestoNuevo.getFecha());
    presupuestoActual.setIntervalo(presupuestoNuevo.getIntervalo());
    this.sessionFactory.getCurrentSession().update(presupuestoActual);
  }

  @Override
  public Presupuesto buscarPresupuesto(Usuario usuario) {
    return (Presupuesto) this.sessionFactory.getCurrentSession()
      .createCriteria(Presupuesto.class)
      .add(Restrictions.eq("usuario", usuario))
      .uniqueResult();
  }
}
