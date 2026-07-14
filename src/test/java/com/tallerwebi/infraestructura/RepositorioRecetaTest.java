package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.tallerwebi.dominio.*;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringWebTestConfig.class, HibernateTestConfig.class })
public class RepositorioRecetaTest {

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private RepositorioReceta repositorioReceta;

  @Autowired
  private RepositorioUsuario repositorioUsuario;

  @Autowired
  private RepositorioAlimento repositorioAlimento;

  @Transactional
  @Test
  public void sePuedeCargarUnaReceta() {
    Usuario usuario = givenExisteUsuario();
    Comida receta = givenComidaValida(usuario);
    whenGuardoReceta(receta);
    thenSeGuardaLaReceta(receta);
  }

  @Transactional
  @Test
  public void sePuedebuscarRecetaPorNombreYUsuario() {
    Usuario usuario = givenExisteUsuario();
    Comida receta = givenComidaValida(usuario);
    repositorioReceta.guardarReceta(receta);
    Comida resultadoObtenido = whenBuscoRecetaPorNombreYUsuario(receta);
    assertThat(receta, is(equalTo(resultadoObtenido)));
  }

  @Transactional
  @Test
  public void sePuedeModificarUnaReceta() {
    Usuario usuario = givenExisteUsuario();
    Comida receta = givenComidaValida(usuario);
    repositorioReceta.guardarReceta(receta);
    receta.setNombre("Comida muy rica");
    receta.setTipo(TipoDeComida.ALMUERZO);
    repositorioReceta.guardarReceta(receta);
    List<Comida> resultadoEsperado = List.of(receta);
    List<Comida> resultadoObtenido = repositorioReceta.obtenerRecetasDeUsuario(usuario);
    assertThat(resultadoObtenido, equalTo(resultadoEsperado));
  }

  @Transactional
  @Test
  public void sePuedeObtenerRecetasDeUsuario() {
    Usuario usuario = givenExisteUsuario();
    Comida receta = givenComidaValida(usuario);
    repositorioReceta.guardarReceta(receta);
    List<Comida> resultadoObtenido = repositorioReceta.obtenerRecetasDeUsuario(usuario);
    assertThat(resultadoObtenido, equalTo(List.of(receta)));
  }

  @Transactional
  @Test
  public void sePuedeEliminarUnaReceta() {
    Usuario usuario = givenExisteUsuario();
    Comida receta = givenComidaValida(usuario);
    repositorioReceta.guardarReceta(receta);
    repositorioReceta.eliminarReceta(receta.getId());

    List<Comida> resultadoObtenido = repositorioReceta.obtenerRecetasDeUsuario(usuario);
    assertThat(resultadoObtenido, is(empty()));
  }

  private Comida whenBuscoRecetaPorNombreYUsuario(Comida receta) {
    return repositorioReceta.buscarRecetaPorNombreYUsuario(receta.getNombre(), receta.getAutor());
  }

  private void thenSeGuardaLaReceta(Comida receta) {
    assertThat(receta.getId(), is(notNullValue()));
  }

  @Transactional
  private void whenGuardoReceta(Comida receta) {
    repositorioReceta.guardarReceta(receta);
  }

  private Comida givenComidaValida(Usuario usuario) {
    Comida comida = new Comida();
    Alimento a1 = new Alimento();
    a1.setNombreGenerico("banana cavendish");
    Alimento a2 = new Alimento();
    a2.setNombreGenerico("Papa");
    repositorioAlimento.guardar(a1);
    repositorioAlimento.guardar(a2);
    ItemComida i1 = new ItemComida(10D, a1);
    ItemComida i2 = new ItemComida(10D, a2);
    comida.setNombre("Comida rica");
    comida.setItems(new ArrayList<ItemComida>(List.of(i1, i2)));
    comida.setAutor(usuario);
    return comida;
  }

  private Usuario givenExisteUsuario() {
    Usuario usuario = new Usuario();
    usuario.setEmail("a@a.com");
    repositorioUsuario.guardar(usuario);
    return usuario;
  }
}
