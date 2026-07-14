package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ItemComida;

public class IngredienteDTO {

  private String nombre;
  private double cantidad;

  public IngredienteDTO() {}

  public IngredienteDTO(ItemComida ingrediente) {
    this.nombre = ingrediente.getAlimento().getNombreGenerico();
    this.cantidad = ingrediente.getCantidadGramos();
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public double getCantidad() {
    return cantidad;
  }

  public void setCantidad(double cantidad) {
    this.cantidad = cantidad;
  }
}
