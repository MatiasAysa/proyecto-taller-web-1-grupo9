package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorPresupuestoTest {

  private final float monto = 100000;
  private final int intervalo = 7;
  private final LocalDate fecha = LocalDate.now();
  private ControladorPresupuesto controladorPresupuesto = new ControladorPresupuesto();
  private String mensaje;

  @Test
  public void siIngresoMontoFechaEIntervaloElPresupuestoEsExitoso() {
    givenUsuarioExiste();
    ModelAndView mav = whenIngresoMontoEIntervalo(new DatosPresupuesto(monto, intervalo, fecha));
    thenSeCreaElPresupuesto(mav);
  }

  @Test
  public void siIngresoMontoYFechaYNoIngresoIntervaloElPresupuestoFalla() {
    givenUsuarioExiste();
    ModelAndView mav = whenIngresoMontoEIntervalo(new DatosPresupuesto(monto, 0, fecha));
    mensaje = controladorPresupuesto.getMENSAJE_INTERVALO_OBLIGATORIO();
    thenNoSeCreaElPresupuesto(mav, mensaje);
  }

  @Test
  public void siIngresoMontoEIntervaloYNoIngresoFechaElPresupuestoFalla() {
    givenUsuarioExiste();
    ModelAndView mav = whenIngresoMontoEIntervalo(new DatosPresupuesto(monto, intervalo, null));
    mensaje = controladorPresupuesto.getMENSAJE_FECHA_OBLIGATORIA();
    thenNoSeCreaElPresupuesto(mav, mensaje);
  }

  @Test
  public void siIngresoFechaEIntervaloYNoIngresoMontoElPresupuestoFalla() {
    givenUsuarioExiste();
    ModelAndView mav = whenIngresoMontoEIntervalo(new DatosPresupuesto(0, intervalo, fecha));
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
    return controladorPresupuesto.validarPresupuesto(datosPresupuesto);
  }

  private void givenUsuarioExiste() {}
}
