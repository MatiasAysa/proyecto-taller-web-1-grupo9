package com.tallerwebi.dominio;

public class Direccion {

  private String ubicacion;
  private int numero;
  private Cordenandas cordenanda;

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String direccion) {
    this.ubicacion = direccion;
  }

  public int getNumero() {
    return numero;
  }

  public Cordenandas getCordenanda() {
    return cordenanda;
  }

  public void setCordenanda(Cordenandas cordenanda) {
    this.cordenanda = cordenanda;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }
}
