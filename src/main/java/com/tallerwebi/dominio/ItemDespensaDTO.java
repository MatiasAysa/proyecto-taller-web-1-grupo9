package com.tallerwebi.dominio;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings({ "PMD.TooManyFields", "CPD-START" })
public class ItemDespensaDTO {

  private String nombre;
  private Double cantidad;
  private TipoDeComida tipoComida;
  private Boolean esVegetariano;
  private Boolean esCeliaco;
  private Boolean contieneLactosa;
  private InformacionNutricional infoNutricional = new InformacionNutricional();
  private Long idAlimentoExistenteEnBaseDatos;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date fechaVencimiento;

  public ItemDespensaDTO() {}

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Double getCantidad() {
    return cantidad;
  }

  public void setCantidad(Double cantidad) {
    this.cantidad = cantidad;
  }

  public TipoDeComida getTipoComida() {
    return tipoComida;
  }

  public void setTipoComida(TipoDeComida tipoComida) {
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

  public InformacionNutricional getInfoNutricional() {
    return infoNutricional;
  }

  public void setInfoNutricional(InformacionNutricional infoNutricional) {
    this.infoNutricional = infoNutricional;
  }

  public Date getFechaVencimiento() {
    return fechaVencimiento;
  }

  public void setFechaVencimiento(Date fechaVencimiento) {
    this.fechaVencimiento = fechaVencimiento;
  }

  public Long getIdAlimentoExistenteEnBaseDatos() {
    return idAlimentoExistenteEnBaseDatos;
  }

  public void setIdAlimentoExistenteEnBaseDatos(Long idAlimentoExistenteEnBaseDatos) {
    this.idAlimentoExistenteEnBaseDatos = idAlimentoExistenteEnBaseDatos;
  }
}
