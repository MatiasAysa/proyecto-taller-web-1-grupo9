package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.PlanAlimenticio;
import com.tallerwebi.dominio.ServicioPlanificador;
import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorPlanificadorTest {

  private ControladorPlanificador controladorPlanificador;
  private ServicioPlanificador servicioPlanificadorMock;
  private PlanAlimenticio planMock;

  @BeforeEach
  public void init() {
    this.servicioPlanificadorMock = mock(ServicioPlanificador.class);
    this.planMock = mock(PlanAlimenticio.class);
    this.controladorPlanificador = new ControladorPlanificador(servicioPlanificadorMock);
  }

  @Test
  public void irAlPlanificadorDeberiaRetornarVistaPlanificador() {
    ModelAndView modelAndView = controladorPlanificador.irAPlanificador();
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("planificador"));
  }

  @Test
  public void generarPlanConExitoDeberiaRetornarVistaConElPlanAsociado()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Long usuarioIdSimulado = 1L;

    when(servicioPlanificadorMock.generarPlanParaUsuario(usuarioIdSimulado)).thenReturn(planMock);
    ModelAndView modelAndView = controladorPlanificador.generarPlanAutomatizado(usuarioIdSimulado);

    assertThat(modelAndView.getViewName(), equalToIgnoringCase("planificador"));
    assertThat(modelAndView.getModel().get("planGenerado"), notNullValue());
    verify(servicioPlanificadorMock, times(1)).generarPlanParaUsuario(usuarioIdSimulado);
  }

  @Test
  public void siElPresupuestoEsInsuficienteExtremoDeberiaMostrarMensajeDeError()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Long usuarioIdSimulado = 1L;

    when(servicioPlanificadorMock.generarPlanParaUsuario(usuarioIdSimulado))
      .thenThrow(new PresupuestoInsuficienteException("Presupuesto insuficiente extremo"));

    ModelAndView modelAndView = controladorPlanificador.generarPlanAutomatizado(usuarioIdSimulado);

    assertThat(modelAndView.getViewName(), equalToIgnoringCase("planificador"));
    assertThat(
      modelAndView.getModel().get("error").toString(),
      equalToIgnoringCase("No se pudo generar el plan: Presupuesto insuficiente extremo")
    );
  }

  @Test
  public void siElUsuarioNoExisteEnElSistemaDeberiaMostrarMensajeDeErrorEspecifico()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Long usuarioIdInexistente = 99L;
    String mensajeErrorEsperado = "El usuario solicitado no existe en el sistema.";

    when(servicioPlanificadorMock.generarPlanParaUsuario(usuarioIdInexistente))
      .thenThrow(new UsuarioInexistenteException(mensajeErrorEsperado));

    ModelAndView modelAndView = controladorPlanificador.generarPlanAutomatizado(
      usuarioIdInexistente
    );

    assertThat(modelAndView.getViewName(), equalToIgnoringCase("planificador"));
    assertThat(modelAndView.getModel().get("error"), notNullValue());
    assertThat(
      modelAndView.getModel().get("error").toString(),
      equalToIgnoringCase(mensajeErrorEsperado)
    );
    verify(servicioPlanificadorMock, times(1)).generarPlanParaUsuario(usuarioIdInexistente);
  }
}
