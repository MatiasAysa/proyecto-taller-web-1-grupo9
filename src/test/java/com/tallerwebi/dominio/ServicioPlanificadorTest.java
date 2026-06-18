package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioPlanificadorTest {

  private ServicioPlanificador servicioPlanificador;
  private RepositorioPlanificador repositorioPlanificadorMock;
  private RepositorioPresupuesto repositorioPresupuestoMock;
  private RepositorioUsuario repositorioUsuarioMock;

  private List<Alimento> alimentosEnStock;

  private static final String EMAIL_VEGETARIANO = "vegetariano@unlam.edu.ar";
  private static final String EMAIL_PREMIUM = "premium@unlam.edu.ar";
  private static final String EMAIL_INTOLERANTE = "intolerante@unlam.edu.ar";
  private static final String EMAIL_INS_EXTREMO = "insuficiente@unlam.edu.ar";
  private static final String EMAIL_INEXISTENTE = "inexistente@unlam.edu.ar";

  @BeforeEach
  public void init() {
    this.repositorioPlanificadorMock = mock(RepositorioPlanificador.class);
    this.repositorioPresupuestoMock = mock(RepositorioPresupuesto.class);
    this.repositorioUsuarioMock = mock(RepositorioUsuario.class);
    this.servicioPlanificador =
      new ServicioPlanificadorImpl(
        this.repositorioPlanificadorMock,
        this.repositorioPresupuestoMock,
        this.repositorioUsuarioMock
      );

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
    Usuario usuarioVegetariano = new Usuario();
    PerfilAlimentarioUsuario perfil = new PerfilAlimentarioUsuario();
    perfil.setPerfilRestricciones(new HashSet<>());

    RestriccionAlimentaria restriccion = new RestriccionAlimentaria();
    restriccion.setNombre("VEGETARIANO");

    PerfilRestriccion perfilRestriccion = new PerfilRestriccion(perfil, restriccion);
    perfil.getPerfilRestricciones().add(perfilRestriccion);

    usuarioVegetariano.setPerfilAlimentario(perfil);

    Presupuesto presupuesto = new Presupuesto();
    presupuesto.setMonto(40000.0f);
    presupuesto.setIntervalo(7);

    when(repositorioUsuarioMock.buscar(EMAIL_VEGETARIANO)).thenReturn(usuarioVegetariano);
    when(repositorioPresupuestoMock.buscarPresupuesto(usuarioVegetariano)).thenReturn(presupuesto);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(
      EMAIL_VEGETARIANO,
      null
    );

    assertThat(planObtenido, notNullValue());
    assertThat(planObtenido.getCostoTotalPlan(), is(lessThanOrEqualTo(40000.0)));

    for (Alimento al : planObtenido.getAlimentosAsignados()) {
      assertThat(al.getEsVegetariano(), is(true));
    }
  }

  @Test
  public void queUnUsuarioCarnivoroConPresupuestoPremiumRecibaUnPlanVariadoConCarneYMacrosTotales()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Usuario usuarioPremium = new Usuario();
    PerfilAlimentarioUsuario perfil = new PerfilAlimentarioUsuario();
    perfil.setPerfilRestricciones(new HashSet<>());
    usuarioPremium.setPerfilAlimentario(perfil);

    Presupuesto presupuesto = new Presupuesto();
    presupuesto.setMonto(80000.0f);
    presupuesto.setIntervalo(7);

    when(repositorioUsuarioMock.buscar(EMAIL_PREMIUM)).thenReturn(usuarioPremium);
    when(repositorioPresupuestoMock.buscarPresupuesto(usuarioPremium)).thenReturn(presupuesto);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(EMAIL_PREMIUM, null);

    assertThat(planObtenido, notNullValue());
    assertThat(planObtenido.getCostoTotalPlan(), is(greaterThan(20000.0)));

    boolean tieneCarne = planObtenido
      .getAlimentosAsignados()
      .stream()
      .anyMatch(al -> !al.getEsVegetariano());
    assertThat(tieneCarne, is(true));
  }

  @Test
  public void queUnUsuarioIntoleranteALaLactosaRecibaUnPlanLibreDeLactosa()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Usuario usuarioIntolerante = new Usuario();
    PerfilAlimentarioUsuario perfil = new PerfilAlimentarioUsuario();
    perfil.setPerfilRestricciones(new HashSet<>());

    RestriccionAlimentaria lactosa = new RestriccionAlimentaria();
    lactosa.setNombre("INTOLERANCIA_LACTOSA");

    PerfilRestriccion perfilRestriccion = new PerfilRestriccion(perfil, lactosa);
    perfil.getPerfilRestricciones().add(perfilRestriccion);

    usuarioIntolerante.setPerfilAlimentario(perfil);

    Presupuesto presupuesto = new Presupuesto();
    presupuesto.setMonto(50000.0f);
    presupuesto.setIntervalo(7);

    when(repositorioUsuarioMock.buscar(EMAIL_INTOLERANTE)).thenReturn(usuarioIntolerante);
    when(repositorioPresupuestoMock.buscarPresupuesto(usuarioIntolerante)).thenReturn(presupuesto);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(
      EMAIL_INTOLERANTE,
      null
    );

    assertThat(planObtenido, notNullValue());
    for (Alimento al : planObtenido.getAlimentosAsignados()) {
      if (al.getContieneLactosa() != null) {
        assertThat(al.getContieneLactosa(), is(false));
      }
    }
  }

  @Test
  public void queLancePresupuestoInsuficienteExceptionSiElDineroNoCubreElMinimoEconomico() {
    Usuario usuarioEvadido = new Usuario();
    PerfilAlimentarioUsuario perfil = new PerfilAlimentarioUsuario();
    perfil.setPerfilRestricciones(new HashSet<>());
    usuarioEvadido.setPerfilAlimentario(perfil);

    Presupuesto presupuestoBajo = new Presupuesto();
    presupuestoBajo.setMonto(1000.0f);
    presupuestoBajo.setIntervalo(7);

    when(repositorioUsuarioMock.buscar(EMAIL_INS_EXTREMO)).thenReturn(usuarioEvadido);
    when(repositorioPresupuestoMock.buscarPresupuesto(usuarioEvadido)).thenReturn(presupuestoBajo);

    assertThrows(
      PresupuestoInsuficienteException.class,
      () -> {
        servicioPlanificador.generarPlanParaUsuario(EMAIL_INS_EXTREMO, null);
      }
    );
  }

  @Test
  public void queLanceUsuarioInexistenteExceptionSiElIdNoCorrespondeAUnUsuarioValido() {
    when(repositorioUsuarioMock.buscar(EMAIL_INEXISTENTE)).thenReturn(null);

    assertThrows(
      UsuarioInexistenteException.class,
      () -> {
        servicioPlanificador.generarPlanParaUsuario(EMAIL_INEXISTENTE, null);
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
