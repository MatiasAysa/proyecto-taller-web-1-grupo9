package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Comida {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private TipoDeComida tipo;

  @Transient
  private List<ItemComida> items = new ArrayList<ItemComida>();

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

  public List<ItemComida> getItems() {
    return items;
  }

  public void setItems(List<ItemComida> items) {
    this.items = items;
  }
}
