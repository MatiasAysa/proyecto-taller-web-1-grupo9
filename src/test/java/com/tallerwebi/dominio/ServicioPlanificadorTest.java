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

    this.alimentosEnStock.add(
        crearAlimento("Pollo", 8500.0, "ALMUERZO", false, false, 165, 31.0, 0.0, 3.6)
      );
    this.alimentosEnStock.add(
        crearAlimento("Lentejas", 2200.0, "ALMUERZO", true, false, 116, 9.0, 20.0, 0.4)
      );
    this.alimentosEnStock.add(
        crearAlimento("Avena", 1500.0, "DESAYUNO", true, false, 389, 16.9, 66.3, 6.9)
      );
    this.alimentosEnStock.add(
        crearAlimento("Yogur Entero", 3500.0, "DESAYUNO", true, true, 61, 3.5, 4.7, 3.3)
      );
    this.alimentosEnStock.add(
        crearAlimento("Fruta", 1000.0, "MERIENDA", true, false, 52, 0.3, 14.0, 0.2)
      );
    this.alimentosEnStock.add(
        crearAlimento("Wok de Pollo", 4200.0, "CENA", false, false, 250, 20.0, 15.0, 5.0)
      );
    this.alimentosEnStock.add(
        crearAlimento("Tortilla Espinaca", 1800.0, "CENA", true, false, 150, 7.0, 10.0, 8.0)
      );
  }

  @Test
  public void queUnUsuarioVegetarianoRecibaUnPlanSinCarneYAcordeAlPresupuestoYConMacrosCalculados()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Long usuarioId = 1L;
    Usuario usuarioVegetariano = new Usuario();
    usuarioVegetariano.setId(usuarioId);
    usuarioVegetariano.setPresupuestoSemanal(40000.0);
    usuarioVegetariano.setEsVegetariano(true);
    usuarioVegetariano.setContieneLactosa(true);

    when(repositorioPlanificadorMock.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioVegetariano);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(usuarioId);

    assertThat(planObtenido, notNullValue());
    assertThat(planObtenido.getCostoTotalPlan(), is(lessThanOrEqualTo(40000.0)));
    assertThat(planObtenido.getTotalCalorias(), is(5376));
    assertThat(planObtenido.getTotalProteinas(), is(256.9));

    for (Alimento al : planObtenido.getAlimentosAsignados()) {
      assertThat(al.getEsVegetariano(), is(true));
    }
  }

  @Test
  public void queUnUsuarioCarnivoroConPresupuestoPremiumRecibaUnPlanVariadoConCarneYMacrosTotales()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Long usuarioId = 2L;
    Usuario usuarioPremium = new Usuario();
    usuarioPremium.setId(usuarioId);
    usuarioPremium.setPresupuestoSemanal(80000.0);
    usuarioPremium.setEsVegetariano(false);
    usuarioPremium.setContieneLactosa(true);

    when(repositorioPlanificadorMock.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioPremium);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(usuarioId);

    assertThat(planObtenido, notNullValue());
    assertThat(planObtenido.getCostoTotalPlan(), is(greaterThan(40000.0)));
    assertThat(planObtenido.getTotalCalorias(), is(8281));

    boolean tieneCarne = planObtenido
      .getAlimentosAsignados()
      .stream()
      .anyMatch(al -> !al.getEsVegetariano());
    assertThat(tieneCarne, is(true));
  }

  @Test
  public void queUnUsuarioIntoleranteALaLactosaRecibaUnPlanLibreDeLactosa()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Long usuarioId = 3L;
    Usuario usuarioIntolerante = new Usuario();
    usuarioIntolerante.setId(usuarioId);
    usuarioIntolerante.setPresupuestoSemanal(50000.0);
    usuarioIntolerante.setEsVegetariano(true);
    usuarioIntolerante.setContieneLactosa(false);

    when(repositorioPlanificadorMock.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioIntolerante);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(usuarioId);

    assertThat(planObtenido, notNullValue());

    for (Alimento al : planObtenido.getAlimentosAsignados()) {
      assertThat(al.getContieneLactosa(), is(false));
    }
  }

  @Test
  public void queLancePresupuestoInsuficienteExceptionSiElDineroNoCubreElMinimoEconomico() {
    Long usuarioId = 4L;
    Usuario usuarioEvadido = new Usuario();
    usuarioEvadido.setId(usuarioId);
    usuarioEvadido.setPresupuestoSemanal(1000.0);
    usuarioEvadido.setEsVegetariano(true);
    usuarioEvadido.setContieneLactosa(true);

    when(repositorioPlanificadorMock.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioEvadido);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

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
    Boolean contieneLactosa,
    Integer calorias,
    Double proteinas,
    Double carbohidratos,
    Double grasas
  ) {
    Alimento alimento = new Alimento();
    alimento.setNombre(nombre);
    alimento.setPrecioEstimado(precio);
    alimento.setEsVegetariano(esVegetariano);
    alimento.setContieneLactosa(contieneLactosa);
    alimento.setEsCeliaco(false);
    alimento.setCalorias(calorias);
    alimento.setProteinas(proteinas);
    alimento.setCarbohidratos(carbohidratos);
    alimento.setGrasas(grasas);
    return alimento;
  }
}
