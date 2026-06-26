package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Alimento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;

  //private String tipoComida;
  //PRECIO POR KILO DEL ALIMENTO
  private Double precioPorKilo;
  //SE DEBERIA ELIMITAR ESTOS ATRIBUTOS BOOLEAN Y PASARLOS A LA COMIDA
  private Boolean esVegetariano;
  private Boolean esCeliaco;
  private Boolean contieneLactosa = false;

  @Embedded
  private InformacionNutricional infoNutricional = new InformacionNutricional();

  @Column(name = "url_supermercado", length = 500)
  private String urlSupermercado;

  public String getUrlSupermercado() {
    return urlSupermercado;
  }

  public void setUrlSupermercado(String urlSupermercado) {
    this.urlSupermercado = urlSupermercado;
  }

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

  public Double getPrecioPorKilo() {
    return precioPorKilo;
  }

  public void setPrecioPorKilo(Double precio) {
    this.precioPorKilo = precio;
  }

  /*
  public String getTipoComida() {
    return tipoComida;
  }

  public void setTipoComida(String tipoComida) {
    this.tipoComida = tipoComida;
  }
  */
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

  public Integer getCalorias() {
    return infoNutricional.getCaloriasX100g();
  }

  public void setCalorias(Integer calorias) {
    infoNutricional.setCaloriasX100g(calorias);
  }

  public Double getProteinas() {
    return infoNutricional.getProteinasX100g();
  }

  public void setProteinas(Double proteinas) {
    infoNutricional.setProteinasX100g(proteinas);
  }

  public Double getCarbohidratos() {
    return infoNutricional.getCarbohidratosX100g();
  }

  public void setCarbohidratos(Double carbohidratos) {
    infoNutricional.setCarbohidratosX100g(carbohidratos);
  }

  public Double getGrasas() {
    return infoNutricional.getGrasasX100g();
  }

  public void setGrasas(Double grasas) {
    infoNutricional.setGrasasX100g(grasas);
  }
}
