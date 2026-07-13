package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ItemDespensa;
import com.tallerwebi.dominio.Usuario;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioDespensaImpl {

  private final SessionFactory sessionFactory; 

  @Autowired
  public RepositorioDespensaImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }


  @SuppressWarnings("unchecked")
  public List<ItemDespensa> obtenerDespensaDelUsuario(Usuario usuario) {
    return this.sessionFactory.getCurrentSession()
      .createCriteria(ItemDespensa.class)
      .add(Restrictions.eq("usuario", usuario))
      .list();
  }


  public void guardarOActualizarDespensa(ItemDespensa item) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(item);
  }


  public void eliminarItemDespensa(String email, ItemDespensa itemDespensa) {
    ItemDespensa item = (ItemDespensa) this.sessionFactory.getCurrentSession()
      .createCriteria(ItemDespensa.class)
      .add(Restrictions.eq("email", email))
      .add(Restrictions.eq("id", itemDespensa.getId()))
      .uniqueResult();

    if (item != null) {
      this.sessionFactory.getCurrentSession().delete(item);
    }
  }
}
