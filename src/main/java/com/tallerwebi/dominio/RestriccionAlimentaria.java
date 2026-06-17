package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class RestriccionAlimentaria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String nombre;

  public RestriccionAlimentaria() {}

  public RestriccionAlimentaria(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
