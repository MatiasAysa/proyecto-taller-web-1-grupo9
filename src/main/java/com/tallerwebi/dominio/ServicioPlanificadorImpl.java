package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioPlanificador")
@Transactional
public class ServicioPlanificadorImpl implements ServicioPlanificador {

  private static final double PRESUPUESTO_MINIMO_DIARIO = 6000.0;
  private static final long MILISEGUNDOS_DIARIOS = 86400000L;

  private static final String STR_DIARIO_NUM = "1";
  private static final String STR_DIARIO_TEXTO = "DIARIO";
  private static final String STR_QUINCENAL_NUM = "15";
  private static final String STR_QUINCENAL_TEXTO = "QUINCENAL";
  private static final String STR_MENSUAL_NUM = "30";
  private static final String STR_MENSUAL_TEXTO = "MENSUAL";
  private static final String STR_PERDER_PESO = "PERDER_PESO";
  private static final String STR_GANAR_PESO = "GANAR_PESO";
  private static final String STR_NULL_LITERAL = "null";

  private static final String RESTRICCION_VEGETARIANO = "VEGETARIANO";
  private static final String RESTRICCION_VEGANO = "VEGANO";
  private static final String RESTRICCION_LACTOSA = "INTOLERANCIA_LACTOSA";
  private static final String RESTRICCION_CELIACO = "CELIACO";

  private static final String COMIDA_DESAYUNO = "DESAYUNO";
  private static final String COMIDA_ALMUERZO = "ALMUERZO";
  private static final String COMIDA_CENA = "CENA";

  private final RepositorioPlanificador repositorioPlanificador;
  private final RepositorioPresupuesto repositorioPresupuesto;
  private final RepositorioUsuario repositorioUsuario;
  private final ScraperService scraperService;

  @Autowired
  public ServicioPlanificadorImpl(
    RepositorioPlanificador repositorioPlanificador,
    RepositorioPresupuesto repositorioPresupuesto,
    RepositorioUsuario repositorioUsuario,
    ScraperService scraperService
  ) {
    this.repositorioPlanificador = repositorioPlanificador;
    this.repositorioPresupuesto = repositorioPresupuesto;
    this.repositorioUsuario = repositorioUsuario;
    this.scraperService = scraperService;
  }

  @Override
  public PlanAlimenticio generarPlanParaUsuario(String email, String tipoDuracion)
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    if (email == null) {
      throw new UsuarioInexistenteException("El email proporcionado es nulo.");
    }

    Usuario usuario = repositorioUsuario.buscar(email);
    if (usuario == null) {
      throw new UsuarioInexistenteException("El usuario solicitado no existe.");
    }

    Presupuesto presupuestoEntity = repositorioPresupuesto.buscarPresupuesto(usuario);
    if (presupuestoEntity == null) {
      throw new PresupuestoInsuficienteException("Primero debes configurar un presupuesto.");
    }

    int diasTotales = determinarDiasPorDuracion(
      obtenerDuracionSegura(tipoDuracion, presupuestoEntity)
    );

    validarPresupuestoMinimoNuevo(presupuestoEntity.getMonto(), diasTotales);
    actualizarPreciosPorScraping();

    List<Comida> comidasAptas = ordenarComidasPorObjetivo(
      obtenerComidasFiltradasPorPerfil(
        repositorioPlanificador.obtenerComidasDisponibles(),
        usuario
      ),
      usuario.getPerfilAlimentario()
    );

    PlanAlimenticio plan = inicializarPlanBase(
      usuario.getPerfilAlimentario(),
      diasTotales,
      presupuestoEntity.getMonto()
    );
    armarBloquesYAsignarCronograma(plan, comidasAptas, diasTotales, usuario.getPerfilAlimentario());

