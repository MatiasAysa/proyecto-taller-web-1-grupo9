package com.tallerwebi.dominio;

import java.util.Date;
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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "desayuno_id")
  private Alimento desayuno;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "almuerzo_id")
  private Alimento almuerzo;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "cena_id")
  private Alimento cena;

  public DiaPlan() {}

  public DiaPlan(int numeroDia, Date fechaCalendario, PlanAlimenticio planAlimenticio) {
    this.numeroDia = numeroDia;
    this.fechaCalendario = fechaCalendario;
    this.planAlimenticio = planAlimenticio;
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

  public Alimento getDesayuno() {
    return desayuno;
  }

  public void setDesayuno(Alimento desayuno) {
    this.desayuno = desayuno;
  }

  public Alimento getAlmuerzo() {
    return almuerzo;
  }

  public void setAlmuerzo(Alimento almuerzo) {
    this.almuerzo = almuerzo;
  }

  public Alimento getCena() {
    return cena;
  }

  public void setCena(Alimento cena) {
    this.cena = cena;
  }
}
