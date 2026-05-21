package com.tallerwebi.dominio;

import java.util.List;

public class PlanAlimenticio {

  private Long id;
  private Double costoTotalPlan;
  private List<String> advertencias;

  public List<String> getAdvertencias() {
    return advertencias;
  }

  public void setAdvertencias(List<String> advertencias) {
    this.advertencias = advertencias;
  }

  public Double getCostoTotalPlan() {
    return costoTotalPlan;
  }

  public void setCostoTotalPlan(Double costoTotalPlan) {
    this.costoTotalPlan = costoTotalPlan;
  }

  public Long getId() {
    return id;
  }
}
