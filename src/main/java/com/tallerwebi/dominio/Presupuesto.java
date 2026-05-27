package com.tallerwebi.dominio;

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

  public Presupuesto(float monto, float intervalo) {
    this.monto = monto;
    this.intervalo = intervalo;
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
