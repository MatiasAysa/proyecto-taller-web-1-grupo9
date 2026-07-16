package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.ItemDespensa;
import com.tallerwebi.dominio.RepositorioDespensa;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import java.util.List;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateInfraestructuraTestConfig.class })
@Transactional
public class RepositorioDespensaTest {

  @Autowired
  private SessionFactory sessionFactory;

  private RepositorioDespensa repositorio;

  @BeforeEach
  public void inicializarObjetos() {
    repositorio = new RepositorioDespensaImpl(sessionFactory);
  }

  @Test
  @Rollback
  public void debeObtenerUnItemDespensaPorId() {
    // GIVEN
    ItemDespensa item = givenExisteUnItemDespensa();

    // WHEN
    ItemDespensa itemEncontrado = repositorio.obtenerItemDespensaPorId(item.getId());

    // THEN
    assertThat(itemEncontrado, notNullValue());
    assertThat(itemEncontrado.getCantidad(), equalTo(2.0));
  }

  @Test
  @Rollback
  public void debeRetornarNullSiNoExisteElItemDespensa() {
    // WHEN
    ItemDespensa item = repositorio.obtenerItemDespensaPorId(99999L);

    // THEN
    assertThat(item, nullValue());
  }

  @Test
  @Rollback
  public void debeObtenerLaDespensaDelUsuario() {
    // GIVEN
    Usuario usuario = givenExisteUnUsuario();

    ItemDespensa item1 = givenExisteUnItemDespensa(usuario, "Arroz", 2.0);
    ItemDespensa item2 = givenExisteUnItemDespensa(usuario, "Fideos", 3.0);

    // WHEN
    List<ItemDespensa> despensa = repositorio.obtenerDespensaDelUsuario(usuario);

    // THEN
    assertThat(despensa, hasSize(2));
    assertThat(despensa.get(0).getUsuario(), equalTo(usuario));
  }

  @Test
  @Rollback
  public void debeGuardarUnNuevoItemEnLaDespensa() {
    // GIVEN
    Usuario usuario = givenExisteUnUsuario();
    Alimento alimento = givenExisteUnAlimento("Leche");

    // WHEN
    repositorio.agregarItemDespensa(usuario, alimento, 5.0);

    // THEN
    List<ItemDespensa> despensa = repositorio.obtenerDespensaDelUsuario(usuario);

    assertThat(despensa, hasSize(1));
    assertThat(despensa.get(0).getCantidad(), equalTo(5.0));
    assertThat(despensa.get(0).getAlimento(), equalTo(alimento));
  }

  @Test
  @Rollback
  public void debeEliminarUnItemDeLaDespensa() {
    // GIVEN
    ItemDespensa item = givenExisteUnItemDespensa();

    // WHEN
    repositorio.eliminarItemDespensa(item.getId());

    // THEN
    ItemDespensa eliminado = repositorio.obtenerItemDespensaPorId(item.getId());

    assertThat(eliminado, nullValue());
  }

  @Test
  @Rollback
  public void debeGuardarOActualizarUnItemDespensa() {
    // GIVEN
    ItemDespensa item = givenExisteUnItemDespensa();

    item.setCantidad(10.0);

    // WHEN
    repositorio.guardarOActualizarDespensa(item);

    // THEN
    ItemDespensa actualizado = repositorio.obtenerItemDespensaPorId(item.getId());

    assertThat(actualizado.getCantidad(), equalTo(10.0));
  }

  @Test
  @Rollback
  public void debeAgregarItemDespensaNuevoAsignandoUsuario() {
    // GIVEN
    Usuario usuario = givenExisteUnUsuario();

    ItemDespensa item = new ItemDespensa();
    item.setCantidad(7.0);

    // WHEN
    repositorio.agregarItemDespensaNuevo(usuario, item);

    // THEN
    assertThat(item.getId(), notNullValue());
    assertThat(item.getUsuario(), equalTo(usuario));
  }

  private ItemDespensa givenExisteUnItemDespensa() {
    Usuario usuario = givenExisteUnUsuario();
    return givenExisteUnItemDespensa(usuario, "Arroz", 2.0);
  }

  private ItemDespensa givenExisteUnItemDespensa(
    Usuario usuario,
    String nombreAlimento,
    Double cantidad
  ) {
    Alimento alimento = givenExisteUnAlimento(nombreAlimento);

    ItemDespensa item = new ItemDespensa();
    item.setUsuario(usuario);
    item.setAlimento(alimento);
    item.setCantidad(cantidad);

    sessionFactory.getCurrentSession().save(item);

    return item;
  }

  private Usuario givenExisteUnUsuario() {
    Usuario usuario = new Usuario();

    usuario.setEmail("test@test.com");
    usuario.setPassword("123456");
    usuario.setRol("USER");
    usuario.setActivo(true);

    sessionFactory.getCurrentSession().save(usuario);

    return usuario;
  }

  private Alimento givenExisteUnAlimento(String nombre) {
    Alimento alimento = new Alimento();

    alimento.setNombre(nombre);

    sessionFactory.getCurrentSession().save(alimento);

    return alimento;
  }
}
