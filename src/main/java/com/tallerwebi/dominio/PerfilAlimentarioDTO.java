package com.tallerwebi.dominio;

public class PerfilAlimentarioDTO {

  private Double peso;
  private Double altura;
  private Integer edad;
  private String sexo;
  private String actividadFisica;
  private String restriccionesAlimentarias;
  private String objetivo;

  public PerfilAlimentarioDTO() {}

  // Escribirlos en una sola línea rompe la similitud de estructura con la Entidad
  public Integer getEdad() {
    return edad;
  }

  public void setEdad(Integer edad) {
    this.edad = edad;
  }

  public String getObjetivo() {
    return objetivo;
  }

  public void setObjetivo(String objetivo) {
    this.objetivo = objetivo;
  }

  public Double getAltura() {
    return altura;
  }

  public void setAltura(Double altura) {
    this.altura = altura;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public Double getPeso() {
    return peso;
  }

  public void setPeso(Double peso) {
    this.peso = peso;
  }

  public String getActividadFisica() {
    return actividadFisica;
  }

  public void setActividadFisica(String actividadFisica) {
    this.actividadFisica = actividadFisica;
  }

  public String getRestriccionesAlimentarias() {
    return restriccionesAlimentarias;
  }

  public void setRestriccionesAlimentarias(String restriccionesAlimentarias) {
    this.restriccionesAlimentarias = restriccionesAlimentarias;
  }
}
