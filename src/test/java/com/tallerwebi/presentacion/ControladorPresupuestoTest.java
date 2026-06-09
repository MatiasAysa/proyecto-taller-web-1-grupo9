package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.ServicioPresupuesto;
import com.tallerwebi.dominio.ServicioPresupuestoImpl;
import com.tallerwebi.dominio.excepcion.PresupuestoNoPositivoException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPresupuestoException;
import java.time.LocalDate;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorPresupuestoTest {

  private final float monto = 100000;
  private final int intervalo = 7;
  private final LocalDate fecha = LocalDate.now();
  private ServicioPresupuesto servicioPresupuesto = mock(ServicioPresupuestoImpl.class);
  private ControladorPresupuesto controladorPresupuesto = new ControladorPresupuesto(servicioPresupuesto);
  private HttpSession session = mock(HttpSession.class);
  private final String email = "a@a.com";
  private String mensaje;

  @Test
  public void siNoInicioSesionConfigurarPresupuestoVuelveALogin() {
    ModelAndView mav = whenNoInicioSesionEnVistaConfigurarPresupuesto();
    thenSeVuelveAlLogin(mav);
  }

  @Test
  public void siNoInicioSesionMiPresupuestoVuelveALogin() {
    ModelAndView mav = whenNoInicioSesionEnVistaMiPresupuesto();
    thenSeVuelveAlLogin(mav);
  }

  @Test
  public void sePuedeIrAMiPresupuesto() {
    DatosPresupuesto datosPresupuesto = new DatosPresupuesto();
    datosPresupuesto.setMonto(monto);
    datosPresupuesto.setIntervalo(intervalo);
    datosPresupuesto.setFecha(fecha);
    ModelAndView mav = whenIrAPresupuesto(datosPresupuesto);
    thenVoyAMiPresupuesto(mav);
  }

  @Test
  public void sePuedeIrAConfigurarPresupuesto() {
    ModelAndView mav = whenIrAConfigurarPresupuesto();
    thenVoyAConfigurarPresupuesto(mav);
  }

  private ModelAndView whenIrAConfigurarPresupuesto() {
    when(session.getAttribute("usuarioLogueadoEmail")).thenReturn(email);
    return controladorPresupuesto.irAConfigurarPresupuesto(session);
  }

  private void thenVoyAConfigurarPresupuesto(ModelAndView mav) {
    assertThat(mav.getViewName(), equalToIgnoringCase("configurar-presupuesto"));
  }

  @Test
  public void siNoHayUnPresupuestoMiPresupuestoRedirigeAConfigurarPresupuesto() {
    DatosPresupuesto datosPresupuesto = new DatosPresupuesto();
    ModelAndView mav = whenIrAPresupuestoSinPresupuesto(datosPresupuesto);
    thenRedirigeAConfigurarPresupuesto(mav);
  }

  private ModelAndView whenIrAPresupuestoSinPresupuesto(DatosPresupuesto datosPresupuesto) {
    when(session.getAttribute("usuarioLogueadoEmail")).thenReturn(email);
    when(session.getAttribute("usuarioLogueadoEmail").toString()).thenReturn(email);
    doThrow(UsuarioSinPresupuestoException.class)
      .when(servicioPresupuesto)
      .buscarPresupuesto(email);
    return controladorPresupuesto.irAMiPresupuesto(session);
  }

  private void thenRedirigeAConfigurarPresupuesto(ModelAndView mav) {
    assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/configurar-presupuesto"));
  }

  private void thenVoyAMiPresupuesto(ModelAndView mav) {
    assertThat(mav.getViewName(), equalToIgnoringCase("mi-presupuesto"));
  }

  private ModelAndView whenIrAPresupuesto(DatosPresupuesto datosPresupuesto) {
    when(session.getAttribute("usuarioLogueadoEmail")).thenReturn(email);
    when(session.getAttribute("usuarioLogueadoEmail").toString()).thenReturn(email);
    when(servicioPresupuesto.buscarPresupuesto(email)).thenReturn(datosPresupuesto);
    return controladorPresupuesto.irAMiPresupuesto(session);
  }

  private ModelAndView whenNoInicioSesionEnVistaMiPresupuesto() {
    when(session.getAttribute("usuarioLogueadoEmail")).thenReturn(null);
    return controladorPresupuesto.irAMiPresupuesto(session);
  }

  private ModelAndView whenNoInicioSesionEnVistaConfigurarPresupuesto() {
    when(session.getAttribute("usuarioLogueadoEmail")).thenReturn(null);
    return controladorPresupuesto.irAConfigurarPresupuesto(session);
  }

  private void thenSeVuelveAlLogin(ModelAndView mav) {
    assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/login"));
  }

  @Test
  public void siIngresoMontoFechaEIntervaloElPresupuestoEsExitoso() {
    givenUsuarioExiste();
    DatosPresupuesto datosPresupuesto = new DatosPresupuesto();
    datosPresupuesto.setMonto(monto);
    datosPresupuesto.setIntervalo(intervalo);
    datosPresupuesto.setFecha(fecha);
    ModelAndView mav = whenIngresoMontoEIntervalo(datosPresupuesto);
    thenSeCreaElPresupuesto(mav);
  }

  @Test
  public void siIngresoMontoYFechaYNoIngresoIntervaloElPresupuestoFalla() {
    givenUsuarioExiste();
    DatosPresupuesto datosPresupuesto = new DatosPresupuesto();
    datosPresupuesto.setMonto(monto);
    datosPresupuesto.setIntervalo(0);
    datosPresupuesto.setFecha(fecha);
    ModelAndView mav = whenIngresoMontoEIntervalo(datosPresupuesto);
    mensaje = controladorPresupuesto.getMENSAJE_INTERVALO_OBLIGATORIO();
    thenNoSeCreaElPresupuesto(mav, mensaje);
  }

  @Test
  public void siIngresoMontoEIntervaloYNoIngresoFechaElPresupuestoFalla() {
    givenUsuarioExiste();
    DatosPresupuesto datosPresupuesto = new DatosPresupuesto();
    datosPresupuesto.setMonto(monto);
    datosPresupuesto.setIntervalo(intervalo);
    datosPresupuesto.setFecha(null);
    ModelAndView mav = whenIngresoMontoEIntervalo(datosPresupuesto);
    mensaje = controladorPresupuesto.getMENSAJE_FECHA_OBLIGATORIA();
    thenNoSeCreaElPresupuesto(mav, mensaje);
  }

  @Test
  public void siIngresoFechaEIntervaloYNoIngresoMontoElPresupuestoFalla() {
    givenUsuarioExiste();
    DatosPresupuesto datosPresupuesto = new DatosPresupuesto();
    datosPresupuesto.setMonto(0);
    datosPresupuesto.setIntervalo(intervalo);
    datosPresupuesto.setFecha(fecha);
    doThrow(PresupuestoNoPositivoException.class)
      .when(servicioPresupuesto)
      .crearPresupuesto(0, intervalo, fecha, email);

    ModelAndView mav = whenIngresoMontoEIntervalo(datosPresupuesto);
    mensaje = controladorPresupuesto.getMENSAJE_MONTO_OBLIGATORIO();
    thenNoSeCreaElPresupuesto(mav, mensaje);
  }

  private void thenNoSeCreaElPresupuesto(ModelAndView mav, String mensaje) {
    assertThat(mav.getViewName(), equalToIgnoringCase("configurar-presupuesto"));
    assertThat(mav.getModel().get("mensaje").toString(), equalToIgnoringCase(mensaje));
  }

  private void thenSeCreaElPresupuesto(ModelAndView mav) {
    assertThat(mav.getViewName(), equalToIgnoringCase("mi-presupuesto"));
    assertThat(
      mav.getModel().get("mensaje").toString(),
      equalToIgnoringCase(controladorPresupuesto.getMENSAJE_PRESUPUESTO_EXITOSO())
    );
  }

  private ModelAndView whenIngresoMontoEIntervalo(DatosPresupuesto datosPresupuesto) {
    when(session.getAttribute("usuarioLogueadoEmail")).thenReturn(email);

    return controladorPresupuesto.validarPresupuesto(datosPresupuesto, session);
  }

  private void givenUsuarioExiste() {}
}
