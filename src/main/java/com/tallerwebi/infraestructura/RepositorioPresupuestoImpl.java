package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Presupuesto;
import com.tallerwebi.dominio.RepositorioPresupuesto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioPresupuesto")
public class RepositorioPresupuestoImpl implements RepositorioPresupuesto {

    SessionFactory sessionFactory;

    @Autowired
    public RepositorioPresupuestoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarPresupuesto(Presupuesto presupuesto) {
    this.sessionFactory.getCurrentSession().save(presupuesto);
    }
}
