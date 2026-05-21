package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PerfilAlimentarioUsuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double peso;
  private Double altura;
  private Integer edad;
  private String sexo;
  private String actividadFisica;
  private String restriccionesAlimentarias;
  private String objetivo;

  public PerfilAlimentarioUsuario() {}

  public PerfilAlimentarioUsuario(
    Double peso,
    Double altura,
    Integer edad,
    String sexo,
    String actividadFisica,
    String restriccionesAlimentarias,
    String objetivo
  ) {
    this.peso = peso;
    this.altura = altura;
    this.edad = edad;
    this.sexo = sexo;
    this.actividadFisica = actividadFisica;
    this.restriccionesAlimentarias = restriccionesAlimentarias;
    this.objetivo = objetivo;
  }

  public Double getPeso() {
    return peso;
  }

  public void setPeso(Double peso) {
    this.peso = peso;
  }

  public Double getAltura() {
    return altura;
  }

  public void setAltura(Double altura) {
    this.altura = altura;
  }

  public Integer getEdad() {
    return edad;
  }

  public void setEdad(Integer edad) {
    this.edad = edad;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
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

  public String getObjetivo() {
    return objetivo;
  }

  public void setObjetivo(String objetivo) {
    this.objetivo = objetivo;
  }
}
