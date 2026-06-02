package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public class PlanAlimenticio {

  private Long id;
  private Double costoTotalPlan;
  private List<String> advertencias = new ArrayList<>();
  private List<Alimento> alimentosAsignados = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getCostoTotalPlan() {
    return costoTotalPlan;
  }

  public void setCostoTotalPlan(Double costoTotalPlan) {
    this.costoTotalPlan = costoTotalPlan;
  }

  public List<String> getAdvertencias() {
    return advertencias;
  }

  public void setAdvertencias(List<String> advertencias) {
    this.advertencias = advertencias;
  }

  public List<Alimento> getAlimentosAsignados() {
    return alimentosAsignados;
  }

  public void setAlimentosAsignados(List<Alimento> alimentosAsignados) {
    this.alimentosAsignados = alimentosAsignados;
  }
}
