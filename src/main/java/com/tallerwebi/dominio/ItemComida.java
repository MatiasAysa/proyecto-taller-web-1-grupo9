package com.tallerwebi.dominio;

import javax.persistence.*;

@Embeddable
public class ItemComida {

  private Double cantidadGramos;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "alimento_id")
  private Alimento alimento;

  public ItemComida() {}

  public ItemComida(Double cantidadGramos, Alimento alimento) {
    this.cantidadGramos = cantidadGramos;
    this.alimento = alimento;
  }

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
