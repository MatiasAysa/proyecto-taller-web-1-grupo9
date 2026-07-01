package com.tallerwebi.presentacion;

import java.util.List;

public class DatosReceta {

  private List<IngredienteDTO> ingredientes;
  private String nombre;
  private String tipo;

  public List<IngredienteDTO> getIngredientes() {
    return ingredientes;
  }

  public void setIngredientes(List<IngredienteDTO> ingredientes) {
    this.ingredientes = ingredientes;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
}
