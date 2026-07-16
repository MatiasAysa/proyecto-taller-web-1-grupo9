package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.RepositorioAlimento;
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
public class RepositorioAlimentoTest {

  @Autowired
  private SessionFactory sessionFactory;

  private RepositorioAlimento repositorio;

  @BeforeEach
  public void inicializarObjetos() {
    repositorio = new RepositorioAlimentoImpl(sessionFactory);
  }

  @Test
  @Rollback
  public void debeBuscarUnAlimentoPorId() {
    // GIVEN
    Alimento alimento = givenExisteUnAlimento();

    // WHEN
    Alimento alimentoEncontrado = repositorio.buscarPorId(alimento.getId());

    // THEN
    assertThat(alimentoEncontrado, notNullValue());
    assertThat(alimentoEncontrado.getNombreGenerico(), equalTo("Arroz"));
  }

  @Test
  @Rollback
  public void debeRetornarNullSiElAlimentoNoExiste() {
    // WHEN
    Alimento alimento = repositorio.buscarPorId(99999L);

    // THEN
    assertThat(alimento, nullValue());
  }

  @Test
  @Rollback
  public void debeObtenerListaDeAlimentos() {
    // GIVEN
    givenExisteUnAlimento();
    givenExisteOtroAlimento();

    // WHEN
    List<Alimento> alimentos = repositorio.obtenerListaAlimentos();

    // THEN
    assertThat(alimentos, hasSize(2));
  }

  @Test
  @Rollback
  public void debeObtenerTodosLosAlimentos() {
    // GIVEN
    givenExisteUnAlimento();

    // WHEN
    List<Alimento> alimentos = repositorio.obtenerTodosLosAlimentos();

    // THEN
    assertThat(alimentos, hasSize(1));
    assertThat(alimentos.get(0).getNombreGenerico(), equalTo("Arroz"));
  }

  @Test
  @Rollback
  public void debeBuscarAlimentoPorNombreGenerico() {
    // GIVEN
    givenExisteUnAlimento();

    // WHEN
    Alimento alimento = repositorio.obtenerPorNombreGenerico("Arroz");

    // THEN
    assertThat(alimento, notNullValue());
    assertThat(alimento.getNombreGenerico(), equalTo("Arroz"));
  }

  @Test
  @Rollback
  public void debeRetornarNullSiNoExisteNombreGenerico() {
    // WHEN
    Alimento alimento = repositorio.obtenerPorNombreGenerico("Chocolate");

    // THEN
    assertThat(alimento, nullValue());
  }

  @Test
  @Rollback
  public void debeGuardarUnAlimento() {
    // GIVEN
    Alimento alimento = new Alimento();
    alimento.setNombreGenerico("Manzana");

    // WHEN
    repositorio.guardar(alimento);

    // THEN
    assertThat(alimento.getId(), notNullValue());

    Alimento guardado = repositorio.buscarPorId(alimento.getId());

    assertThat(guardado.getNombreGenerico(), equalTo("Manzana"));
  }

  private Alimento givenExisteUnAlimento() {
    Alimento alimento = new Alimento();

    alimento.setNombreGenerico("Arroz");

    sessionFactory.getCurrentSession().save(alimento);

    return alimento;
  }

  private Alimento givenExisteOtroAlimento() {
    Alimento alimento = new Alimento();

    alimento.setNombreGenerico("Fideos");

    sessionFactory.getCurrentSession().save(alimento);

    return alimento;
  }
}
