package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class ItemDespensa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Usuario usuario;

  @ManyToOne
  private Alimento alimento;
 
  private Double gramosDisponibles;
  private Integer unidadesDisponibles;

  public ItemDespensa(
    Usuario usuario,
    Alimento alimento,
    Double gramosDisponibles,
    Integer unidadesDisponibles
  ) {
    this.usuario = usuario;
    this.alimento = alimento;
    this.gramosDisponibles = gramosDisponibles;
    this.unidadesDisponibles = unidadesDisponibles;
  }

  public ItemDespensa(Usuario usuario, Alimento alimento, Double gramosDisponibles) {
    this.usuario = usuario;
    this.alimento = alimento;
    this.gramosDisponibles = gramosDisponibles;
  }

  public ItemDespensa(Usuario usuario, Alimento alimento, Integer unidadesDisponibles) {
    this.usuario = usuario;
    this.alimento = alimento;
    this.unidadesDisponibles = unidadesDisponibles;
  }

  public ItemDespensa() {}

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

  public Alimento getAlimento() {
    return alimento;
  }

  public void setAlimento(Alimento alimento) {
    this.alimento = alimento;
  }

  public Double getGramosDisponibles() {
    return gramosDisponibles;
  }

  public void setGramosDisponibles(Double gramosDisponibles) {
    this.gramosDisponibles = gramosDisponibles;
  }

  public Integer getUnidadesDisponibles() {
    return unidadesDisponibles;
  }

  public void setUnidadesDisponibles(Integer unidadesDisponibles) {
    this.unidadesDisponibles = unidadesDisponibles;
  }
}
