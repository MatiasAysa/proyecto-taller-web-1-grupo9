package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ingrediente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double cantidadGramos;

  @ManyToOne
  private Alimento alimento;

  @ManyToOne
  private Comida comida;

  public Double getCantidadGramos() {
    return cantidadGramos;
  }

  public void setCantidadGramos(Double cantidadGramos) {
    this.cantidadGramos = cantidadGramos;
  }

  public Alimento getAlimento() {
    return alimento;
  }

  public void setAlimento(Alimento alimento) {
    this.alimento = alimento;
  }
}
