package com.tallerwebi.dominio;

public class ItemCompra {

  private Alimento alimento;
  private Double cantidadTotal;
  private Double precoTotal;

  public Double getPrecoTotal() {
    return precoTotal;
  }

  public void setPrecoTotal(Double precoTotal) {
    this.precoTotal = precoTotal;
  }

  public Double getCantidadTotal() {
    return cantidadTotal;
  }

  public void setCantidadTotal(Double cantidadTotal) {
    this.cantidadTotal = cantidadTotal;
  }

  public Alimento getAlimento() {
    return alimento;
  }

  public void setAlimento(Alimento alimento) {
    this.alimento = alimento;
  }
}
