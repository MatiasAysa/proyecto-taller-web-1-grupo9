package com.tallerwebi.dominio;

import java.util.List;
import javax.persistence.*;

@Entity
public class Comida {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private TipoDeComida tipo;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Ingrediente> items;

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

  public List<Ingrediente> getItems() {
    return items;
  }

  public void setItems(List<Ingrediente> items) {
    this.items = items;
  }
}
