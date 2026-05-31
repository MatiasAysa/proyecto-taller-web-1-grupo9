package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class RestriccionAlimentaria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String nombre;

  @ManyToOne
  @JoinColumn(name = "perfil_alimentario_usuario_id")
  private PerfilAlimentarioUsuario perfil;

  public RestriccionAlimentaria() {}

  public RestriccionAlimentaria(PerfilAlimentarioUsuario perfil, String nombre) {
    this.perfil = perfil;
    this.nombre = nombre;
  }

  public PerfilAlimentarioUsuario getPerfil() {
    return perfil;
  }

  public void setPerfil(PerfilAlimentarioUsuario perfil) {
    this.perfil = perfil;
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
