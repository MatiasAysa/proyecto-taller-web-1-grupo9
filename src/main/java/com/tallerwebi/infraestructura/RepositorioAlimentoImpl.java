package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.RepositorioAlimento;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

  @Override
  public List<Alimento> obtenerTodosLosAlimentos() {
    return this.sessionFactory.getCurrentSession()
      .createQuery("FROM Alimento", Alimento.class)
      .getResultList();
  }

  @Override
  public Alimento obtenerPorNombreGenerico(String nombre) {
    return (Alimento) this.sessionFactory.getCurrentSession()
      .createCriteria(Alimento.class)
      .add(Restrictions.eq("nombreGenerico", nombre))
      .uniqueResult();
  }

  @Override
  public void guardar(Alimento alimento) {
    sessionFactory.getCurrentSession().save(alimento);
  }
}
