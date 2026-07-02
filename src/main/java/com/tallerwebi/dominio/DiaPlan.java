package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
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
    inverseJoinColumns = @JoinColumn(name = "comida_id")
  )
  private List<Comida> opcionesComidas = new ArrayList<>();

  public DiaPlan() {}

  public DiaPlan(int numeroDia, Date fechaCalendario, PlanAlimenticio planAlimenticio) {
    this.numeroDia = numeroDia;
    this.fechaCalendario = fechaCalendario;
    this.planAlimenticio = planAlimenticio;
  }

  public List<Comida> getOpcionesDesayuno() {
    return opcionesComidas
      .stream()
      .filter(c -> c.getTipo() != null && "DESAYUNO".equalsIgnoreCase(c.getTipo().name()))
      .collect(Collectors.toList());
  }

  public List<Comida> getOpcionesAlmuerzo() {
    return opcionesComidas
      .stream()
      .filter(c -> c.getTipo() != null && "ALMUERZO".equalsIgnoreCase(c.getTipo().name()))
      .collect(Collectors.toList());
  }

  public List<Comida> getOpcionesCena() {
    return opcionesComidas
      .stream()
      .filter(c -> c.getTipo() != null && "CENA".equalsIgnoreCase(c.getTipo().name()))
      .collect(Collectors.toList());
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

  // 🌟 GETTER/SETTER de la colección general mapeada por Hibernate
  public List<Comida> getOpcionesAlimentos() {
    return opcionesComidas;
  }

  public void setOpcionesAlimentos(List<Comida> opcionesComidas) {
    this.opcionesComidas = opcionesComidas;
  }
}
