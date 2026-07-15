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
  private ScraperService scraperServiceMock;

  private List<Comida> comidasEnStock;
  private List<Alimento> alimentosEnStock;

  private static final String EMAIL_VEGETARIANO = "vegetariano@unlam.edu.ar";
  private static final String EMAIL_PREMIUM = "premium@unlam.edu.ar";
  private static final String EMAIL_INTOLERANTE = "intolerante@unlam.edu.ar";
  private static final String EMAIL_CELIACO = "celiaco@unlam.edu.ar";
  private static final String EMAIL_COMUN = "comun@unlam.edu.ar";
  private static final String EMAIL_SIN_OPCIONES = "sinopciones@unlam.edu.ar";
  private static final String EMAIL_INS_EXTREMO = "insuficiente@unlam.edu.ar";
  private static final String EMAIL_INEXISTENTE = "inexistente@unlam.edu.ar";

  @BeforeEach
  public void init() {
    this.repositorioPlanificadorMock = mock(RepositorioPlanificador.class);
    this.repositorioPresupuestoMock = mock(RepositorioPresupuesto.class);
    this.repositorioUsuarioMock = mock(RepositorioUsuario.class);
    this.scraperServiceMock = mock(ScraperService.class);
    this.servicioPlanificador =
      new ServicioPlanificadorImpl(
        this.repositorioPlanificadorMock,
        this.repositorioPresupuestoMock,
        this.repositorioUsuarioMock,
        this.scraperServiceMock
      );

    this.alimentosEnStock = new ArrayList<>();
    this.comidasEnStock = new ArrayList<>();

    Alimento pollo = crearAlimento("Pollo", 8500.0, "ALMUERZO", false, false, 165, 31.0, 0.0, 3.6);
    Alimento lentejas = crearAlimento(
      "Lentejas",
      2200.0,
      "ALMUERZO",
      true,
      false,
      116,
      9.0,
      20.0,
      0.4
    );
    Alimento avena = crearAlimento("Avena", 1500.0, "DESAYUNO", true, false, 389, 16.9, 66.3, 6.9);
    Alimento yogur = crearAlimento(
      "Yogur Entero",
      3500.0,
      "DESAYUNO",
      true,
      true,
      61,
      3.5,
      4.7,
      3.3
    );
    Alimento fruta = crearAlimento("Fruta", 1000.0, "MERIENDA", true, false, 52, 0.3, 14.0, 0.2);
    Alimento wok = crearAlimento(
      "Wok de Pollo",
      4200.0,
      "CENA",
      false,
      false,
      250,
      20.0,
      15.0,
      5.0
    );
    Alimento tortilla = crearAlimento(
      "Tortilla Espinaca",
      1800.0,
      "CENA",
      true,
      false,
      150,
      7.0,
      10.0,
      8.0
    );

    this.alimentosEnStock.addAll(List.of(pollo, lentejas, avena, yogur, fruta, wok, tortilla));
    this.comidasEnStock.add(crearComida(pollo, TipoDeComida.ALMUERZO));
    this.comidasEnStock.add(crearComida(lentejas, TipoDeComida.ALMUERZO));
    this.comidasEnStock.add(crearComida(avena, TipoDeComida.DESAYUNO));
    this.comidasEnStock.add(crearComida(yogur, TipoDeComida.DESAYUNO));
    this.comidasEnStock.add(crearComida(fruta, TipoDeComida.MERIENDA));
    this.comidasEnStock.add(crearComida(wok, TipoDeComida.CENA));
    this.comidasEnStock.add(crearComida(tortilla, TipoDeComida.CENA));
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
    presupuesto.setMonto(55000.0f);
    presupuesto.setIntervalo(7);

    when(repositorioUsuarioMock.buscar(EMAIL_VEGETARIANO)).thenReturn(usuarioVegetariano);
    when(repositorioPresupuestoMock.buscarPresupuesto(usuarioVegetariano)).thenReturn(presupuesto);

    when(repositorioPlanificadorMock.obtenerComidasDisponibles()).thenReturn(comidasEnStock);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(
      EMAIL_VEGETARIANO,
      null
    );

    assertThat(planObtenido, notNullValue());
    assertThat(planObtenido.getCostoTotalPlan(), is(lessThanOrEqualTo(55000.0)));

    for (Alimento al : planObtenido.getAlimentosAsignados()) {
      assertThat(al.getEsVegetariano(), is(true));
    }
  }

  @Test
  public void queUnUsuarioCarnivoroRecibaUnPlanVariadoConCarneYMacrosTotales()
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
    when(repositorioPlanificadorMock.obtenerComidasDisponibles()).thenReturn(comidasEnStock);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(EMAIL_PREMIUM, null);

    assertThat(planObtenido, notNullValue());
    assertThat(planObtenido.getCostoTotalPlan(), is(greaterThan(20000.0)));

    boolean tieneCarne = planObtenido
      .getAlimentosAsignados()
      .stream()
      .anyMatch(al -> !al.getEsVegetariano());

    assertThat(tieneCarne, equalTo(true));
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
    when(repositorioPlanificadorMock.obtenerComidasDisponibles()).thenReturn(comidasEnStock);
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
  public void queUnUsuarioCeliacoRecibaUnPlanExclusivamenteConAlimentosAptosParaCeliacos()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Usuario usuarioCeliaco = new Usuario();
    PerfilAlimentarioUsuario perfil = new PerfilAlimentarioUsuario();
    perfil.setPerfilRestricciones(new HashSet<>());

    RestriccionAlimentaria celiaco = new RestriccionAlimentaria();
    celiaco.setNombre("CELIACO");

    PerfilRestriccion perfilRestriccion = new PerfilRestriccion(perfil, celiaco);
    perfil.getPerfilRestricciones().add(perfilRestriccion);

    usuarioCeliaco.setPerfilAlimentario(perfil);

    Presupuesto presupuesto = new Presupuesto();
    presupuesto.setMonto(60000.0f);
    presupuesto.setIntervalo(7);

    alimentosEnStock.get(1).setEsCeliaco(true);
    alimentosEnStock.get(3).setEsCeliaco(true);

    when(repositorioUsuarioMock.buscar(EMAIL_CELIACO)).thenReturn(usuarioCeliaco);
    when(repositorioPresupuestoMock.buscarPresupuesto(usuarioCeliaco)).thenReturn(presupuesto);
    when(repositorioPlanificadorMock.obtenerComidasDisponibles()).thenReturn(comidasEnStock);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(EMAIL_CELIACO, null);

    assertThat(planObtenido, notNullValue());
    for (Alimento al : planObtenido.getAlimentosAsignados()) {
      assertThat(al.getEsCeliaco(), is(true));
    }
  }

  @Test
  public void queElPlanificadorNoFalleYSigaGenerandoElPlanSiHayComidasSinItemsEnElStock()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Usuario usuarioComun = new Usuario();
    PerfilAlimentarioUsuario perfil = new PerfilAlimentarioUsuario();
    perfil.setPerfilRestricciones(new HashSet<>());
    usuarioComun.setPerfilAlimentario(perfil);

    Presupuesto presupuesto = new Presupuesto();
    presupuesto.setMonto(50000.0f);
    presupuesto.setIntervalo(7);
    Comida comidaVacia = new Comida();
    comidaVacia.setNombre("Sopa de viento");
    comidaVacia.setTipo(TipoDeComida.CENA);
    comidaVacia.setItems(new ArrayList<>());
    comidasEnStock.add(comidaVacia);

    when(repositorioUsuarioMock.buscar(EMAIL_COMUN)).thenReturn(usuarioComun);
    when(repositorioPresupuestoMock.buscarPresupuesto(usuarioComun)).thenReturn(presupuesto);
    when(repositorioPlanificadorMock.obtenerComidasDisponibles()).thenReturn(comidasEnStock);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(EMAIL_COMUN, null);

    assertThat(planObtenido, notNullValue());
    assertThat(planObtenido.getAlimentosAsignados().size(), is(greaterThan(0)));
  }

  @Test
  public void queSeManejeLaSituacionDondeNoHayAlimentosDisponiblesQueCumplanLasRestriccionesDelUsuario()
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    Usuario usuarioExtremo = new Usuario();
    PerfilAlimentarioUsuario perfil = new PerfilAlimentarioUsuario();
    perfil.setPerfilRestricciones(new HashSet<>());

    RestriccionAlimentaria vegetariano = new RestriccionAlimentaria();
    vegetariano.setNombre("VEGETARIANO");
    RestriccionAlimentaria celiaco = new RestriccionAlimentaria();
    celiaco.setNombre("CELIACO");

    perfil.getPerfilRestricciones().add(new PerfilRestriccion(perfil, vegetariano));
    perfil.getPerfilRestricciones().add(new PerfilRestriccion(perfil, celiaco));
    usuarioExtremo.setPerfilAlimentario(perfil);

    Presupuesto presupuesto = new Presupuesto();
    presupuesto.setMonto(50000.0f);
    presupuesto.setIntervalo(7);
    for (Alimento al : alimentosEnStock) {
      al.setEsVegetariano(false);
      al.setEsCeliaco(false);
    }

    when(repositorioUsuarioMock.buscar(EMAIL_SIN_OPCIONES)).thenReturn(usuarioExtremo);
    when(repositorioPresupuestoMock.buscarPresupuesto(usuarioExtremo)).thenReturn(presupuesto);
    when(repositorioPlanificadorMock.obtenerComidasDisponibles()).thenReturn(comidasEnStock);
    when(repositorioPlanificadorMock.obtenerAlimentosDisponibles()).thenReturn(alimentosEnStock);

    PlanAlimenticio planObtenido = servicioPlanificador.generarPlanParaUsuario(
      EMAIL_SIN_OPCIONES,
      null
    );

    assertThat(planObtenido, notNullValue());
    assertThat(planObtenido.getAlimentosAsignados(), hasSize(0));
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
    String tipoComida,
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
    alimento.setTipoComida(tipoComida);
    alimento.setEsVegetariano(esVegetariano);
    alimento.setContieneLactosa(contieneLactosa);
    alimento.setEsCeliaco(false);
    alimento.setCalorias(calorias);
    alimento.setProteinas(proteinas);
    alimento.setCarbohidratos(carbohidratos);
    alimento.setGrasas(grasas);
    return alimento;
  }

  private Comida crearComida(Alimento alimento, TipoDeComida tipo) {
    Comida comida = new Comida();
    comida.setNombre("Plato de " + alimento.getNombre());
    comida.setTipo(tipo);

    ItemComida item = new ItemComida();
    item.setAlimento(alimento);
    item.setCantidadGramos(1000.0);

    comida.setItems(List.of(item));
    return comida;
  }
}
