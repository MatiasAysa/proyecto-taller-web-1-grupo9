package com.tallerwebi.dominio;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Alimento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  //private String tipoComida;
  private Double precioEstimado;

  @Embedded
  private InformacionNutricional infoNutricional = new InformacionNutricional();

  //el precio estimado es el precio del alimento por kg
  //tal vez en caso de frutas,es mejor usar un precio por la una unidad del alimento,tipo una banana
  //private Boolean esVegetariano;
 // private Boolean esCeliaco;
  //private Boolean contieneLactosa = false;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Double getPrecioEstimado() {
    return precioEstimado;
  }

  public void setPrecioEstimado(Double precio) {
    this.precioEstimado = precio;
  }
/*
  public String getTipoComida() {
    return tipoComida;
  }

  public void setTipoComida(String tipoComida) {
    this.tipoComida = tipoComida;
  }

  public Boolean getEsVegetariano() {
    return esVegetariano;
  }

  public void setEsVegetariano(Boolean esVegetariano) {
    this.esVegetariano = esVegetariano;
  }

  public Boolean getEsCeliaco() {
    return esCeliaco;
  }

  public void setEsCeliaco(Boolean esCeliaco) {
    this.esCeliaco = esCeliaco;
  }

  public Boolean getContieneLactosa() {
    return contieneLactosa;
  }

  public void setContieneLactosa(Boolean contieneLactosa) {
    this.contieneLactosa = contieneLactosa;
  }
*/
  public Integer getCaloriasX100g() {
    return infoNutricional.getCaloriasX100g();
  }

  public void setCaloriasX100g(Integer caloriasX100g) {
    infoNutricional.setCaloriasX100g(caloriasX100g);
  }

  public Double getProteinasX100g() {
    return infoNutricional.getProteinasX100g();
  }

  public void setProteinasX100g(Double proteinasX100g) {
    infoNutricional.setProteinasX100g(proteinasX100g);
  }

  public Double getCarbohidratosX100g() {
    return infoNutricional.getCarbohidratosX100g();
  }

  public void setCarbohidratosX100g(Double carbohidratosX100g) {
    infoNutricional.setCarbohidratosX100g(carbohidratosX100g);
  }

  public Double getGrasasX100() {
    return infoNutricional.getGrasasX100();
  }

  public void setGrasasX100(Double grasasX) {
    infoNutricional.setGrasasX100(grasasX);
  }
}
















