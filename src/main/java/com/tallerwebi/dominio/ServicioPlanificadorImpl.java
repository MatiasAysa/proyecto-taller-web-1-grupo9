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

    List<Alimento> alimentosAptos = ordenarAlimentosPorObjetivo(
      obtenerAlimentosFiltradosPorPerfil(
        repositorioPlanificador.obtenerAlimentosDisponibles(),
        usuario.getPerfilAlimentario()
      ),
      usuario.getPerfilAlimentario()
    );

    PlanAlimenticio plan = new PlanAlimenticio();
    List<String> advertencias = generarAdvertenciasPorObjetivo(usuario.getPerfilAlimentario());
    advertencias.add("Duración del plan: " + diasTotales + " días.");
    plan.setAdvertencias(advertencias);

    int calObj = CalculadorNutricionalHelper.calcularCaloriasObjetivo(
      usuario.getPerfilAlimentario(),
      diasTotales
    );
    armarEstructuraNutricionalYCostosNueva(
      plan,
      alimentosAptos,
      presupuestoEntity.getMonto(),
      diasTotales,
      calObj
    );
    return plan;
  }

  private List<Alimento> ordenarAlimentosPorObjetivo(
    List<Alimento> alimentos,
    PerfilAlimentarioUsuario perfil
  ) {
    if (perfil == null || perfil.getObjetivo() == null) {
      return alimentos;
    }
    String obj = perfil.getObjetivo().toUpperCase(Locale.ROOT);
    if (STR_GANAR_PESO.equals(obj)) {
      return alimentos
        .stream()
        .sorted((a1, a2) ->
          Double.compare(
            a2.getProteinas() != null ? a2.getProteinas() : 0.0,
            a1.getProteinas() != null ? a1.getProteinas() : 0.0
          )
        )
        .collect(Collectors.toList());
    }
    if (STR_PERDER_PESO.equals(obj)) {
      return alimentos
        .stream()
        .sorted((a1, a2) ->
          Double.compare(
            a1.getGrasas() != null ? a1.getGrasas() : 0.0,
            a2.getGrasas() != null ? a2.getGrasas() : 0.0
          )
        )
        .collect(Collectors.toList());
    }
    return alimentos;
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

  private List<Alimento> obtenerAlimentosFiltradosPorPerfil(
    List<Alimento> alimentos,
    PerfilAlimentarioUsuario perfil
  ) {
    if (
      perfil == null ||
      perfil.getPerfilRestricciones() == null ||
      perfil.getPerfilRestricciones().isEmpty()
    ) {
      return alimentos;
    }
    List<Alimento> aptos = new ArrayList<>();
    for (Alimento alim : alimentos) {
      if (verificarAlimentoApto(alim, perfil.getPerfilRestricciones())) {
        aptos.add(alim);
      }
    }
    return aptos;
  }

  private boolean verificarAlimentoApto(Alimento alim, Set<PerfilRestriccion> restricciones) {
    for (PerfilRestriccion pr : restricciones) {
      if (
        pr.getRestriccion() != null &&
        pr.getRestriccion().getNombre() != null &&
        !evaluarFiltrosEspecificos(alim, pr.getRestriccion().getNombre().toUpperCase(Locale.ROOT))
      ) {
        return false;
      }
    }
    return true;
  }

  private boolean evaluarFiltrosEspecificos(Alimento alim, String restriccionNombre) {
    if (
      (RESTRICCION_VEGETARIANO.equals(restriccionNombre) ||
        RESTRICCION_VEGANO.equals(restriccionNombre)) &&
      (alim.getEsVegetariano() == null || !alim.getEsVegetariano())
    ) {
      return false;
    }
    if (
      RESTRICCION_LACTOSA.equals(restriccionNombre) &&
      alim.getContieneLactosa() != null &&
      alim.getContieneLactosa()
    ) {
      return false;
    }
    return (
      !RESTRICCION_CELIACO.equals(restriccionNombre) ||
      (alim.getEsCeliaco() != null && alim.getEsCeliaco())
    );
  }

  @Override
  public void armarEstructuraNutricionalYCostosNueva(
    PlanAlimenticio plan,
    List<Alimento> alimentos,
    float monto,
    int dias,
    int calObj
  ) {
    for (Alimento alim : alimentos) {
      if (alim.getUrlSupermercado() != null && !alim.getUrlSupermercado().trim().isEmpty()) {
        Double pr = scraperService.obtenerPrecioReal(alim.getUrlSupermercado());
        if (pr != null && pr > 0) {
          alim.setPrecioEstimado(pr);
        }
      }
    }
    plan.setAlimentosAsignados(alimentos);
    plan.setCostoTotalPlan((double) monto);

    List<Alimento> des = alimentos
      .stream()
      .filter(a -> COMIDA_DESAYUNO.equalsIgnoreCase(a.getTipoComida()))
      .collect(Collectors.toList());
    List<Alimento> alm = alimentos
      .stream()
      .filter(a -> COMIDA_ALMUERZO.equalsIgnoreCase(a.getTipoComida()))
      .collect(Collectors.toList());
    List<Alimento> cen = alimentos
      .stream()
      .filter(a -> COMIDA_CENA.equalsIgnoreCase(a.getTipoComida()))
      .collect(Collectors.toList());

    if (des.isEmpty()) {
      des.addAll(alimentos);
    }
    if (alm.isEmpty()) {
      alm.addAll(alimentos);
    }
    if (cen.isEmpty()) {
      cen.addAll(alimentos);
    }

    plan.setCronogramaDias(generarCronogramaDias(des, alm, cen, dias, plan));
    CalculadorNutricionalHelper.calcularYSetearMacros(plan, alimentos, dias, calObj);
  }

  private List<DiaPlan> generarCronogramaDias(
    List<Alimento> des,
    List<Alimento> alm,
    List<Alimento> cen,
    int dias,
    PlanAlimenticio plan
  ) {
    List<DiaPlan> cronograma = new ArrayList<>();
    for (int diaIndex = 1; diaIndex <= dias; diaIndex++) {
      long tiempoDesplazado =
        System.currentTimeMillis() + ((long) (diaIndex - 1) * MILISEGUNDOS_DIARIOS);
      DiaPlan dia = new DiaPlan(diaIndex, new Date(tiempoDesplazado), plan);
      inyectarOpcionesComida(dia, des, alm, cen, diaIndex);
      cronograma.add(dia);
    }
    return cronograma;
  }

  private void inyectarOpcionesComida(
    DiaPlan dia,
    List<Alimento> des,
    List<Alimento> alm,
    List<Alimento> cen,
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
}
