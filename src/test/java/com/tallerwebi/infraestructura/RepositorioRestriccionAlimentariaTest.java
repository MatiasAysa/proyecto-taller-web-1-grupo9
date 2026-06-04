package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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
@ContextConfiguration(classes = { HibernateInfraestructuraTestConfig.class })
public class RepositorioRestriccionAlimentariaTest {

  @Autowired
  private SessionFactory sessionFactory;

  private RepositorioRestriccionAlimentaria repositorioRestriccion;

  @BeforeEach
  public void init() {
    repositorioRestriccion = new RepositorioRestriccionAlimentariaImpl(sessionFactory);
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaGuardarUnaNuevaRestriccionAlimentaria() {
    // give
    RestriccionAlimentaria restriccion = new RestriccionAlimentaria();

    // when
    this.cuandoGuardoLaRestriccionAlimentaria(restriccion);

    // then
    this.entoncesSeGuardoLaRestriccionAlimentaria(restriccion);
  }

  private void cuandoGuardoLaRestriccionAlimentaria(RestriccionAlimentaria restriccion) {
    repositorioRestriccion.guardar(restriccion);
  }

  private void entoncesSeGuardoLaRestriccionAlimentaria(RestriccionAlimentaria restriccion) {
    Long id = restriccion.getId();
    assertThat(id, is(notNullValue()));
  }
}
