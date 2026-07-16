package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.tallerwebi.dominio.Comida;
import com.tallerwebi.dominio.RepositorioListaDeCompras;
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
public class RepositorioListaDeComprasTest {

  @Autowired
  private SessionFactory sessionFactory;

  private RepositorioListaDeCompras repositorio;

  @BeforeEach
  public void inicializarObjetos() {
    repositorio = new RepositorioListaDeComprasImpl(sessionFactory);
  }

  @Test
  @Rollback
  public void debeBuscarUnaComidaPorId() {
    // GIVEN
    Comida comida = new Comida();
    comida.setNombre("Pollo con arroz");
    sessionFactory.getCurrentSession().save(comida);

    // WHEN
    Comida comidaObtenida = repositorio.buscarComidaPorId(comida.getId());

    // THEN
    assertThat(comidaObtenida, notNullValue());
    assertThat(comidaObtenida.getNombre(), equalTo("Pollo con arroz"));
  }

  @Test
  @Rollback
  public void debeRetornarNullSiLaComidaNoExiste() {
    // WHEN
    Comida comida = repositorio.buscarComidaPorId(99999L);

    // THEN
    assertThat(comida, nullValue());
  }
}
