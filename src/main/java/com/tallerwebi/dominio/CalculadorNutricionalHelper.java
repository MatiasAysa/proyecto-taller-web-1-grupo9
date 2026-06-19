package com.tallerwebi.dominio;

import java.util.List;

public final class CalculadorNutricionalHelper {

  private static final int CALORIAS_BASE_REGULATORIAS = 2000;
  private static final double FACTOR_PORCION_BALANCEADA = 2.0;
  private static final double MULTIPLICADOR_REDONDEO = 100.0;
  private static final String SEXO_FEMENINO = "F";
  private static final String STR_PERDER_PESO = "PERDER_PESO";
  private static final String STR_GANAR_PESO = "GANAR_PESO";
  private static final String ACTIVIDAD_MODERADA = "MODERADA";
  private static final String ACTIVIDAD_ACTIVA = "ACTIVA";

  private CalculadorNutricionalHelper() {}

  public static int calcularCaloriasObjetivo(PerfilAlimentarioUsuario perfil, int dias) {
    if (
      perfil == null ||
      perfil.getPeso() == null ||
      perfil.getAltura() == null ||
      perfil.getEdad() == null
    ) {
      return CALORIAS_BASE_REGULATORIAS * dias;
    }

    double mb = SEXO_FEMENINO.equalsIgnoreCase(perfil.getSexo())
      ? 447.593 +
      (9.247 * perfil.getPeso()) +
      (3.098 * perfil.getAltura()) -
      (4.330 * perfil.getEdad())
      : 88.362 +
      (13.397 * perfil.getPeso()) +
      (4.799 * perfil.getAltura()) -
      (5.677 * perfil.getEdad());

    double act = obtenerActividad(perfil.getActividadFisica());
    double obj = obtenerObjetivo(perfil.getObjetivo());

    return (int) (mb * act * obj * (double) dias);
  }

  private static double obtenerActividad(String actividad) {
    if (ACTIVIDAD_MODERADA.equalsIgnoreCase(actividad)) {
      return 1.55;
    }
    if (ACTIVIDAD_ACTIVA.equalsIgnoreCase(actividad)) {
      return 1.9;
    }
    return 1.2;
  }

  private static double obtenerObjetivo(String objetivo) {
    if (STR_PERDER_PESO.equalsIgnoreCase(objetivo)) {
      return 0.85;
    }
    if (STR_GANAR_PESO.equalsIgnoreCase(objetivo)) {
      return 1.15;
    }
    return 1.0;
  }

  public static void calcularYSetearMacros(
    PlanAlimenticio plan,
    List<Alimento> filtrados,
    int dias,
    int caloriasObjetivo
  ) {
    int calPlan = 0;
    double prot = 0.0;
    double carb = 0.0;
    double gras = 0.0;

    for (Alimento alim : filtrados) {
      double factor = (double) dias / FACTOR_PORCION_BALANCEADA;
      calPlan += (int) ((alim.getCalorias() != null ? alim.getCalorias() : 0) * factor);
      prot += (alim.getProteinas() != null ? alim.getProteinas() : 0.0) * factor;
      carb += (alim.getCarbohidratos() != null ? alim.getCarbohidratos() : 0.0) * factor;
      gras += (alim.getGrasas() != null ? alim.getGrasas() : 0.0) * factor;
    }

    double fAjuste = calPlan > caloriasObjetivo
      ? (double) caloriasObjetivo / (double) calPlan
      : 1.0;

    plan.setTotalCalorias(calPlan > caloriasObjetivo ? caloriasObjetivo : calPlan);
    plan.setTotalProteinas(
      Math.round((prot * fAjuste) * MULTIPLICADOR_REDONDEO) / MULTIPLICADOR_REDONDEO
    );
    plan.setTotalCarbohidratos(
      Math.round((carb * fAjuste) * MULTIPLICADOR_REDONDEO) / MULTIPLICADOR_REDONDEO
    );
    plan.setTotalGrasas(
      Math.round((gras * fAjuste) * MULTIPLICADOR_REDONDEO) / MULTIPLICADOR_REDONDEO
    );
  }
}
