package com.tallerwebi.dominio;

import javax.persistence.Embeddable;

@Embeddable
public class InformacionNutricional {

  private Integer caloriasX100g;
  private Double proteinasX100g;
  private Double carbohidratosX100g;
  private Double grasasX100g;

  public Integer getCaloriasX100g() {
    return caloriasX100g;
  }

  public void setCaloriasX100g(Integer calorias) {
    this.caloriasX100g = calorias;
  }

  public Double getProteinasX100g() {
    return proteinasX100g;
  }

  public void setProteinasX100g(Double proteinas) {
    this.proteinasX100g = proteinas;
  }

  public Double getCarbohidratosX100g() {
    return carbohidratosX100g;
  }

  public void setCarbohidratosX100g(Double carbohidratos) {
    this.carbohidratosX100g = carbohidratos;
  }

  public Double getGrasasX100g() {
    return grasasX100g;
  }

  public void setGrasasX100g(Double grasas) {
    this.grasasX100g = grasas;
  }
}
