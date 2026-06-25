package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "PLAN_ALIMENTICIO")
public class PlanAlimenticio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private double costoTotalPlan;
  private int totalCalorias;
  private double totalProteinas;
  private double totalCarbohidratos;
  private double totalGrasas;

  @ElementCollection(fetch = FetchType.LAZY)
  private List<String> advertencias = new ArrayList<>();

  @OneToMany(
    mappedBy = "planAlimenticio",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<DiaPlan> cronogramaDias = new ArrayList<>();

  @Transient
  private List<Alimento> alimentosAsignados = new ArrayList<>();

  //comidas

  public PlanAlimenticio() {}

  public List<DiaPlan> getCronogramaDias() {
    return cronogramaDias;
  }

  public void setCronogramaDias(List<DiaPlan> cronogramaDias) {
    this.cronogramaDias = cronogramaDias;
  }

  public double getCostoTotalPlan() {
    return costoTotalPlan;
  }

  public void setCostoTotalPlan(double costoTotalPlan) {
    this.costoTotalPlan = costoTotalPlan;
  }

  public int getTotalCalorias() {
    return totalCalorias;
  }

  public void setTotalCalorias(int totalCalorias) {
    this.totalCalorias = totalCalorias;
  }

  public double getTotalProteinas() {
    return totalProteinas;
  }

  public void setTotalProteinas(double totalProteinas) {
    this.totalProteinas = totalProteinas;
  }

  public double getTotalCarbohidratos() {
    return totalCarbohidratos;
  }

  public void setTotalCarbohidratos(double totalCarbohidratos) {
    this.totalCarbohidratos = totalCarbohidratos;
  }

  public double getTotalGrasas() {
    return totalGrasas;
  }

  public void setTotalGrasas(double totalGrasas) {
    this.totalGrasas = totalGrasas;
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
