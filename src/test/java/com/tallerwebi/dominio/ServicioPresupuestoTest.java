package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.google.protobuf.NullValue;
import com.tallerwebi.dominio.excepcion.PresupuestoNoPositivoException;
import java.time.LocalDate;

import com.tallerwebi.infraestructura.RepositorioPresupuestoImpl;
import org.junit.jupiter.api.Test;

public class ServicioPresupuestoTest {

  private RepositorioPresupuesto repositorioPresupuesto = mock(RepositorioPresupuestoImpl.class);
  private ServicioPresupuesto servicioPresupuesto = new ServicioPresupuestoImpl(repositorioPresupuesto);
  private final float monto = 200000;
  private final int intervalo = 7;
  private final LocalDate fecha = LocalDate.now();

  @Test
  public void siElUsuarioIngresaUnMontoFechaEIntervaloSeCreaElPresupuesto() {
    givenExisteUsuario();
    Presupuesto resultado = whenUsuarioIngresaMontoEIntervalo(monto, intervalo, fecha);
    thenSeCreaElPresupuesto(resultado);
  }

  @Test
  public void siElMontoEsCeroONegativoElPresupuestoNoSeCrea() {
    givenExisteUsuario();
    assertThrows(
      PresupuestoNoPositivoException.class,
      () -> whenUsuarioIngresaMontoEIntervalo(0, intervalo, fecha)
    );
  }

  private void thenSeCreaElPresupuesto(Presupuesto resultado) {
    assertThat(resultado, is(notNullValue()));
  }

  private Presupuesto whenUsuarioIngresaMontoEIntervalo(
    float monto,
    int intervalo,
    LocalDate fecha
  ) {
    return servicioPresupuesto.crearPresupuesto(monto, intervalo, fecha);
  }

  private void givenExisteUsuario() {}
}
