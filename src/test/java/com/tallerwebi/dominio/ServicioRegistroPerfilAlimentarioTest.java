package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class ServicioRegistroPerfilAlimentarioTest {

  private RepositorioPerfilAlimentarioUsuario repositorioPerfilAlimentarioUsuarioMock;
  private RepositorioUsuario repositorioUsuario;
  private ServicioRegistroPerfilAlimentario servicioRegistroPerfilAlimentario;

  @BeforeEach
  void setUp() {
    this.repositorioPerfilAlimentarioUsuarioMock = mock(RepositorioPerfilAlimentarioUsuario.class);
    this.repositorioUsuario = mock(RepositorioUsuario.class);
    this.servicioRegistroPerfilAlimentario =
      new ServicioRegistroPerfilAlimentarioImpl(
        repositorioPerfilAlimentarioUsuarioMock,
        repositorioUsuario
      );
  }

  @Test
  void dadoQueTodosLosDatosSonValidosYQueElUsuarioExisteCuandoSeGuardaEntoncesPersisteEnElRepositorio() {
    // Given
    PerfilAlimentarioDTO perfilDTO = givenPerfilConDatosValidos();
    Usuario usuario = givenUsuarioExiste();
    ArgumentCaptor<PerfilAlimentarioUsuario> perfilAlimentarioUsuarioCaptor =
      ArgumentCaptor.forClass(PerfilAlimentarioUsuario.class);
    when(repositorioUsuario.buscar(usuario.getEmail())).thenReturn(usuario);

    // When
    Boolean resultado = whenGuardarPerfilAlimentario(perfilDTO, usuario.getEmail());

    // Then
    verify(repositorioPerfilAlimentarioUsuarioMock)
      .guardar(perfilAlimentarioUsuarioCaptor.capture());
    PerfilAlimentarioUsuario perfilEntidadCapturado = perfilAlimentarioUsuarioCaptor.getValue();

    thenElResultadoEsTrue(resultado);
    verify(repositorioPerfilAlimentarioUsuarioMock, times(1)).guardar(perfilEntidadCapturado);
  }

  private Boolean whenGuardarPerfilAlimentario(PerfilAlimentarioDTO perfilDTO, String email) {
    return servicioRegistroPerfilAlimentario.guardarPerfilAlimentario(perfilDTO, email);
  }

  @Test
  void dadoQueTodosLosDatosSonValidosCuandoSeValidaObtengoTrue() {
    // Given
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();

    // When
    Boolean resultado = whenValidarPerfilAlimentario(perfil);

    // Then
    thenElResultadoEsTrue(resultado);
  }

  @Test
  void dadoQueIngresoUnPesoNegativoCuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setPeso(-100.0);
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  @Test
  void dadoQueIngresoUnPesoCeroCuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setPeso(0.0);
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  @Test
  void dadoQueIngresoUnPesoMayorOIgualA635CuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setPeso(635.0);
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  @Test
  void dadoQueIngresoUnaAlturaNegativaCuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setAltura(-100.0);
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  @Test
  void dadoQueIngresoUnaAlturaCeroCuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setAltura(0.0);
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  @Test
  void dadoQueIngresoUnaAlturaMayorOIgualA272CuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setAltura(272.0);
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  @Test
  void dadoQueIngresoUnaEdadNegativaCuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setEdad(-1);
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  @Test
  void dadoQueIngresoUnaEdadCeroCuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setEdad(0);
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  @Test
  void dadoQueIngresoUnaEdadMayorOIgualA100CuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setEdad(100);
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  @Test
  void dadoQueIngresoUnSexoInvalidoCuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setSexo("x");
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  @Test
  void dadoQueIngresoUnaRestriccionAlimentariaInvalidaCuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setRestriccionesAlimentarias("x");
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  @Test
  void dadoQueIngresoUnaActividadFisicaInvalidaCuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setActividadFisica("x");
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  @Test
  void dadoQueIngresoUnObjetivoInvalidoCuandoSeValidaObtengoFalse() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setObjetivo("x");
    Boolean resultado = whenValidarPerfilAlimentario(perfil);
    thenElResultadoEsFalse(resultado);
  }

  public PerfilAlimentarioDTO givenPerfilConDatosValidos() {
    Double peso = 100.0;
    Double altura = 100.0;
    Integer edad = 20;
    String sexo = Sexo.M.name();
    String actividadFisica = AcividadFisica.ACTIVA.name();
    String restriccionesAlimentarias = RestriccionAlimentaria.CELIACO.name();
    String objetivo = Objetivo.GANAR_PESO.name();
    PerfilAlimentarioDTO perfil = new PerfilAlimentarioDTO(
      peso,
      altura,
      edad,
      sexo,
      actividadFisica,
      restriccionesAlimentarias,
      objetivo
    );
    return perfil;
  }

  public Usuario givenUsuarioExiste() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setEmail("test@test.com");
    usuario.setPassword("test");
    usuario.setRol("user");
    return usuario;
  }

  public boolean whenValidarPerfilAlimentario(PerfilAlimentarioDTO perfil) {
    return servicioRegistroPerfilAlimentario.validarPerfilAlimentario(perfil);
  }

  public void thenElResultadoEsFalse(boolean resultado) {
    assertThat(resultado, is(false));
  }

  public void thenElResultadoEsTrue(boolean resultado) {
    assertThat(resultado, is(true));
  }
}
