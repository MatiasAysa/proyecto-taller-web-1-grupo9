package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import java.util.ArrayList;
import java.util.Calendar;
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
  private static final double FACTOR_PORCION_BALANCEADA = 2.0;
  private static final double MULTIPLICADOR_REDONDEO = 100.0;
  private static final int CALORIAS_BASE_REGULATORIAS = 2000;

  private static final String STR_DIARIO_NUM = "1";
  private static final String STR_DIARIO_TEXTO = "DIARIO";
  private static final String STR_QUINCENAL_NUM = "15";
  private static final String STR_QUINCENAL_TEXTO = "QUINCENAL";
  private static final String STR_MENSUAL_NUM = "30";
  private static final String STR_MENSUAL_TEXTO = "MENSUAL";
  private static final String STR_PERDER_PESO = "PERDER_PESO";
  private static final String STR_GANAR_PESO = "GANAR_PESO";
  private static final String STR_SEXO_FEM = "F";
  private static final String STR_ACT_MODERADA = "MODERADA";
  private static final String STR_ACT_ACTIVA = "ACTIVA";
  private static final String STR_NULL_LITERAL = "null";

  private static final double HB_FEM_BASE = 447.593;
  private static final double HB_FEM_PESO = 9.247;
  private static final double HB_FEM_ALTURA = 3.098;
  private static final double HB_FEM_EDAD = 4.330;

  private static final double HB_MASC_BASE = 88.362;
  private static final double HB_MASC_PESO = 13.397;
  private static final double HB_MASC_ALTURA = 4.799;
  private static final double HB_MASC_EDAD = 5.677;

  private static final double FACTOR_ACT_SEDENTARIA = 1.2;
  private static final double FACTOR_ACT_MODERADA = 1.55;
  private static final double FACTOR_ACT_ACTIVA = 1.9;

  private static final double MODIFICADOR_DEFICIT = 0.85;
  private static final double MODIFICADOR_SUPERAVIT = 1.15;

  private final RepositorioPlanificador repositorioPlanificador;
  private final RepositorioPresupuesto repositorioPresupuesto;
  private final RepositorioUsuario repositorioUsuario;

  @Autowired
  public ServicioPlanificadorImpl(
    RepositorioPlanificador repositorioPlanificador,
    RepositorioPresupuesto repositorioPresupuesto,
    RepositorioUsuario repositorioUsuario
  ) {
    this.repositorioPlanificador = repositorioPlanificador;
    this.repositorioPresupuesto = repositorioPresupuesto;
    this.repositorioUsuario = repositorioUsuario;
  }

  @Override
  public PlanAlimenticio generarPlanParaUsuario(String email, String tipoDuracion)
    throws PresupuestoInsuficienteException, UsuarioInexistenteException {
    if (email == null) {
      throw new UsuarioInexistenteException("El email proporcionado es nulo.");
    }

    Usuario usuario = repositorioUsuario.buscar(email);
    if (usuario == null) {
      throw new UsuarioInexistenteException("El usuario solicitado no existe en el sistema.");
    }
    Presupuesto presupuestoEntity = repositorioPresupuesto.buscarPresupuesto(usuario);
    if (presupuestoEntity == null) {
      throw new PresupuestoInsuficienteException(
        "Primero debes configurar un presupuesto en tu perfil."
      );
    }
    String duracionEvaluar = obtenerDuracionSegura(tipoDuracion, presupuestoEntity);

    int diasTotales = determinarDiasPorDuracion(duracionEvaluar);
    float montoDisponible = presupuestoEntity.getMonto();

    validarPresupuestoMinimoNuevo(montoDisponible, diasTotales);

    List<Alimento> todosLosAlimentos = repositorioPlanificador.obtenerAlimentosDisponibles();
    List<Alimento> alimentosAptos = obtenerAlimentosFiltradosPorPerfil(
      todosLosAlimentos,
      usuario.getPerfilAlimentario()
    );

    int caloriasObjetivoPeriodo = calcularCaloriasObjetivo(
      usuario.getPerfilAlimentario(),
      diasTotales
    );

    PlanAlimenticio plan = new PlanAlimenticio();
    List<String> advertencias = generarAdvertenciasPorObjetivo(usuario.getPerfilAlimentario());
    advertencias.add("Duración del plan: " + diasTotales + " días.");
    plan.setAdvertencias(advertencias);

    armarEstructuraNutricionalYCostosNueva(
      plan,
      alimentosAptos,
      montoDisponible,
      diasTotales,
      caloriasObjetivoPeriodo
    );

    return plan;
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
    double presupuestoMinimoRequerido = PRESUPUESTO_MINIMO_DIARIO * (double) dias;
    if ((double) monto < presupuestoMinimoRequerido) {
      throw new PresupuestoInsuficienteException(
        "El monto de $" +
        monto +
        " es insuficiente para cubrir un plan de " +
        dias +
        " dias de forma saludable. Requerido minimo: $" +
        presupuestoMinimoRequerido
      );
    }
  }

  private int calcularCaloriasObjetivo(PerfilAlimentarioUsuario perfil, int dias) {
    if (
      perfil == null ||
      perfil.getPeso() == null ||
      perfil.getAltura() == null ||
      perfil.getEdad() == null
    ) {
      return CALORIAS_BASE_REGULATORIAS * dias;
    }

    double metabolismoBasal;
    if (STR_SEXO_FEM.equalsIgnoreCase(perfil.getSexo())) {
      metabolismoBasal =
        HB_FEM_BASE +
        (HB_FEM_PESO * perfil.getPeso()) +
        (HB_FEM_ALTURA * perfil.getAltura()) -
        (HB_FEM_EDAD * perfil.getEdad());
    } else {
      metabolismoBasal =
        HB_MASC_BASE +
        (HB_MASC_PESO * perfil.getPeso()) +
        (HB_MASC_ALTURA * perfil.getAltura()) -
        (HB_MASC_EDAD * perfil.getEdad());
    }

    double gastoEnergeticoTotal =
      metabolismoBasal * obtenerFactorActividad(perfil.getActividadFisica());

    if (STR_PERDER_PESO.equalsIgnoreCase(perfil.getObjetivo())) {
      gastoEnergeticoTotal = gastoEnergeticoTotal * MODIFICADOR_DEFICIT;
    } else if (STR_GANAR_PESO.equalsIgnoreCase(perfil.getObjetivo())) {
      gastoEnergeticoTotal = gastoEnergeticoTotal * MODIFICADOR_SUPERAVIT;
    }
    return (int) (gastoEnergeticoTotal * (double) dias);
  }

  private double obtenerFactorActividad(String actividad) {
    if (actividad == null) {
      return FACTOR_ACT_SEDENTARIA;
    }
    if (STR_ACT_MODERADA.equalsIgnoreCase(actividad)) {
      return FACTOR_ACT_MODERADA;
    }
    if (STR_ACT_ACTIVA.equalsIgnoreCase(actividad)) {
      return FACTOR_ACT_ACTIVA;
    }
    return FACTOR_ACT_SEDENTARIA;
  }

  private List<Alimento> obtenerAlimentosFiltradosPorPerfil(
    List<Alimento> alimentos,
    PerfilAlimentarioUsuario perfil
  ) {
    if (
      perfil == null ||
      perfil.getRestriccionesAlimentarias() == null ||
      perfil.getRestriccionesAlimentarias().isEmpty()
    ) {
      return alimentos;
    }

    List<Alimento> aptos = new ArrayList<>();
    for (Alimento alim : alimentos) {
      if (
        verificarAlimentoApto(
          alim,
          perfil
            .getRestriccionesAlimentarias()
            .stream()
            .map(r -> r.getNombre().toUpperCase(Locale.ROOT))
            .collect(Collectors.toSet())
        )
      ) {
        aptos.add(alim);
      }
    }
    return aptos;
  }

  private boolean verificarAlimentoApto(Alimento alim, Set<String> restricciones) {
    if (
      (restricciones.contains("VEGETARIANO") || restricciones.contains("VEGANO")) &&
      (alim.getEsVegetariano() == null || !alim.getEsVegetariano())
    ) {
      return false;
    }
    if (
      restricciones.contains("INTOLERANCIA_LACTOSA") &&
      alim.getContieneLactosa() != null &&
      alim.getContieneLactosa()
    ) {
      return false;
    }
    return (
      !restricciones.contains("CELIACO") || (alim.getEsCeliaco() != null && alim.getEsCeliaco())
    );
  }

  @Override
  public void armarEstructuraNutricionalYCostosNueva(
    PlanAlimenticio plan,
    List<Alimento> alimentos,
    float montoDisponibleTotal,
    int dias,
    int caloriasObjetivo
  ) {
    plan.setAlimentosAsignados(alimentos);
    plan.setCostoTotalPlan((double) montoDisponibleTotal);

    List<Alimento> desayunos = alimentos
      .stream()
      .filter(a -> "DESAYUNO".equalsIgnoreCase(a.getTipoComida()))
      .collect(Collectors.toList());
    List<Alimento> almuerzos = alimentos
      .stream()
      .filter(a -> "ALMUERZO".equalsIgnoreCase(a.getTipoComida()))
      .collect(Collectors.toList());
    List<Alimento> cenas = alimentos
      .stream()
      .filter(a -> "CENA".equalsIgnoreCase(a.getTipoComida()))
      .collect(Collectors.toList());

    if (desayunos.isEmpty()) desayunos.addAll(alimentos);
    if (almuerzos.isEmpty()) almuerzos.addAll(alimentos);
    if (cenas.isEmpty()) cenas.addAll(alimentos);

    List<DiaPlan> cronograma = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();

    for (int i = 1; i <= dias; i++) {
      DiaPlan dia = new DiaPlan(i, calendar.getTime(), plan);

      dia.setDesayuno(desayunos.get((i - 1) % desayunos.size()));
      dia.setAlmuerzo(almuerzos.get((i - 1) % almuerzos.size()));
      dia.setCena(cenas.get((i - 1) % cenas.size()));

      cronograma.add(dia);

      calendar.add(Calendar.DAY_OF_YEAR, 1);
    }
    int dummyVar = calendar.get(Calendar.YEAR);
    if (dummyVar == 0) {
      calendar.clear();
    }

    plan.setCronogramaDias(cronograma);
    calcularYSetearMacros(plan, alimentos, dias, caloriasObjetivo);
  }

  private void calcularYSetearMacros(
    PlanAlimenticio plan,
    List<Alimento> filtrados,
    int dias,
    int caloriasObjetivo
  ) {
    int caloriasPlan = 0;
    double proteinas = 0.0;
    double carbohidratos = 0.0;
    double grasas = 0.0;

    for (Alimento alimentoSeleccionado : filtrados) {
      int caloriasIndividuales = alimentoSeleccionado.getCalorias() != null
        ? alimentoSeleccionado.getCalorias()
        : 0;
      double proteinasIndividuales = alimentoSeleccionado.getProteinas() != null
        ? alimentoSeleccionado.getProteinas()
        : 0.0;
      double carbohidratosIndividuales = alimentoSeleccionado.getCarbohidratos() != null
        ? alimentoSeleccionado.getCarbohidratos()
        : 0.0;
      double grasasIndividuales = alimentoSeleccionado.getGrasas() != null
        ? alimentoSeleccionado.getGrasas()
        : 0.0;

      caloriasPlan += (int) (caloriasIndividuales * ((double) dias / FACTOR_PORCION_BALANCEADA));
      proteinas += proteinasIndividuales * ((double) dias / FACTOR_PORCION_BALANCEADA);
      carbohidratos += carbohidratosIndividuales * ((double) dias / FACTOR_PORCION_BALANCEADA);
      grasas += grasasIndividuales * ((double) dias / FACTOR_PORCION_BALANCEADA);
    }

    if (caloriasPlan > caloriasObjetivo) {
      double factorAjuste = (double) caloriasObjetivo / (double) caloriasPlan;
      caloriasPlan = caloriasObjetivo;
      proteinas = proteinas * factorAjuste;
      carbohidratos = carbohidratos * factorAjuste;
      grasas = grasas * factorAjuste;
    }

    plan.setTotalCalorias(caloriasPlan);
    plan.setTotalProteinas(Math.round(proteinas * MULTIPLICADOR_REDONDEO) / MULTIPLICADOR_REDONDEO);
    plan.setTotalCarbohidratos(
      Math.round(carbohidratos * MULTIPLICADOR_REDONDEO) / MULTIPLICADOR_REDONDEO
    );
    plan.setTotalGrasas(Math.round(grasas * MULTIPLICADOR_REDONDEO) / MULTIPLICADOR_REDONDEO);
  }

  private List<String> generarAdvertenciasPorObjetivo(PerfilAlimentarioUsuario perfil) {
    List<String> ads = new ArrayList<>();
    if (perfil == null) {
      return ads;
    }
    if (STR_PERDER_PESO.equalsIgnoreCase(perfil.getObjetivo())) {
      ads.add(
        "El plan aplica un deficit calorico controlado para alcanzar tu objetivo de perder peso de forma segura."
      );
    }
    if (STR_GANAR_PESO.equalsIgnoreCase(perfil.getObjetivo())) {
      ads.add(
        "Se incremento la densidad calorica y proteica para favorecer la ganancia de masa muscular."
      );
    }
    return ads;
  }
}
