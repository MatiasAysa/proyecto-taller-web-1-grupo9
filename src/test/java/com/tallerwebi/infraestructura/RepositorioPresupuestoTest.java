package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

import com.tallerwebi.dominio.Presupuesto;
import com.tallerwebi.dominio.RepositorioPresupuesto;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import java.time.LocalDate;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringWebTestConfig.class, HibernateTestConfig.class })
public class RepositorioPresupuestoTest {

  @Autowired
  SessionFactory sessionFactory;

  @Autowired
  RepositorioUsuario repositorioUsuario;

  @Autowired
  RepositorioPresupuesto repositorioPresupuesto;

  @Test
  @Transactional
  @Rollback
  public void sePuedeGuardarUnPresupuesto() {
    Usuario usuario = givenExisteUnUsuario();
    Presupuesto presupuesto = new Presupuesto();
    presupuesto.setMonto(100000);
    presupuesto.setIntervalo(7);
    presupuesto.setFecha(LocalDate.now());

    whenGuardoUnPresupuesto(usuario, presupuesto);

    thenElPresupuestoSeGuardaEnLaBD(presupuesto, usuario);
  }

  @Transactional
  @Rollback
  @Test
  public void siYaExisteUnPresupuestoSeModificaElPresupuestoExistente() {
    Usuario usuario = givenExisteUnUsuario();
    Presupuesto presupuesto1 = givenExisteUnPresupuestoEnBD(usuario);
    Presupuesto presupuesto2 = new Presupuesto();
    presupuesto2.setMonto(222222);
    presupuesto2.setIntervalo(30);
    presupuesto2.setFecha(LocalDate.now());

    whenGuardoUnPresupuesto(usuario, presupuesto2);

    thenSeModificaElPresupuestoOriginal(usuario, presupuesto1, presupuesto2);
  }

  private void thenSeModificaElPresupuestoOriginal(Usuario usuario, Presupuesto presupuesto1, Presupuesto presupuesto2) {
    Presupuesto presupuestoEncontrado = repositorioPresupuesto.buscarPresupuesto(usuario);

    assertThat(presupuestoEncontrado.getId(), is(equalTo(presupuesto1.getId())));
    assertThat(presupuesto2.getId(), nullValue());
    assertThat(presupuestoEncontrado.getFecha(), is(equalTo(presupuesto2.getFecha())));
    assertThat(presupuestoEncontrado.getMonto(), is(equalTo(presupuesto2.getMonto())));
    assertThat(presupuestoEncontrado.getIntervalo(), is(equalTo(presupuesto2.getIntervalo())));
  }

  private Usuario givenExisteUnUsuario() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setEmail("a@a.com");
    usuario.setPassword("abcdef1234");
    usuario.setRol("a");
    usuario.setActivo(true);
    repositorioUsuario.guardar(usuario);
    return usuario;
  }

  private void thenElPresupuestoSeGuardaEnLaBD(Presupuesto presupuesto, Usuario usuario) {
    assertThat(presupuesto.getId(), notNullValue());
    assertThat(presupuesto.getUsuario(), equalTo(usuario));
  }

  private Presupuesto whenGuardoUnPresupuesto(Usuario usuario, Presupuesto presupuesto) {
    repositorioPresupuesto.guardarPresupuesto(presupuesto, usuario);
    return presupuesto;
  }

  @Test
  @Transactional
  @Rollback
  public void sePuedeRecuperarUnPresupuesto() {
    Usuario usuario = givenExisteUnUsuario();
    Presupuesto presupuesto = givenExisteUnPresupuestoEnBD(usuario);

    Presupuesto presupuestoEncontrado = whenBuscoPresupuesto(usuario);

    thenElPresupuestoExiste(presupuestoEncontrado, presupuesto);
  }

  private void thenElPresupuestoExiste(Presupuesto presupuestoEncontrado, Presupuesto presupuesto) {
    assertThat(presupuesto, equalTo(presupuestoEncontrado));
  }

  private Presupuesto whenBuscoPresupuesto(Usuario usuario) {
    return repositorioPresupuesto.buscarPresupuesto(usuario);
  }

  private Presupuesto givenExisteUnPresupuestoEnBD(Usuario usuario) {
    Presupuesto presupuesto = new Presupuesto();
    presupuesto.setMonto(100000);
    presupuesto.setIntervalo(7);
    presupuesto.setFecha(LocalDate.now());
    return whenGuardoUnPresupuesto(usuario, presupuesto);
  }
}
