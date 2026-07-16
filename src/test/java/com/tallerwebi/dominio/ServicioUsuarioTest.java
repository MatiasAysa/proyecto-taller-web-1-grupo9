package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioUsuarioTest {

  private RepositorioUsuario repositorioUsuario;
  private RepositorioPresupuesto repositorioPresupuesto;
  private RepositorioPerfilAlimentarioUsuario repositorioPerfilAlimentarioUsuario;

  private ServicioUsuario servicioUsuario;

  @BeforeEach
  public void inicializarObjetos() {
    repositorioUsuario = mock(RepositorioUsuario.class);
    repositorioPresupuesto = mock(RepositorioPresupuesto.class);
    repositorioPerfilAlimentarioUsuario = mock(RepositorioPerfilAlimentarioUsuario.class);

    servicioUsuario =
      new ServicioUsuarioImp(
        repositorioUsuario,
        repositorioPresupuesto,
        repositorioPerfilAlimentarioUsuario
      );
  }

  @Test
  public void siUsuarioNoExisteNoTienePerfilAlimentario() {
    // GIVEN
    when(repositorioUsuario.buscar("test@test.com")).thenReturn(null);

    // WHEN
    boolean resultado = servicioUsuario.tienePerfilAlimentario("test@test.com");

    // THEN
    assertThat(resultado, is(false));
  }

  @Test
  public void siUsuarioTienePerfilAlimentarioDebeRetornarTrue() {
    // GIVEN
    Usuario usuario = new Usuario();

    PerfilAlimentarioUsuario perfil = new PerfilAlimentarioUsuario();

    usuario.setPerfilAlimentario(perfil);

    when(repositorioUsuario.buscar("test@test.com")).thenReturn(usuario);

    // WHEN
    boolean resultado = servicioUsuario.tienePerfilAlimentario("test@test.com");

    // THEN
    assertThat(resultado, is(true));
  }

  @Test
  public void siUsuarioNoTienePerfilDebeRetornarFalse() {
    // GIVEN
    Usuario usuario = new Usuario();

    usuario.setPerfilAlimentario(null);

    when(repositorioUsuario.buscar("test@test.com")).thenReturn(usuario);

    // WHEN
    boolean resultado = servicioUsuario.tienePerfilAlimentario("test@test.com");

    // THEN
    assertThat(resultado, is(false));
  }

  @Test
  public void siUsuarioTienePresupuestoDebeRetornarTrue() {
    // GIVEN
    Usuario usuario = new Usuario();

    Presupuesto presupuesto = new Presupuesto();

    when(repositorioUsuario.buscar("test@test.com")).thenReturn(usuario);

    when(repositorioPresupuesto.buscarPresupuesto(usuario)).thenReturn(presupuesto);

    // WHEN
    boolean resultado = servicioUsuario.tienePresupuesto("test@test.com");

    // THEN
    assertThat(resultado, is(true));
  }

  @Test
  public void siUsuarioNoTienePresupuestoDebeRetornarFalse() {
    // GIVEN
    Usuario usuario = new Usuario();

    when(repositorioUsuario.buscar("test@test.com")).thenReturn(usuario);

    when(repositorioPresupuesto.buscarPresupuesto(usuario)).thenReturn(null);

    // WHEN
    boolean resultado = servicioUsuario.tienePresupuesto("test@test.com");

    // THEN
    assertThat(resultado, is(false));
  }

  @Test
  public void siUsuarioNoExisteNoDevuelveDatosClientePanel() {
    // GIVEN
    when(repositorioUsuario.buscar("test@test.com")).thenReturn(null);

    // WHEN
    DatosClientePanel datos = servicioUsuario.obtenerDatosClientePanel("test@test.com");

    // THEN
    assertThat(datos, nullValue());
  }

  @Test
  public void debeObtenerDatosDelClientePanel() {
    // GIVEN

    Usuario usuario = new Usuario();

    PerfilAlimentarioUsuario perfil = new PerfilAlimentarioUsuario();

    perfil.setPeso(70.0);
    perfil.setAltura(1.75);
    perfil.setEdad(25);

    Set<PerfilRestriccion> restricciones = new HashSet<>();

    perfil.setPerfilRestricciones(restricciones);

    usuario.setPerfilAlimentario(perfil);

    Presupuesto presupuesto = new Presupuesto();

    presupuesto.setMonto(50000);
    presupuesto.setIntervalo(30);

    when(repositorioUsuario.buscar("test@test.com")).thenReturn(usuario);

    when(repositorioPresupuesto.buscarPresupuesto(usuario)).thenReturn(presupuesto);

    // WHEN

    DatosClientePanel datos = servicioUsuario.obtenerDatosClientePanel("test@test.com");

    // THEN

    assertThat(datos.getPeso(), equalTo(70.0));
    assertThat(datos.getAltura(), equalTo(1.75));
    assertThat(datos.getEdad(), equalTo(25));

    assertThat(datos.getMonto(), equalTo(50000.0));
    assertThat(datos.getIntervalo(), equalTo(30));
  }
}
