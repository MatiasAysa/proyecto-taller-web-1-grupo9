package com.tallerwebi.dominio;

import java.util.Date;
import javax.persistence.*;

@SuppressWarnings("PMD.TooManyFields")
@Entity
public class ItemDespensa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Usuario usuario;

  private String nombre;
  private TipoDeComida tipoComida;
  private Boolean esVegetariano;
  private Boolean esCeliaco;
  private Boolean contieneLactosa;

  @Embedded
  private InformacionNutricional infoNutricional = new InformacionNutricional();

  @Enumerated(EnumType.STRING)
  private UnidadMedidaTipo unidadMedidaTipo;

  private Double cantidad;
  private Date fechaVencimiento;

  @ManyToOne(cascade = CascadeType.PERSIST)
  private Alimento alimento;

  public ItemDespensa() {}

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
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

  public UnidadMedidaTipo getUnidadMedidaTipo() {
    return unidadMedidaTipo;
  }

  public void setUnidadMedidaTipo(UnidadMedidaTipo unidadMedidaTipo) {
    this.unidadMedidaTipo = unidadMedidaTipo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public Double getCantidad() {
    return cantidad;
  }

  public void setCantidad(Double cantidad) {
    this.cantidad = cantidad;
  }

  public Integer getFechaVencimiento() {
    if (fechaVencimiento == null) {
      return null;
    }

    java.time.LocalDate hoy = java.time.LocalDate.now();
    java.time.LocalDate vencimiento = new java.sql.Date(fechaVencimiento.getTime()).toLocalDate();
    long diferenciaDias = java.time.temporal.ChronoUnit.DAYS.between(hoy, vencimiento);
    if (diferenciaDias <= 0) return null;

    return (int) diferenciaDias;
  }

  public void setFechaVencimiento(Date fechaVencimiento) {
    this.fechaVencimiento = fechaVencimiento;
  }

  public Alimento getAlimento() {
    return alimento;
  }

  public void setAlimento(Alimento alimento) {
    this.alimento = alimento;
  }
}
