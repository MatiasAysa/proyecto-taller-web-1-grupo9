package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.excepcion.PresupuestoNoPositivoException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPresupuestoException;
import com.tallerwebi.infraestructura.RepositorioPresupuestoImpl;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.presentacion.DatosPresupuesto;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class ServicioPresupuestoTest {

  private RepositorioPresupuesto repositorioPresupuesto = mock(RepositorioPresupuesto.class);
  private RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
  private ServicioPresupuesto servicioPresupuesto = new ServicioPresupuestoImpl(repositorioPresupuesto, repositorioUsuario);
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

  @Test
  public void sePuedeBuscarUnPresupuesto() {
    Usuario usuario = new Usuario();
    usuario.setEmail("a@a.com");
    Presupuesto presupuesto = new Presupuesto();
    presupuesto.setUsuario(usuario);

    DatosPresupuesto presupuestoEncontrado = whenBuscoPresupuesto(usuario, presupuesto);
    thenSeEncuentraElPresupuesto(presupuestoEncontrado, presupuesto);
  }

  @Test
  public void siElUsuarioNoTienePresupuestoSeDevuelveUnaExcepcion() {
    Usuario usuario = new Usuario();
    usuario.setEmail("a@a.com");

    assertThrows(UsuarioSinPresupuestoException.class, () -> whenBuscoPresupuesto(usuario, null));
  }

  private void thenSeEncuentraElPresupuesto(
    DatosPresupuesto presupuestoEncontrado,
    Presupuesto presupuesto
  ) {
    assertThat(presupuestoEncontrado.getMonto(), equalTo(presupuesto.getMonto()));
    assertThat(presupuestoEncontrado.getIntervalo(), equalTo(presupuesto.getIntervalo()));
    assertThat(presupuestoEncontrado.getFecha(), equalTo(presupuesto.getFecha()));
  }

  private DatosPresupuesto whenBuscoPresupuesto(Usuario usuario, Presupuesto presupuesto) {
    when(repositorioPresupuesto.buscarPresupuesto(usuario)).thenReturn(presupuesto);
    when(repositorioUsuario.buscar(usuario.getEmail())).thenReturn(usuario);
    return servicioPresupuesto.buscarPresupuesto(usuario.getEmail());
  }

  private void thenSeCreaElPresupuesto(Presupuesto resultado) {
    assertThat(resultado, is(notNullValue()));
  }

  private Presupuesto whenUsuarioIngresaMontoEIntervalo(float monto, int intervalo, LocalDate fecha)
  {
    return servicioPresupuesto.crearPresupuesto(monto, intervalo, fecha, "a@a.com");
  }

  private void givenExisteUsuario() {}
}
