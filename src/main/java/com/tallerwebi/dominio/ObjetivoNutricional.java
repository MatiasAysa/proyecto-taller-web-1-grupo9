package com.tallerwebi.dominio;

public class ObjetivoNutricional {

  private Integer calorias;
  private Double proteinas;
  private Double carbohidratos;
  private Double grasas;

  public ObjetivoNutricional(
    Integer calorias,
    Double proteinas,
    Double carbohidratos,
    Double grasas
  ) {
    this.calorias = calorias;
    this.proteinas = proteinas;
    this.carbohidratos = carbohidratos;
    this.grasas = grasas;
  }

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

  @Override
  public String toString() {
    return (
      "Calorías: " +
      calorias +
      " | Proteínas: " +
      proteinas +
      " | Carbohidratos: " +
      carbohidratos +
      " | Grasas: " +
      grasas
    );
  }
}
