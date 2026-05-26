package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.google.protobuf.NullValue;
import org.junit.jupiter.api.Test;

public class ServicioPresupuestoTest {

  private ServicioPresupuesto servicioPresupuesto = new ServicioPresupuestoImpl();
  private final float monto = 200000;
  private final float intervalo = 7;

  @Test
  public void siElUsuarioIngresaUnMontoEIntervaloSeCreaElPresupuesto() {
    givenExisteUsuario();
    Presupuesto resultado = whenUsuarioIngresaMontoEIntervalo(monto, intervalo);
    thenSeCreaElPresupuesto(resultado);
  }

  @Test
  public void siElUsuarioIngresaUnMontoCeroONegativoNoSeCreaElPresupuesto() {
    givenExisteUsuario();
    Presupuesto resultado = whenUsuarioIngresaMontoCeroONegativoEIntervalo(0f, intervalo);
    thenNoSeCreaElPresupuesto(resultado);
  }

  @Test
  public void siElUsuarioIngresaUnIntervaloCeroONegativoNoSeCreaElPresupuesto() {
    givenExisteUsuario();
    Presupuesto resultado = whenUsuarioIngresaMontoEIntervaloCeroONegativo(monto, 0);
    thenNoSeCreaElPresupuesto(resultado);
  }

  private Presupuesto whenUsuarioIngresaMontoEIntervaloCeroONegativo(float monto, float intervalo) {
    return whenUsuarioIngresaMontoEIntervalo(monto, intervalo);
  }

  private void thenNoSeCreaElPresupuesto(Presupuesto resultado) {
    assertThat(resultado, is(nullValue()));
  }

  private Presupuesto whenUsuarioIngresaMontoCeroONegativoEIntervalo(float monto, float intervalo) {
    return whenUsuarioIngresaMontoEIntervalo(monto, intervalo);
  }

  private void thenSeCreaElPresupuesto(Presupuesto resultado) {
    assertThat(resultado, is(notNullValue()));
  }

  private Presupuesto whenUsuarioIngresaMontoEIntervalo(float monto, float intervalo) {
    return servicioPresupuesto.crearPresupuesto(monto, intervalo);
  }

  private void givenExisteUsuario() {}
}
