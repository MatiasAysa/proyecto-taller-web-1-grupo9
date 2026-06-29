package com.tallerwebi.dominio;

import java.util.List;
import javax.persistence.*;

@Entity
public class Comida {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;

  @Enumerated(EnumType.STRING)
  private TipoDeComida tipo;

  private Boolean celiaco;
  private Boolean diabetico;
  private Boolean intoleranciaLactosa;
  private Boolean vegetariano;
  private Boolean vegano;

  @OneToMany(mappedBy = "comida")
  private List<Ingrediente> ingredientes;

  public Boolean getCeliaco() {
    return celiaco;
  }

  public void setCeliaco(Boolean celiaco) {
    this.celiaco = celiaco;
  }

  public Boolean getDiabetico() {
    return diabetico;
  }

  public void setDiabetico(Boolean diabetico) {
    this.diabetico = diabetico;
  }

  public Boolean getIntoleranciaLactosa() {
    return intoleranciaLactosa;
  }

  public void setIntoleranciaLactosa(Boolean intoleranciaLactosa) {
    this.intoleranciaLactosa = intoleranciaLactosa;
  }

  public Boolean getVegetariano() {
    return vegetariano;
  }

  public void setVegetariano(Boolean vegetariano) {
    this.vegetariano = vegetariano;
  }

  public Boolean getVegano() {
    return vegano;
  }

  public void setVegano(Boolean vegano) {
    this.vegano = vegano;
  }

  public List<Ingrediente> getIngredientes() {
    return this.ingredientes;
  }

  public void setIngredientes(List<Ingrediente> ingredientes) {
    this.ingredientes = ingredientes;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public TipoDeComida getTipo() {
    return tipo;
  }

  public void setTipo(TipoDeComida tipo) {
    this.tipo = tipo;
  }
}
