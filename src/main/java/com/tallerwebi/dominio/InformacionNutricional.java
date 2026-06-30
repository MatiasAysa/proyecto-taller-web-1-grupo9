package com.tallerwebi.dominio;

import javax.persistence.Embeddable;

@Embeddable
public class InformacionNutricional {

  private Integer calorias;
  private Double proteinas;
  private Double carbohidratos;
  private Double grasas;

  public Integer getCalorias() {
    return calorias;
  }

  public void setCalorias(Integer calorias) {
    this.calorias = calorias;
  }

  public Double getProteinas() {
    return proteinas;
  }

  public void setProteinas(Double proteinas) {
    this.proteinas = proteinas;
  }

  public Double getCarbohidratos() {
    return carbohidratos;
  }

  public void setCarbohidratos(Double carbohidratos) {
    this.carbohidratos = carbohidratos;
  }

  public Double getGrasas() {
    return grasas;
  }

  public void setGrasas(Double grasas) {
    this.grasas = grasas;
  }
}
