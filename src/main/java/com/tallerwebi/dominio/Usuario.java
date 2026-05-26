package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String password;
  private String rol;
  private Boolean activo = false;

  //Sincroniza las acciones del Usuario en su Perfil (Guardar/Borrar)
  // y elimina automáticamente el Perfil viejo de la base de datos si deja de estar asociado al Usuario.
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "perfil_alimentario_id")
  private PerfilAlimentarioUsuario perfilAlimentario;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public void activar() {
    activo = true;
  }

  public PerfilAlimentarioUsuario getPerfilAlimentario() {
    return perfilAlimentario;
  }

  public void setPerfilAlimentario(PerfilAlimentarioUsuario perfilAlimentario) {
    this.perfilAlimentario = perfilAlimentario;
  }
}
