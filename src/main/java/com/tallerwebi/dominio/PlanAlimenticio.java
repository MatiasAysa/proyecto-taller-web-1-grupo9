package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public class PlanAlimenticio {

  private Long id;
  private Double costoTotalPlan;
  private List<String> advertencias = new ArrayList<>();
  private List<Alimento> alimentosAsignados = new ArrayList<>();
  private Integer totalCalorias = 0;
  private Double totalProteinas = 0.0;
  private Double totalCarbohidratos = 0.0;
  private Double totalGrasas = 0.0;

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

  public Integer getTotalCalorias() {
    return totalCalorias;
  }

  public void setTotalCalorias(Integer totalCalorias) {
    this.totalCalorias = totalCalorias;
  }

  public Double getTotalProteinas() {
    return totalProteinas;
  }

  public void setTotalProteinas(Double totalProteinas) {
    this.totalProteinas = totalProteinas;
  }

  public Double getTotalCarbohidratos() {
    return totalCarbohidratos;
  }

  public void setTotalCarbohidratos(Double totalCarbohidratos) {
    this.totalCarbohidratos = totalCarbohidratos;
  }

  public Double getTotalGrasas() {
    return totalGrasas;
  }

  public void setTotalGrasas(Double totalGrasas) {
    this.totalGrasas = totalGrasas;
  }
}
