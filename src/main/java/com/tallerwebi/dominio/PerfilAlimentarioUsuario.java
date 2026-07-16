package com.tallerwebi.dominio;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class PerfilAlimentarioUsuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double peso;
  private Double altura;
  private Integer edad;
  private String sexo;
  private String actividadFisica;

  @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PerfilRestriccion> perfilRestricciones;

  private String objetivo;

  public PerfilAlimentarioUsuario() {}

  public Double getPeso() {
    return peso;
  }

  public void setPeso(Double peso) {
    this.peso = peso;
  }

  public Double getAltura() {
    return altura;
  }

  public void setAltura(Double altura) {
    this.altura = altura;
  }

  public Integer getEdad() {
    return edad;
  }

  public void setEdad(Integer edad) {
    this.edad = edad;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public String getActividadFisica() {
    return actividadFisica;
  }

  public void setActividadFisica(String actividadFisica) {
    this.actividadFisica = actividadFisica;
  }

  public String getObjetivo() {
    return objetivo;
  }

  public void setObjetivo(String objetivo) {
    this.objetivo = objetivo;
  }

  public Set<PerfilRestriccion> getPerfilRestricciones() {
    return perfilRestricciones;
  }

  public void setPerfilRestricciones(Set<PerfilRestriccion> perfilRestricciones) {
    this.perfilRestricciones = perfilRestricciones;
  }

  public Long getId() {
    return id;
  }
}
