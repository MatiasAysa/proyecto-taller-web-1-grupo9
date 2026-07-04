package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Comida;
import com.tallerwebi.dominio.RepositorioReceta;
import com.tallerwebi.dominio.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioRecetaImpl implements RepositorioReceta {

  private SessionFactory sessionFactory;

  @Autowired
  public RepositorioRecetaImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void guardarReceta(Comida receta) {
    sessionFactory.getCurrentSession().save(receta);
  }

  @Override
  public Comida buscarRecetaPorNombreYUsuario(String nombre, Usuario usuario) {
    return (Comida) sessionFactory
      .getCurrentSession()
      .createCriteria(Comida.class)
      .add(Restrictions.eq("autor", usuario))
      .add(Restrictions.eq("nombre", nombre))
      .uniqueResult();
  }

  @Override
  public List<Comida> obtenerRecetasDeUsuario(Usuario usuario) {
    return usuario != null
      ? (List<Comida>) sessionFactory
        .getCurrentSession()
        .createCriteria(Comida.class)
        .add(Restrictions.eq("autor", usuario))
        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
        .list()
      : new ArrayList<Comida>();
  }
}
