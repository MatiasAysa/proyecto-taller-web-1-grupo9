package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.tallerwebi.dominio.RepositorioRestriccionAlimentaria;
import com.tallerwebi.dominio.RestriccionAlimentaria;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextConfiguration(classes = { HibernateInfraestructuraTestConfig.class })
public class RepositorioRestriccionAlimentariaTest {

  @Autowired
  private SessionFactory sessionFactory;

  private RepositorioRestriccionAlimentaria repositorio;

  @BeforeEach
  public void inicializarObjetos() {
    repositorio = new RepositorioRestriccionAlimentariaImpl(sessionFactory);
  }

  @Test
  @Rollback
  public void debeGuardarUnaRestriccionAlimentaria() {
    // GIVEN
    RestriccionAlimentaria restriccion = new RestriccionAlimentaria();
    restriccion.setNombre("Sin gluten");

    // WHEN
    repositorio.guardar(restriccion);

    // THEN
    assertThat(restriccion.getId(), notNullValue());

    RestriccionAlimentaria guardada = sessionFactory
      .getCurrentSession()
      .get(RestriccionAlimentaria.class, restriccion.getId());

    assertThat(guardada.getNombre(), equalTo("Sin gluten"));
  }

  @Test
  @Rollback
  public void debeBuscarRestriccionPorNombre() {
    // GIVEN
    RestriccionAlimentaria restriccion = givenExisteUnaRestriccion();

    // WHEN
    RestriccionAlimentaria encontrada = repositorio.buscarPorNombre("Vegetariana");

    // THEN
    assertThat(encontrada, notNullValue());
    assertThat(encontrada.getId(), equalTo(restriccion.getId()));
    assertThat(encontrada.getNombre(), equalTo("Vegetariana"));
  }

  @Test
  @Rollback
  public void debeRetornarNullSiNoExisteLaRestriccion() {
    // WHEN
    RestriccionAlimentaria restriccion = repositorio.buscarPorNombre("No existe");

    // THEN
    assertThat(restriccion, nullValue());
  }

  private RestriccionAlimentaria givenExisteUnaRestriccion() {
    RestriccionAlimentaria restriccion = new RestriccionAlimentaria();

    restriccion.setNombre("Vegetariana");

    sessionFactory.getCurrentSession().save(restriccion);

    return restriccion;
  }
}
