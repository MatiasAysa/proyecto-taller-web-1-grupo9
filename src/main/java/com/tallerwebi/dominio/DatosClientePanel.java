package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.Set;

public class DatosClientePanel {

  //=========PERFIL ALIMENTARIO================
  private double peso;
  private double altura;
  private String objetivo;
  private String sexo;
  private int edad;
  private String actividadFisica;
  private Set<PerfilRestriccion> perfilRestricciones;

  //=========PRESUPUESTO================
  private LocalDate presupuestoFechaInicio;
  private int intervalo;
  private double monto;

  //=========DIRECCION================
  // private String calle;
  // private String numero;
  // private String ciudad;

  //=========================

  public DatosClientePanel(
    double peso,
    double altura,
    String objetivo,
    String sexo,
    int edad,
    String actividadFisica,
    Set<PerfilRestriccion> perfilRestricciones,
    LocalDate presupuestoFechaInicio,
    int intervalo,
    double monto
  ) {
    this.peso = peso;
    this.altura = altura;
    this.objetivo = objetivo;
    this.sexo = sexo;
    this.edad = edad;
    this.actividadFisica = actividadFisica;
    this.perfilRestricciones = perfilRestricciones;
    this.presupuestoFechaInicio = presupuestoFechaInicio;
    this.intervalo = intervalo;
    this.monto = monto;
  }

  public DatosClientePanel() {}

  public double getPeso() {
    return peso;
  }

  public void setPeso(double peso) {
    this.peso = peso;
  }

  public double getAltura() {
    return altura;
  }

  public void setAltura(double altura) {
    this.altura = altura;
  }

  public String getObjetivo() {
    return objetivo;
  }

  public void setObjetivo(String objetivo) {
    this.objetivo = objetivo;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public int getEdad() {
    return edad;
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  public String getActividadFisica() {
    return actividadFisica;
  }

  public void setActividadFisica(String actividadFisica) {
    this.actividadFisica = actividadFisica;
  }

  public Set<PerfilRestriccion> getPerfilRestricciones() {
    return perfilRestricciones;
  }

  public void setPerfilRestricciones(Set<PerfilRestriccion> perfilRestricciones) {
    this.perfilRestricciones = perfilRestricciones;
  }

  public LocalDate getPresupuestoFechaInicio() {
    return presupuestoFechaInicio;
  }

  public void setPresupuestoFechaInicio(LocalDate presupuestoFechaInicio) {
    this.presupuestoFechaInicio = presupuestoFechaInicio;
  }

  public int getIntervalo() {
    return intervalo;
  }

  public void setIntervalo(int intervalo) {
    this.intervalo = intervalo;
  }

  public double getMonto() {
    return monto;
  }

  public void setMonto(double monto) {
    this.monto = monto;
  }
}
