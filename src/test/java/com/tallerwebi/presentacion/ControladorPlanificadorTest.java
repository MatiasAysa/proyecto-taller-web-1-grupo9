package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.PlanAlimenticio;
import com.tallerwebi.dominio.ServicioPlanificador;
import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorPlanificadorTest {

  private ControladorPlanificador controladorPlanificador;
  private ServicioPlanificador servicioPlanificadorMock;
  private HttpSession sessionMock;
  private PlanAlimenticio planMock;
  private static final String CAMPO_MAIL_USUARIO = "usuarioLogueadoEmail";
  private static final String EMAIL_SIMULADO = "santiago@unlam.edu.ar";
  private static final String VISTA_PLANIFICADOR = "planificador";
  private static final String REDIRECT_LOGIN = "redirect:/login";

  @BeforeEach
  public void init() {
    this.servicioPlanificadorMock = mock(ServicioPlanificador.class);
    this.sessionMock = mock(HttpSession.class);
    this.planMock = mock(PlanAlimenticio.class);
    this.controladorPlanificador = new ControladorPlanificador(servicioPlanificadorMock);
  }

  @Test
  public void irAlPlanificadorDeberiaRetornarVistaPlanificadorSiElUsuarioEstaLogueado() {
    when(sessionMock.getAttribute(CAMPO_MAIL_USUARIO)).thenReturn(EMAIL_SIMULADO);

    ModelAndView modelAndView = controladorPlanificador.irAPlanificador(sessionMock);

    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_PLANIFICADOR));
  }

  @Test
  public void irAlPlanificadorDeberiaRedirigirAlLoginSiNoHayUsuarioEnSesion() {
    when(sessionMock.getAttribute(CAMPO_MAIL_USUARIO)).thenReturn(null);

    ModelAndView modelAndView = controladorPlanificador.irAPlanificador(sessionMock);

    assertThat(modelAndView.getViewName(), equalToIgnoringCase(REDIRECT_LOGIN));
  }

  @Test
  public void generarPlanConExitoDeberiaRetornarVistaConElPlanAsociado()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    when(sessionMock.getAttribute(CAMPO_MAIL_USUARIO)).thenReturn(EMAIL_SIMULADO);
    when(servicioPlanificadorMock.generarPlanParaUsuario(EMAIL_SIMULADO, null))
      .thenReturn(planMock);
    ModelAndView modelAndView = controladorPlanificador.generarPlanAutomatizado(sessionMock);

    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_PLANIFICADOR));
    assertThat(modelAndView.getModel().get("planGenerado"), notNullValue());
    verify(servicioPlanificadorMock, times(1)).generarPlanParaUsuario(EMAIL_SIMULADO, null);
  }

  @Test
  public void siElPresupuestoEsInsuficienteDeberiaMostrarMensajeDeError()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    when(sessionMock.getAttribute(CAMPO_MAIL_USUARIO)).thenReturn(EMAIL_SIMULADO);
    when(servicioPlanificadorMock.generarPlanParaUsuario(EMAIL_SIMULADO, null))
      .thenThrow(new PresupuestoInsuficienteException("Monto insuficiente"));
    ModelAndView modelAndView = controladorPlanificador.generarPlanAutomatizado(sessionMock);

    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_PLANIFICADOR));
    assertThat(
      modelAndView.getModel().get("error").toString(),
      equalToIgnoringCase("No se pudo generar el plan: Monto insuficiente")
    );
  }

  @Test
  public void siElUsuarioNoExisteEnElSistemaDeberiaMostrarMensajeDeErrorEspecifico()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    String mensajeErrorEsperado = "El usuario solicitado no existe en el sistema.";

    when(sessionMock.getAttribute(CAMPO_MAIL_USUARIO)).thenReturn(EMAIL_SIMULADO);
    when(servicioPlanificadorMock.generarPlanParaUsuario(EMAIL_SIMULADO, null))
      .thenThrow(new UsuarioInexistenteException(mensajeErrorEsperado));
    ModelAndView modelAndView = controladorPlanificador.generarPlanAutomatizado(sessionMock);

    assertThat(modelAndView.getViewName(), equalToIgnoringCase(VISTA_PLANIFICADOR));
    assertThat(modelAndView.getModel().get("error"), notNullValue());
    assertThat(
      modelAndView.getModel().get("error").toString(),
      equalToIgnoringCase("Error de perfil: " + mensajeErrorEsperado)
    );
  }
}
