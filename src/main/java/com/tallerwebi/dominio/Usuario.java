package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
@SuppressWarnings("PMD.TooManyFields")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String password;
  private String rol;
  private Boolean activo = false;

  //private Double presupuestoSemanal;
  //private Double peso;
  //private String actividadFisica;
  //private Boolean esVegetariano = false;
  //private Boolean esIntoleranteLactosa = false;
  //private String objetivo;

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
    this.activo = true;
  }

  /*
  public Double getPresupuestoSemanal() {
    return presupuestoSemanal;
  }

  public void setPresupuestoSemanal(Double presupuestoSemanal) {
    this.presupuestoSemanal = presupuestoSemanal;
  }

  public Double getPeso() {
    return peso;
  }

  public void setPeso(Double peso) {
    this.peso = peso;
  }

  public String getActividadFisica() {
    return actividadFisica;
  }

  public void setActividadFisica(String actividadFisica) {
    this.actividadFisica = actividadFisica;
  }

  public Boolean getEsVegetariano() {
    return esVegetariano;
  }

  public void setEsVegetariano(Boolean esVegetariano) {
    this.esVegetariano = esVegetariano;
  }

  public Boolean getEsIntoleranteLactosa() {
    return esIntoleranteLactosa;
  }

  public void setEsIntoleranteLactosa(Boolean esIntoleranteLactosa) {
    this.esIntoleranteLactosa = esIntoleranteLactosa;
  }

  public String getObjetivo() {
    return objetivo;
  }

  public void setObjetivo(String objetivo) {
    this.objetivo = objetivo;
  }

  public void setContieneLactosa(Boolean contieneLactosa) {
    this.esIntoleranteLactosa = !contieneLactosa;
  }
*/
  public PerfilAlimentarioUsuario getPerfilAlimentario() {
    return perfilAlimentario;
  }

  public void setPerfilAlimentario(PerfilAlimentarioUsuario perfilAlimentario) {
    this.perfilAlimentario = perfilAlimentario;
  }
}
