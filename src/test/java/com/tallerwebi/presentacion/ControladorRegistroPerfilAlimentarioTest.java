package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.ActividadFisicaTipo;
import com.tallerwebi.dominio.ObjetivoTipo;
import com.tallerwebi.dominio.PerfilAlimentarioDTO;
import com.tallerwebi.dominio.ServicioRegistroPerfilAlimentario;
import com.tallerwebi.dominio.SexoTipo;
import com.tallerwebi.dominio.excepcion.perfilException.ActividadFisicaInvalidaException;
import com.tallerwebi.dominio.excepcion.perfilException.AlturaInvalidaException;
import com.tallerwebi.dominio.excepcion.perfilException.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.perfilException.ObjetivoInvalidaException;
import com.tallerwebi.dominio.excepcion.perfilException.PerfilAlimentarioDTONuloException;
import com.tallerwebi.dominio.excepcion.perfilException.PesoInvalidoException;
import com.tallerwebi.dominio.excepcion.perfilException.RestriccionesAlimentariasInvalidasException;
import com.tallerwebi.dominio.excepcion.perfilException.SexoInvalidoException;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorRegistroPerfilAlimentarioTest {

  private static final String ATT_ERROR = "error";
  private static final String ATT_USUARIO_LOGUEADO = "usuarioLogueadoEmail";
  private static final String VISTA_REGISTRO = "registroPerfilAlimentario";
  private static final String REDIRECT_LOGIN = "redirect:/login";
  private static final String HOME = "redirect:/";

  private ServicioRegistroPerfilAlimentario servicioRegistroPerfilAlimentarioMock;
  private ControladorRegistroPerfilAlimentario controlador;
  private HttpSession sessionMock;
  private String usuarioLogueadoEmail;
  private PerfilAlimentarioDTO perfilAlimentarioDTO;

  @BeforeEach
  public void init() {
    servicioRegistroPerfilAlimentarioMock = mock(ServicioRegistroPerfilAlimentario.class);
    controlador = new ControladorRegistroPerfilAlimentario(servicioRegistroPerfilAlimentarioMock);
    sessionMock = mock(HttpSession.class);
    usuarioLogueadoEmail = "eric@gmail.com";
    perfilAlimentarioDTO = new PerfilAlimentarioDTO();
  }

  private PerfilAlimentarioDTO generarPerfilValido() {
    PerfilAlimentarioDTO perfil = new PerfilAlimentarioDTO();
    perfil.setPeso(60.0);
    perfil.setAltura(1.70);
    perfil.setEdad(25);
    perfil.setSexo(SexoTipo.M.name());
    perfil.setActividadFisica(ActividadFisicaTipo.ACTIVA.name());
    perfil.setObjetivo(ObjetivoTipo.MANTENER_PESO.name());
    return perfil;
  }

  // mostrarFormulario
  @Test
  public void siElUsuarioNoEstaLogueadoAlIntentarAccederAlFormularioSeRedirigeAlLogin() {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(null);
    // When
    ModelAndView modelAndView = controlador.mostrarFormulario(sessionMock);
    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(REDIRECT_LOGIN));
  }

  @Test
  public void siElUsuarioEstaLogueadoAlIntentarAccederAlFormularioSeMuestraElFormulario() {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);
    // When
    ModelAndView modelAndView = controlador.mostrarFormulario(sessionMock);
    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_REGISTRO));
  }

  // procesarFormulario
  @Test
  public void alIntentarRegistrarUnPerfilAlimentarioValidoSeMuestraElHome() {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);
    PerfilAlimentarioDTO perfilValido = generarPerfilValido();

    // When
    ModelAndView modelAndView = controlador.procesarFormulario(perfilValido, sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(HOME));
  }

  // mensajes de error
  @Test
  public void alIntentarRegistrarUnPesoInvalidoLanzaPesoInvalidoExceptionYVuelveAlRegistroConMensaje()
    throws Exception {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);
    doThrow(new PesoInvalidoException())
      .when(servicioRegistroPerfilAlimentarioMock)
      .guardarPerfilAlimentario(any(PerfilAlimentarioDTO.class), eq(usuarioLogueadoEmail));

    // When
    ModelAndView modelAndView = controlador.procesarFormulario(perfilAlimentarioDTO, sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_REGISTRO));
    assertThat(
      modelAndView.getModel().get(ATT_ERROR).toString(),
      equalToIgnoringCase("Error: El peso ingresado no es valido (Debe ser entre 0 y 635 kg)")
    );
  }

  @Test
  public void alIntentarRegistrarUnaActividadFisicaInvalidaVuelveAlRegistroConMensaje()
    throws Exception {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);

    doThrow(new ActividadFisicaInvalidaException())
      .when(servicioRegistroPerfilAlimentarioMock)
      .guardarPerfilAlimentario(any(PerfilAlimentarioDTO.class), eq(usuarioLogueadoEmail));

    // When
    ModelAndView modelAndView = controlador.procesarFormulario(perfilAlimentarioDTO, sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_REGISTRO));
    assertThat(
      modelAndView.getModel().get(ATT_ERROR).toString(),
      equalToIgnoringCase("Error: Actividad fisica invalida, vuelva a intentarlo")
    );
  }

  @Test
  public void alIntentarRegistrarUnaAlturaInvalidaVuelveAlRegistroConMensaje() throws Exception {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);

    doThrow(new AlturaInvalidaException())
      .when(servicioRegistroPerfilAlimentarioMock)
      .guardarPerfilAlimentario(any(PerfilAlimentarioDTO.class), eq(usuarioLogueadoEmail));

    // When
    ModelAndView modelAndView = controlador.procesarFormulario(perfilAlimentarioDTO, sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_REGISTRO));
    assertThat(
      modelAndView.getModel().get(ATT_ERROR).toString(),
      equalToIgnoringCase("Error: La altura ingresada no es valida (Debe ser entre 0 y 272 cm)")
    );
  }

  @Test
  public void alIntentarRegistrarUnaEdadInvalidaVuelveAlRegistroConMensaje() throws Exception {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);

    doThrow(new EdadInvalidaException())
      .when(servicioRegistroPerfilAlimentarioMock)
      .guardarPerfilAlimentario(any(PerfilAlimentarioDTO.class), eq(usuarioLogueadoEmail));

    // When
    ModelAndView modelAndView = controlador.procesarFormulario(perfilAlimentarioDTO, sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_REGISTRO));
    assertThat(
      modelAndView.getModel().get(ATT_ERROR).toString(),
      equalToIgnoringCase("Error: La edad ingresada no es valida (Debe ser entre 0 y 100 años)")
    );
  }

  @Test
  public void alIntentarRegistrarUnObjetivoInvalidoVuelveAlRegistroConMensaje() throws Exception {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);

    doThrow(new ObjetivoInvalidaException())
      .when(servicioRegistroPerfilAlimentarioMock)
      .guardarPerfilAlimentario(any(PerfilAlimentarioDTO.class), eq(usuarioLogueadoEmail));

    // When
    ModelAndView modelAndView = controlador.procesarFormulario(perfilAlimentarioDTO, sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_REGISTRO));
    assertThat(
      modelAndView.getModel().get(ATT_ERROR).toString(),
      equalToIgnoringCase("Error: Objetivo invalido, vuelva a intentarlo")
    );
  }

  @Test
  public void alIntentarRegistrarUnPerfilNuloVuelveAlRegistroConMensaje() throws Exception {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);

    doThrow(new PerfilAlimentarioDTONuloException())
      .when(servicioRegistroPerfilAlimentarioMock)
      .guardarPerfilAlimentario(any(PerfilAlimentarioDTO.class), eq(usuarioLogueadoEmail));

    // When
    ModelAndView modelAndView = controlador.procesarFormulario(perfilAlimentarioDTO, sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_REGISTRO));
    assertThat(
      modelAndView.getModel().get(ATT_ERROR).toString(),
      equalToIgnoringCase("Error: Perfil esta vacio, vuelva a intentarlo")
    );
  }

  @Test
  public void alIntentarRegistrarRestriccionesAlimentariasInvalidadasVuelveAlRegistroConMensaje()
    throws Exception {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);

    doThrow(new RestriccionesAlimentariasInvalidasException())
      .when(servicioRegistroPerfilAlimentarioMock)
      .guardarPerfilAlimentario(any(PerfilAlimentarioDTO.class), eq(usuarioLogueadoEmail));

    // When
    ModelAndView modelAndView = controlador.procesarFormulario(perfilAlimentarioDTO, sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_REGISTRO));
    assertThat(
      modelAndView.getModel().get(ATT_ERROR).toString(),
      equalToIgnoringCase("Error: Una o mas restricciones alimentarias seleccionadas son invlidas")
    );
  }

  @Test
  public void alIntentarRegistrarUnSexoInvalidoVuelveAlRegistroConMensaje() throws Exception {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);

    doThrow(new SexoInvalidoException())
      .when(servicioRegistroPerfilAlimentarioMock)
      .guardarPerfilAlimentario(any(PerfilAlimentarioDTO.class), eq(usuarioLogueadoEmail));

    // When
    ModelAndView modelAndView = controlador.procesarFormulario(perfilAlimentarioDTO, sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_REGISTRO));
    assertThat(
      modelAndView.getModel().get(ATT_ERROR).toString(),
      equalToIgnoringCase("Error: Sexo invalido, vuelva a intentarlo")
    );
  }

  @Test
  public void alLanzarCualquierOtraExcepcionGenericaMuestraElMensajePorDefecto() throws Exception {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);

    doThrow(new RuntimeException())
      .when(servicioRegistroPerfilAlimentarioMock)
      .guardarPerfilAlimentario(any(PerfilAlimentarioDTO.class), eq(usuarioLogueadoEmail));

    // When
    ModelAndView modelAndView = controlador.procesarFormulario(perfilAlimentarioDTO, sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_REGISTRO));
    assertThat(
      modelAndView.getModel().get(ATT_ERROR).toString(),
      equalToIgnoringCase("Error inesperado, vuelva a intentarlo")
    );
  }
}
