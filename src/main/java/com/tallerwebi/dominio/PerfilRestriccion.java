package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class PerfilRestriccion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "perfil_id")
  private PerfilAlimentarioUsuario perfil;

  @ManyToOne
  @JoinColumn(name = "restriccion_id")
  private RestriccionAlimentaria restriccion;

  public PerfilRestriccion() {}

  public PerfilRestriccion(PerfilAlimentarioUsuario perfil, RestriccionAlimentaria restriccion) {
    this.perfil = perfil;
    this.restriccion = restriccion;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PerfilAlimentarioUsuario getPerfil() {
    return perfil;
  }

  public void setPerfil(PerfilAlimentarioUsuario perfil) {
    this.perfil = perfil;
  }

  public RestriccionAlimentaria getRestriccion() {
    return restriccion;
  }

  public void setRestriccion(RestriccionAlimentaria restriccion) {
    this.restriccion = restriccion;
  }
}
