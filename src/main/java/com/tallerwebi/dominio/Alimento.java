package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Alimento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private String tipoComida;
  private Double precioEstimado;

  private Boolean esVegetariano;
  private Boolean esCeliaco;
  private Boolean contieneLactosa = false;

  @Embedded
  private InformacionNutricional infoNutricional = new InformacionNutricional();

  @Column(name = "url_supermercado", length = 500)
  private String urlSupermercado;

  private String nombreGenerico;

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

  public Double getPrecioEstimado() {
    return precioEstimado;
  }

  public void setPrecioEstimado(Double precio) {
    this.precioEstimado = precio;
  }

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

  public Integer getCalorias() {
    return infoNutricional.getCalorias();
  }

  public void setCalorias(Integer calorias) {
    infoNutricional.setCalorias(calorias);
  }

  public Double getProteinas() {
    return infoNutricional.getProteinas();
  }

  public void setProteinas(Double proteinas) {
    infoNutricional.setProteinas(proteinas);
  }

  public Double getCarbohidratos() {
    return infoNutricional.getCarbohidratos();
  }

  public void setCarbohidratos(Double carbohidratos) {
    infoNutricional.setCarbohidratos(carbohidratos);
  }

  public Double getGrasas() {
    return infoNutricional.getGrasas();
  }

  public void setGrasas(Double grasas) {
    infoNutricional.setGrasas(grasas);
  }

  public String getNombreGenerico() {
    return nombreGenerico;
  }

  public void setNombreGenerico(String nombreGenerico) {
    this.nombreGenerico = nombreGenerico;
  }
}
