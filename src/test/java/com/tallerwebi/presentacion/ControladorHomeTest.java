package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.ServicioUsuario;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorHomeTest {

  private static final String ATT_USUARIO_LOGUEADO = "usuarioLogueadoEmail";
  private static final String VISTA_HOME = "home";
  private static final String REDIRECT_HOME = "redirect:/home";
  private static final String REDIRECT_LOGIN = "redirect:/login";
  private static final String REDIRECT_REGISTRO_PERFIL = "redirect:/Registro-perfil-alimentario";
  private static final String REDIRECT_PRESUPUESTO = "redirect:/configurar-presupuesto";
  private static final String REDIRECT_LISTA_COMPRAS = "redirect:/lista-compras";

  private ServicioUsuario servicioUsuarioMock;
  private ControladorHome controlador;
  private HttpSession sessionMock;
  private String usuarioLogueadoEmail;

  @BeforeEach
  public void init() {
    servicioUsuarioMock = mock(ServicioUsuario.class);
    controlador = new ControladorHome(servicioUsuarioMock);
    sessionMock = mock(HttpSession.class);
    usuarioLogueadoEmail = "test@correo.com";
  }

  // --- TESTS DE irAHome ---

  @Test
  public void siElUsuarioEstaLogueadoAlIrAHomeSeRetornaLaVistaHomeConElEmailEnElModelo() {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);

    // When
    ModelAndView modelAndView = controlador.irAHome(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_HOME));
    assertThat(modelAndView.getModel().get(ATT_USUARIO_LOGUEADO), is(notNullValue()));
    assertThat(
      modelAndView.getModel().get(ATT_USUARIO_LOGUEADO).toString(),
      equalToIgnoringCase(usuarioLogueadoEmail)
    );
  }

  @Test
  public void siElUsuarioNoEstaLogueadoAlIrAHomeSeRetornaLaVistaHomeSinModelo() {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(null);

    // When
    ModelAndView modelAndView = controlador.irAHome(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_HOME));
    assertThat(modelAndView.getModel().isEmpty(), is(true));
  }

  // --- TESTS DE logout ---

  @Test
  public void alHacerLogoutSeRemueveElAtributoDeSesionYSeRedirigeAHome() {
    // When
    ModelAndView modelAndView = controlador.logout(sessionMock);

    // Then
    verify(sessionMock).removeAttribute(ATT_USUARIO_LOGUEADO);
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(REDIRECT_HOME));
  }

  // --- TESTS DE irAPlanificar ---

  @Test
  public void alPlanificarSiElUsuarioNoEstaLogueadoRedirigeAlLogin() {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(null);

    // When
    ModelAndView modelAndView = controlador.irAPlannificar(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(REDIRECT_LOGIN));
  }

  @Test
  public void alPlanificarSiEstaLogueadoPeroNoTienePerfilAlimentarioRedirigeAlRegistroDePerfil() {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);
    when(servicioUsuarioMock.tienePerfilAlimentario(usuarioLogueadoEmail)).thenReturn(false);

    // When
    ModelAndView modelAndView = controlador.irAPlannificar(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(REDIRECT_REGISTRO_PERFIL));
  }

  @Test
  public void alPlanificarSiTienePerfilPeroNoTienePresupuestoRedirigeAConfigurarPresupuesto() {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);
    when(servicioUsuarioMock.tienePerfilAlimentario(usuarioLogueadoEmail)).thenReturn(true);
    when(servicioUsuarioMock.tienePresupuesto(usuarioLogueadoEmail)).thenReturn(false);

    // When
    ModelAndView modelAndView = controlador.irAPlannificar(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(REDIRECT_PRESUPUESTO));
  }

  @Test
  public void alPlanificarSiTienePerfilYTienePresupuestoRedirigeAListaDeCompras() {
    // Given
    when(sessionMock.getAttribute(ATT_USUARIO_LOGUEADO)).thenReturn(usuarioLogueadoEmail);
    when(servicioUsuarioMock.tienePerfilAlimentario(usuarioLogueadoEmail)).thenReturn(true);
    when(servicioUsuarioMock.tienePresupuesto(usuarioLogueadoEmail)).thenReturn(true);

    // When
    ModelAndView modelAndView = controlador.irAPlannificar(sessionMock);

    // Then
    assertThat(modelAndView.getViewName(), equalToIgnoringCase(REDIRECT_LISTA_COMPRAS));
  }
}
