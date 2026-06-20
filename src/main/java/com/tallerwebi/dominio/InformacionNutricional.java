package com.tallerwebi.dominio;

import javax.persistence.Embeddable;

@Embeddable
public class InformacionNutricional {

  //TODO EN FUNCION A 100 GRAMOS , CALORIAS CADA 100 GRAMOS DE UN ALIMENTO
  private Integer caloriasX100g;
  private Double proteinasX100g;
  private Double carbohidratosX100g;
  private Double grasasX100g;

  public Integer getCaloriasX100g() {
    return caloriasX100g;
  }

  public void setCaloriasX100g(Integer caloriasX100g) {
    this.caloriasX100g = caloriasX100g;
  }

  public Double getProteinasX100g() {
    return proteinasX100g;
  }

  public void setProteinasX100g(Double proteinasX100g) {
    this.proteinasX100g = proteinasX100g;
  }

  public Double getCarbohidratosX100g() {
    return carbohidratosX100g;
  }

  public void setCarbohidratosX100g(Double carbohidratosX100g) {
    this.carbohidratosX100g = carbohidratosX100g;
  }

  public Double getGrasasX100g() {
    return this.grasasX100g;
  }

  public void setGrasasX100g(Double grasasX100g) {
    this.grasasX100g = grasasX100g;
  }
}
