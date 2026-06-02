package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioPlanificadorTest {

  private ServicioPlanificador servicioPlanificador;
  private RepositorioPlanificador repositorioPlanificadorMock;
  private List<Alimento> alimentosEnStock;

  @BeforeEach
  public void init() {
    this.repositorioPlanificadorMock = mock(RepositorioPlanificador.class);
    this.servicioPlanificador = new ServicioPlanificadorImpl(this.repositorioPlanificadorMock);
    this.alimentosEnStock = new ArrayList<>();
    this.alimentosEnStock.add(crearAlimento("Pollo", 8500.0, "ALMUERZO", false, false));
    this.alimentosEnStock.add(crearAlimento("Lentejas", 2200.0, "ALMUERZO", true, false));
    this.alimentosEnStock.add(crearAlimento("Avena", 1500.0, "DESAYUNO", true, false));
    this.alimentosEnStock.add(crearAlimento("Yogur Entero", 3500.0, "DESAYUNO", true, true)); // Contiene lactosa
    this.alimentosEnStock.add(crearAlimento("Fruta", 1000.0, "MERIENDA", true, false));
    this.alimentosEnStock.add(crearAlimento("Wok de Pollo", 4200.0, "CENA", false, false));
    this.alimentosEnStock.add(crearAlimento("Tortilla Espinaca", 1800.0, "CENA", true, false));
  }

  @Test
  public void queUnUsuarioVegetarianoRecibaUnPlanSinCarneYAcordeAlPresupuesto()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    // Given
    Long usuarioId = 1L;
    Usuario usuarioVegetariano = new Usuario();
    usuarioVegetariano.setId(usuarioId);
    usuarioVegetariano.setPresupuestoSemanal(40000.0);
    usuarioVegetariano.setEsVegetariano(true);
    usuarioVegetariano.setContieneLactosa(true);

    when(repositorioPlanificadorMock.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioVegetariano);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    // When
    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(usuarioId);

    // Then
    assertThat(planObtenido, notNullValue());
    assertThat(planObtenido.getCostoTotalPlan(), is(lessThanOrEqualTo(40000.0)));

    for (Alimento al : planObtenido.getAlimentosAsignados()) {
      assertThat(al.getEsVegetariano(), is(true));
    }
  }

  @Test
  public void queUnUsuarioCarnivoroConPresupuestoPremiumRecibaUnPlanVariadoConCarne()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    // Given
    Long usuarioId = 2L;
    Usuario usuarioPremium = new Usuario();
    usuarioPremium.setId(usuarioId);
    usuarioPremium.setPresupuestoSemanal(80000.0);
    usuarioPremium.setEsVegetariano(false);
    usuarioPremium.setContieneLactosa(true);

    when(repositorioPlanificadorMock.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioPremium);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    // When
    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(usuarioId);

    // Then
    assertThat(planObtenido, notNullValue());
    assertThat(planObtenido.getCostoTotalPlan(), is(greaterThan(40000.0)));

    boolean tieneCarne = planObtenido
      .getAlimentosAsignados()
      .stream()
      .anyMatch(al -> !al.getEsVegetariano());
    assertThat(tieneCarne, is(true));
  }

  @Test
  public void queUnUsuarioIntoleranteALaLactosaRecibaUnPlanLibreDeLactosa()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    // Given
    Long usuarioId = 3L;
    Usuario usuarioIntolerante = new Usuario();
    usuarioIntolerante.setId(usuarioId);
    usuarioIntolerante.setPresupuestoSemanal(50000.0);
    usuarioIntolerante.setEsVegetariano(true);
    usuarioIntolerante.setContieneLactosa(false);

    when(repositorioPlanificadorMock.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioIntolerante);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    // When
    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(usuarioId);

    // Then
    assertThat(planObtenido, notNullValue());

    for (Alimento al : planObtenido.getAlimentosAsignados()) {
      assertThat(al.getContieneLactosa(), is(false));
    }
  }

  @Test
  public void queLancePresupuestoInsuficienteExceptionSiElDineroNoCubreElMinimoEconomico() {
    // Given
    Long usuarioId = 4L;
    Usuario usuarioEvadido = new Usuario();
    usuarioEvadido.setId(usuarioId);
    usuarioEvadido.setPresupuestoSemanal(1000.0);
    usuarioEvadido.setEsVegetariano(true);
    usuarioEvadido.setContieneLactosa(true);

    when(repositorioPlanificadorMock.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioEvadido);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    // When & Then
    assertThrows(
      PresupuestoInsuficienteException.class,
      () -> {
        servicioPlanificador.generarPlanParaUsuario(usuarioId);
      }
    );
  }

  @Test
  public void queLanceUsuarioInexistenteExceptionSiElIdNoCorrespondeAUnUsuarioValido() {
    Long usuarioIdInexistente = 99L;

    when(repositorioPlanificadorMock.buscarUsuarioPorId(usuarioIdInexistente)).thenReturn(null);

    assertThrows(
      UsuarioInexistenteException.class,
      () -> {
        servicioPlanificador.generarPlanParaUsuario(usuarioIdInexistente);
      }
    );
  }

  private Alimento crearAlimento(
    String nombre,
    Double precio,
    String tipo,
    Boolean esVegetariano,
    Boolean contieneLactosa
  ) {
    Alimento alimento = new Alimento();
    alimento.setNombre(nombre);
    alimento.setPrecioEstimado(precio);
    alimento.setTipoComida(tipo);
    alimento.setEsVegetariano(esVegetariano);
    alimento.setContieneLactosa(contieneLactosa);
    alimento.setEsCeliaco(false);
    return alimento;
  }
}
