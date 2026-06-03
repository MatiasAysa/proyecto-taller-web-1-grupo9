package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import com.tallerwebi.dominio.excepcion.perfilException.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class ServicioRegistroPerfilAlimentarioTest {

  private RepositorioPerfilAlimentarioUsuario repositorioPerfilAlimentarioUsuarioMock;
  private RepositorioUsuario repositorioUsuario;
  private RepositorioRestriccionAlimentaria repositorioRestriccionAlimentaria;
  private ServicioRegistroPerfilAlimentario servicioRegistroPerfilAlimentario;

  @BeforeEach
  void setUp() {
    this.repositorioPerfilAlimentarioUsuarioMock = mock(RepositorioPerfilAlimentarioUsuario.class);
    this.repositorioUsuario = mock(RepositorioUsuario.class);
    this.repositorioRestriccionAlimentaria = mock(RepositorioRestriccionAlimentaria.class);
    this.servicioRegistroPerfilAlimentario =
      new ServicioRegistroPerfilAlimentarioImpl(
        repositorioPerfilAlimentarioUsuarioMock,
        repositorioUsuario,
        repositorioRestriccionAlimentaria
      );
  }

  @Test
  void dadoQueTodosLosDatosSonValidosYQueElUsuarioExisteCuandoSeGuardaEntoncesPersisteEnElRepositorio()
    throws UsuarioInexistenteException {
    // Given
    PerfilAlimentarioDTO perfilDTO = givenPerfilConDatosValidos();
    Usuario usuario = givenUsuarioExiste();
    ArgumentCaptor<PerfilAlimentarioUsuario> perfilAlimentarioUsuarioCaptor =
      ArgumentCaptor.forClass(PerfilAlimentarioUsuario.class);
    when(repositorioUsuario.buscar(usuario.getEmail())).thenReturn(usuario);

    // When
    servicioRegistroPerfilAlimentario.guardarPerfilAlimentario(perfilDTO, usuario.getEmail());

    // Then
    verify(repositorioPerfilAlimentarioUsuarioMock, times(1))
      .guardar(perfilAlimentarioUsuarioCaptor.capture());

    PerfilAlimentarioUsuario perfilEntidadCapturado = perfilAlimentarioUsuarioCaptor.getValue();
    assertThat(perfilEntidadCapturado.getPeso(), is(perfilDTO.getPeso()));
    assertThat(perfilEntidadCapturado.getAltura(), is(perfilDTO.getAltura()));
  }

  @Test
  void dadoQueTodosLosDatosSonValidosCuandoSeValidaNoLanzaNingunaExcepcion() {
    // Given
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();

    // When & Then
    assertDoesNotThrow(() -> servicioRegistroPerfilAlimentario.validarPerfilAlimentario(perfil));
  }

  @Test
  void dadoQueIngresoUnPesoNegativoCuandoSeValidaLanzaPesoInvalidoException() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setPeso(-10.0);

    assertThrows(
      PesoInvalidoException.class,
      () -> servicioRegistroPerfilAlimentario.validarPerfilAlimentario(perfil)
    );
  }

  @Test
  void dadoQueIngresoUnPesoMayorA635CuandoSeValidaLanzaPesoInvalidoException() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setPeso(635.1); // Tu servicio permite hasta 635 exacto, cambiamos a 635.1 para romperlo

    assertThrows(
      PesoInvalidoException.class,
      () -> servicioRegistroPerfilAlimentario.validarPerfilAlimentario(perfil)
    );
  }

  @Test
  void dadoQueIngresoUnaAlturaNegativaCuandoSeValidaLanzaAlturaInvalidaException() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setAltura(-1.0);

    assertThrows(
      AlturaInvalidaException.class,
      () -> servicioRegistroPerfilAlimentario.validarPerfilAlimentario(perfil)
    );
  }

  @Test
  void dadoQueIngresoUnaAlturaMayorA272CuandoSeValidaLanzaAlturaInvalidaException() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setAltura(272.1); // Tu servicio permite hasta 272 exacto

    assertThrows(
      AlturaInvalidaException.class,
      () -> servicioRegistroPerfilAlimentario.validarPerfilAlimentario(perfil)
    );
  }

  @Test
  void dadoQueIngresoUnaEdadNegativaCuandoSeValidaLanzaEdadInvalidaException() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setEdad(-1);

    assertThrows(
      EdadInvalidaException.class,
      () -> servicioRegistroPerfilAlimentario.validarPerfilAlimentario(perfil)
    );
  }

  @Test
  void dadoQueIngresoUnaEdadMayorA100CuandoSeValidaLanzaEdadInvalidaException() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setEdad(101); // Tu servicio permite hasta 100 exacto

    assertThrows(
      EdadInvalidaException.class,
      () -> servicioRegistroPerfilAlimentario.validarPerfilAlimentario(perfil)
    );
  }

  @Test
  void dadoQueIngresoUnSexoInvalidoCuandoSeValidaLanzaSexoInvalidoException() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setSexo("x");

    assertThrows(
      SexoInvalidoException.class,
      () -> servicioRegistroPerfilAlimentario.validarPerfilAlimentario(perfil)
    );
  }

  @Test
  void dadoQueIngresoUnaActividadFisicaInvalidaCuandoSeValidaLanzaActividadFisicaInvalidaException() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setActividadFisica("x");

    assertThrows(
      ActividadFisicaInvalidaException.class,
      () -> servicioRegistroPerfilAlimentario.validarPerfilAlimentario(perfil)
    );
  }

  @Test
  void dadoQueIngresoUnObjetivoInvalidoCuandoSeValidaLanzaObjetivoInvalidaException() {
    PerfilAlimentarioDTO perfil = givenPerfilConDatosValidos();
    perfil.setObjetivo("x");

    assertThrows(
      ObjetivoInvalidaException.class,
      () -> servicioRegistroPerfilAlimentario.validarPerfilAlimentario(perfil)
    );
  }

  private PerfilAlimentarioDTO givenPerfilConDatosValidos() {
    Double peso = 100.0;
    Double altura = 100.0;
    Integer edad = 20;
    String sexo = "M";
    String actividadFisica = "ACTIVA";
    Set<String> restriccionesAlimentarias = new HashSet<>();
    restriccionesAlimentarias.add("CELIACO");
    restriccionesAlimentarias.add("DIABETICO");
    String objetivo = "GANAR_PESO";

    return new PerfilAlimentarioDTO(
      peso,
      altura,
      edad,
      sexo,
      actividadFisica,
      restriccionesAlimentarias,
      objetivo
    );
  }

  private Usuario givenUsuarioExiste() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setEmail("test@test.com");
    usuario.setPassword("test");
    usuario.setRol("user");
    return usuario;
  }
}
