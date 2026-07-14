package com.tallerwebi.dominio;

public class Supermercado {

  private String nombre;
  private Cordenandas cordenadas;
  private Double distanciaMetros;
  private Integer minutosCaminando;
  private String direccionName;

  public String getDireccionName() {
    return direccionName;
  }

  public void setDireccionName(String direccionName) {
    this.direccionName = direccionName;
  }

  public Double getDistanciaMetros() {
    return distanciaMetros;
  }

  public void setDistanciaMetros(Double distanciaMetros) {
    this.distanciaMetros = distanciaMetros;
  }

  public Integer getMinutosCaminando() {
    return minutosCaminando;
  }

  public void setMinutosCaminando(Integer minutosCaminando) {
    this.minutosCaminando = minutosCaminando;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Cordenandas getCordenadas() {
    return cordenadas;
  }

  public void setCordenadas(Cordenandas cordenadas) {
    this.cordenadas = cordenadas;
  }
}
