package com.tallerwebi.dominio;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
public class Presupuesto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private float monto;
  private int intervalo;
  private LocalDate fecha;

  @OneToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public float getMonto() {
    return monto;
  }

  public void setMonto(float monto) {
    this.monto = monto;
  }

  public int getIntervalo() {
    return intervalo;
  }

  public void setIntervalo(int intervalo) {
    this.intervalo = intervalo;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }
}
