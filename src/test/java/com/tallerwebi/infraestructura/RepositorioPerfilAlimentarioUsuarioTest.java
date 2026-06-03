package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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
    // give
    PerfilAlimentarioUsuario perfil = new PerfilAlimentarioUsuario();

    // when
    this.cuandoGuardoElPerfilAlimentario(perfil);

    // then
    this.entoncesSeGuardoElPerfilAlimentario(perfil);
  }

  private void cuandoGuardoElPerfilAlimentario(PerfilAlimentarioUsuario perfil) {
    repositorioPerfil.guardar(perfil);
  }

  private void entoncesSeGuardoElPerfilAlimentario(PerfilAlimentarioUsuario perfil) {
    Long id = perfil.getId();
    assertThat(id, is(notNullValue()));
  }
}
