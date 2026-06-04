package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
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

    Presupuesto presupuesto = whenGuardoUnPresupuesto(usuario);

    thenElPresupuestoSeGuardaEnLaBD(presupuesto, usuario);
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

  private Presupuesto whenGuardoUnPresupuesto(Usuario usuario) {
    Presupuesto presupuesto = new Presupuesto();
    presupuesto.setMonto(100000);
    presupuesto.setIntervalo(7);
    presupuesto.setFecha(LocalDate.now());
    repositorioPresupuesto.guardarPresupuesto(presupuesto, usuario.getEmail());
    return presupuesto;
  }

  @Test
  @Transactional
  @Rollback
  public void sePuedeRecuperarUnPresupuesto() {
    Usuario usuario = givenExisteUnUsuario();
    Presupuesto presupuesto = givenExisteUnPresupuestoEnBD(usuario);

    Presupuesto presupuestoEncontrado = whenBuscoPresupuesto(usuario.getEmail());

    thenElPresupuestoExiste(presupuestoEncontrado, presupuesto);
  }

  private void thenElPresupuestoExiste(Presupuesto presupuestoEncontrado, Presupuesto presupuesto) {
    assertThat(presupuesto, equalTo(presupuestoEncontrado));
  }

  private Presupuesto whenBuscoPresupuesto(String email) {
    return repositorioPresupuesto.buscarPresupuesto(email);
  }

  private Presupuesto givenExisteUnPresupuestoEnBD(Usuario usuario) {
    return whenGuardoUnPresupuesto(usuario);
  }
}
