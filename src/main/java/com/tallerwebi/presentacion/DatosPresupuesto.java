package com.tallerwebi.presentacion;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class DatosPresupuesto {

  private float monto;
  private int intervalo;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate fecha;

  public DatosPresupuesto() {}

  public float getMonto() {
    return monto;
  }

  public int getIntervalo() {
    return intervalo;
  }

  public void setMonto(float monto) {
    this.monto = monto;
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
