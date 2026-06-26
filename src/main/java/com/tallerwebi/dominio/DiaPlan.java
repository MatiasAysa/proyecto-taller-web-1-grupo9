package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
@Table(name = "DIA_PLAN")
public class DiaPlan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int numeroDia;

  @Temporal(TemporalType.DATE)
  private Date fechaCalendario;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "plan_alimenticio_id")
  private PlanAlimenticio planAlimenticio;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "DIA_PLAN_OPCIONES",
    joinColumns = @JoinColumn(name = "dia_plan_id"),
    inverseJoinColumns = @JoinColumn(name = "alimento_id")
  )
  //comidas
  private List<Alimento> opcionesAlimentos = new ArrayList<>();

  public DiaPlan() {}

  public DiaPlan(int numeroDia, Date fechaCalendario, PlanAlimenticio planAlimenticio) {
    this.numeroDia = numeroDia;
    this.fechaCalendario = fechaCalendario;
    this.planAlimenticio = planAlimenticio;
  }

  public List<Alimento> getOpcionesDesayuno() {
    /*
    return opcionesAlimentos
      .stream()
      .filter(a -> "DESAYUNO".equals(a.getTipoComida().toString()))
      .collect(Collectors.toList());
    */

    return opcionesAlimentos;
  }

  public List<Alimento> getOpcionesAlmuerzo() {
    /*
    return opcionesAlimentos
      .stream()
      .filter(a -> "ALMUERZO".equals(a.getTipoComida().toString()))
      .collect(Collectors.toList());
  */
    return opcionesAlimentos;
  }

  public List<Alimento> getOpcionesCena() {
    /*
    return opcionesAlimentos
      .stream()
      .filter(a -> "CENA".equals(a.getTipoComida().toString()))
      .collect(Collectors.toList());

     */
    return opcionesAlimentos;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getNumeroDia() {
    return numeroDia;
  }

  public void setNumeroDia(int numeroDia) {
    this.numeroDia = numeroDia;
  }

  public Date getFechaCalendario() {
    return fechaCalendario;
  }

  public void setFechaCalendario(Date fechaCalendario) {
    this.fechaCalendario = fechaCalendario;
  }

  public PlanAlimenticio getPlanAlimenticio() {
    return planAlimenticio;
  }

  public void setPlanAlimenticio(PlanAlimenticio planAlimenticio) {
    this.planAlimenticio = planAlimenticio;
  }

  public List<Alimento> getOpcionesAlimentos() {
    return opcionesAlimentos;
  }

  public void setOpcionesAlimentos(List<Alimento> opcionesAlimentos) {
    this.opcionesAlimentos = opcionesAlimentos;
  }
}
