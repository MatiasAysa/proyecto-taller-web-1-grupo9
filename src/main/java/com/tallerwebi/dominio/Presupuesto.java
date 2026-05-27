package com.tallerwebi.dominio;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Presupuesto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private float monto;
  private float intervalo;
  private LocalDate fecha;

  public Presupuesto(float monto, float intervalo, LocalDate fecha) {
    this.monto = monto;
    this.intervalo = intervalo;
    this.fecha = fecha;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public float getMonto() {
    return monto;
  }

  public void setMonto(float monto) {
    this.monto = monto;
  }

  public float getIntervalo() {
    return intervalo;
  }

  public void setIntervalo(float intervalo) {
    this.intervalo = intervalo;
  }
}
