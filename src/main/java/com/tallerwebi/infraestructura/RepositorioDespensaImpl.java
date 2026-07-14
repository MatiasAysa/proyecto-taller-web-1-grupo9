package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.ItemDespensa;
import com.tallerwebi.dominio.RepositorioDespensa;
import com.tallerwebi.dominio.Usuario;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioDespensaImpl implements RepositorioDespensa {

  private final SessionFactory sessionFactory;

  @Autowired
  public RepositorioDespensaImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public ItemDespensa obtenerItemDespensaPorId(Long id) {
    return this.sessionFactory.getCurrentSession().get(ItemDespensa.class, id);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ItemDespensa> obtenerDespensaDelUsuario(Usuario usuario) {
    return this.sessionFactory.getCurrentSession()
      .createCriteria(ItemDespensa.class)
      .add(Restrictions.eq("usuario", usuario))
      .list();
  }

  @Override
  public void guardarOActualizarDespensa(ItemDespensa item) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(item);
  }

  @Override
  public void eliminarItemDespensa(Long id) {
    ItemDespensa itemDespensa = this.sessionFactory.getCurrentSession().get(ItemDespensa.class, id);

    if (itemDespensa != null) {
      this.sessionFactory.getCurrentSession().delete(itemDespensa);
    }
  }

  @Override
  public void agregarItemDespensa(Usuario usuario, Alimento alimento, Double cantidad) {
    ItemDespensa item = new ItemDespensa();
    item.setUsuario(usuario);
    item.setAlimento(alimento);
    item.setCantidad(cantidad);
    this.sessionFactory.getCurrentSession().save(item);
  }

  @Override
  public void agregarItemDespensaNuevo(Usuario usuario, ItemDespensa itemDespensa) {
    itemDespensa.setUsuario(usuario);
    this.sessionFactory.getCurrentSession().save(itemDespensa);
  }
}