    return plan;
  }

  private PlanAlimenticio inicializarPlanBase(
    PerfilAlimentarioUsuario perfil,
    int diasTotales,
    float monto
  ) {
    PlanAlimenticio plan = new PlanAlimenticio();
    List<String> advertencias = generarAdvertenciasPorObjetivo(perfil);
    advertencias.add("Duración del plan: " + diasTotales + " días.");
    plan.setAdvertencias(advertencias);
    plan.setCostoTotalPlan((double) monto);
    return plan;
  }

  private void armarBloquesYAsignarCronograma(
    PlanAlimenticio plan,
    List<Comida> comidasAptas,
    int diasTotales,
    PerfilAlimentarioUsuario perfil
  ) {
    List<Comida> des = filtrarComidasPorTipo(comidasAptas, COMIDA_DESAYUNO);
    List<Comida> alm = filtrarComidasPorTipo(comidasAptas, COMIDA_ALMUERZO);
    List<Comida> cen = filtrarComidasPorTipo(comidasAptas, COMIDA_CENA);

    plan.setCronogramaDias(generarCronogramaDias(des, alm, cen, diasTotales, plan));

    List<Alimento> alimentosAsignados = comidasAptas
      .stream()
      .flatMap(c -> c.getItems().stream().map(ItemComida::getAlimento))
      .distinct()
      .collect(Collectors.toList());
    plan.setAlimentosAsignados(alimentosAsignados);
    int calObj = CalculadorNutricionalHelper.calcularCaloriasObjetivo(perfil, diasTotales);

    CalculadorNutricionalHelper.calcularYSetearMacros(
      plan,
      alimentosAsignados,
      diasTotales,
      calObj
    );
  }

  private List<Comida> filtrarComidasPorTipo(List<Comida> comidasAptas, String tipo) {
    List<Comida> filtradas = comidasAptas
      .stream()
      .filter(c -> tipo.equalsIgnoreCase(c.getTipo() != null ? c.getTipo().name() : ""))
      .collect(Collectors.toList());
    if (filtradas.isEmpty()) {
      filtradas.addAll(comidasAptas);
    }
    return filtradas;
  }

  private void actualizarPreciosPorScraping() {
    List<Alimento> todosLosAlimentos = repositorioPlanificador.obtenerAlimentosDisponibles();
    for (Alimento alim : todosLosAlimentos) {
      if (alim.getUrlSupermercado() != null && !alim.getUrlSupermercado().trim().isEmpty()) {
        Double pr = scraperService.obtenerPrecioReal(alim.getUrlSupermercado());
        if (pr != null && pr > 0) {
          alim.setPrecioEstimado(pr);
        }
      }
    }
  }

  private List<Comida> ordenarComidasPorObjetivo(
    List<Comida> comidas,
    PerfilAlimentarioUsuario perfil
  ) {
    if (perfil == null || perfil.getObjetivo() == null) {
      return comidas;
    }
    String obj = perfil.getObjetivo().toUpperCase(Locale.ROOT);
    if (STR_GANAR_PESO.equals(obj)) {
      return comidas
        .stream()
        .sorted((c1, c2) ->
          Double.compare(
            c2.getProteinas() != null ? c2.getProteinas() : 0.0,
            c1.getProteinas() != null ? c1.getProteinas() : 0.0
          )
        )
        .collect(Collectors.toList());
    }
    if (STR_PERDER_PESO.equals(obj)) {
      return comidas
        .stream()
        .sorted((c1, c2) ->
          Double.compare(
            c1.getGrasas() != null ? c1.getGrasas() : 0.0,
            c2.getGrasas() != null ? c2.getGrasas() : 0.0
          )
        )
        .collect(Collectors.toList());
    }
    return comidas;
  }

  private String obtenerDuracionSegura(String tipoDuracion, Presupuesto presupuestoEntity) {
    if (tipoDuracion == null || STR_NULL_LITERAL.equalsIgnoreCase(tipoDuracion)) {
      return String.valueOf(presupuestoEntity.getIntervalo());
    }
    return tipoDuracion;
  }

  private int determinarDiasPorDuracion(String tipoDuracion) {
    if (tipoDuracion == null) {
      return 7;
    }
    if (STR_DIARIO_NUM.equals(tipoDuracion) || STR_DIARIO_TEXTO.equalsIgnoreCase(tipoDuracion)) {
      return 1;
    }
    if (
      STR_QUINCENAL_NUM.equals(tipoDuracion) || STR_QUINCENAL_TEXTO.equalsIgnoreCase(tipoDuracion)
    ) {
      return 15;
    }
    if (STR_MENSUAL_NUM.equals(tipoDuracion) || STR_MENSUAL_TEXTO.equalsIgnoreCase(tipoDuracion)) {
      return 30;
    }
    return 7;
  }

  private void validarPresupuestoMinimoNuevo(float monto, int dias)
    throws PresupuestoInsuficienteException {
    if ((double) monto < (PRESUPUESTO_MINIMO_DIARIO * (double) dias)) {
      throw new PresupuestoInsuficienteException("El monto es insuficiente.");
    }
  }

  private List<Comida> obtenerComidasFiltradasPorPerfil(List<Comida> comidas, Usuario usuario) {
    PerfilAlimentarioUsuario perfil = usuario.getPerfilAlimentario();
    List<Comida> comidasFiltradasPorUsuario = obtenerComidasFiltradasPorUsuario(usuario, comidas);
    if (
      perfil == null ||
      perfil.getPerfilRestricciones() == null ||
      perfil.getPerfilRestricciones().isEmpty()
    ) {
      return comidasFiltradasPorUsuario;
    }
    List<Comida> aptas = new ArrayList<>();
    for (Comida com : comidasFiltradasPorUsuario) {
      if (verificarComidaApta(com, perfil.getPerfilRestricciones())) {
        aptas.add(com);
      }
    }
    return aptas;
  }

  private List<Comida> obtenerComidasFiltradasPorUsuario(Usuario usuario, List<Comida> comidas) {
    List<Comida> comidasFiltradas = new ArrayList<Comida>();
    for (Comida c : comidas) {
      if (c != null && (c.getAutor() == null || c.getAutor().equals(usuario))) comidasFiltradas.add(
        c
      );
    }
    return comidasFiltradas.isEmpty() ? comidas : comidasFiltradas;
  }

  private boolean verificarComidaApta(Comida com, Set<PerfilRestriccion> restricciones) {
    for (PerfilRestriccion pr : restricciones) {
      if (
        pr.getRestriccion() != null &&
        pr.getRestriccion().getNombre() != null &&
        !evaluarFiltrosPlato(com, pr.getRestriccion().getNombre().toUpperCase(Locale.ROOT))
      ) {
        return false;
      }
    }
    return true;
  }

  private boolean evaluarFiltrosPlato(Comida com, String restriccionNombre) {
    if (
      (RESTRICCION_VEGETARIANO.equals(restriccionNombre) ||
        RESTRICCION_VEGANO.equals(restriccionNombre)) &&
      (com.getEsVegetariano() == null || !com.getEsVegetariano())
    ) {
      return false;
    }
    if (
      RESTRICCION_LACTOSA.equals(restriccionNombre) &&
      com.getContieneLactosa() != null &&
      com.getContieneLactosa()
    ) {
      return false;
    }
    return (
      !RESTRICCION_CELIACO.equals(restriccionNombre) ||
      (com.getEsCeliaco() != null && com.getEsCeliaco())
    );
  }

  private List<DiaPlan> generarCronogramaDias(
    List<Comida> des,
    List<Comida> alm,
    List<Comida> cen,
    int dias,
    PlanAlimenticio plan
  ) {
    List<DiaPlan> cronograma = new ArrayList<>();
    for (int diaIndex = 1; diaIndex <= dias; diaIndex++) {
      long tiempoDesplazado =
        System.currentTimeMillis() + ((long) (diaIndex - 1) * MILISEGUNDOS_DIARIOS);
      DiaPlan dia = new DiaPlan(diaIndex, new Date(tiempoDesplazado), plan);
      inyectarOpcionesPlato(dia, des, alm, cen, diaIndex);
      cronograma.add(dia);
    }
    return cronograma;
  }

  private void inyectarOpcionesPlato(
    DiaPlan dia,
    List<Comida> des,
    List<Comida> alm,
    List<Comida> cen,
    int idx
  ) {
    if (!des.isEmpty()) {
      dia.getOpcionesAlimentos().add(des.get((idx - 1) % des.size()));
      dia.getOpcionesAlimentos().add(des.get(idx % des.size()));
      dia.getOpcionesAlimentos().add(des.get((idx + 1) % des.size()));
    }
    if (!alm.isEmpty()) {
      dia.getOpcionesAlimentos().add(alm.get((idx - 1) % alm.size()));
      dia.getOpcionesAlimentos().add(alm.get(idx % alm.size()));
      dia.getOpcionesAlimentos().add(alm.get((idx + 1) % alm.size()));
    }
    if (!cen.isEmpty()) {
      dia.getOpcionesAlimentos().add(cen.get((idx - 1) % cen.size()));
      dia.getOpcionesAlimentos().add(cen.get(idx % cen.size()));
      dia.getOpcionesAlimentos().add(cen.get((idx + 1) % cen.size()));
    }
  }

  private List<String> generarAdvertenciasPorObjetivo(PerfilAlimentarioUsuario perfil) {
    List<String> ads = new ArrayList<>();
    if (perfil != null && perfil.getObjetivo() != null) {
      if (STR_PERDER_PESO.equalsIgnoreCase(perfil.getObjetivo())) {
        ads.add("El plan aplica un deficit calorico controlado.");
      }
      if (STR_GANAR_PESO.equalsIgnoreCase(perfil.getObjetivo())) {
        ads.add("Se incremento la densidad calorica y proteica.");
      }
    }
    return ads;
  }

  @Override
  public void armarEstructuraNutricionalYCostosNueva(
    PlanAlimenticio plan,
    List<Alimento> alimentos,
    float monto,
    int dias,
    int calObj
  ) {}
}
