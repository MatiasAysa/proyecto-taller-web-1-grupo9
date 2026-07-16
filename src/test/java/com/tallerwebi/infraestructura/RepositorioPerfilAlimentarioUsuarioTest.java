package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.tallerwebi.dominio.PerfilAlimentarioUsuario;
import com.tallerwebi.dominio.RepositorioPerfilAlimentarioUsuario;
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
public class RepositorioPerfilAlimentarioUsuarioTest {

  @Autowired
  private SessionFactory sessionFactory;

  private RepositorioPerfilAlimentarioUsuario repositorioPerfil;

  @BeforeEach
  public void init() {
    repositorioPerfil = new RepositorioPerfilAlimentarioUsuarioImpl(sessionFactory);
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaGuardarUnNuevoPerfilAlimentario() {
    PerfilAlimentarioUsuario perfil = new PerfilAlimentarioUsuario();
    perfil.setPeso(75.5);
    perfil.setAltura(1.75);
    perfil.setEdad(30);
    perfil.setSexo("M");
    perfil.setActividadFisica("ACTIVA");
    perfil.setObjetivo("MANTENER_PESO");

    this.cuandoGuardoElPerfilAlimentario(perfil);
    this.entoncesSeGuardoYSePuedeRecuperarElPerfilAlimentario(perfil);
  }

  private void cuandoGuardoElPerfilAlimentario(PerfilAlimentarioUsuario perfil) {
    repositorioPerfil.guardar(perfil);
  }

  private void entoncesSeGuardoYSePuedeRecuperarElPerfilAlimentario(
    PerfilAlimentarioUsuario perfil
  ) {
    Long id = perfil.getId();
    assertThat(id, is(notNullValue()));
    PerfilAlimentarioUsuario perfilGuardado = sessionFactory
      .getCurrentSession()
      .get(PerfilAlimentarioUsuario.class, id);

    assertThat(perfilGuardado, is(notNullValue()));

    assertThat(perfilGuardado.getPeso(), is(75.5));
    assertThat(perfilGuardado.getAltura(), is(1.75));
    assertThat(perfilGuardado.getEdad(), is(30));
    assertThat(perfilGuardado.getSexo(), equalToIgnoringCase("M"));
    assertThat(perfilGuardado.getActividadFisica(), equalToIgnoringCase("ACTIVA"));
    assertThat(perfilGuardado.getObjetivo(), equalToIgnoringCase("MANTENER_PESO"));
  }
}
