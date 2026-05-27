package com.tallerwebi.dominio;

public class ServicioPresupuestoImpl implements ServicioPresupuesto {

  @Override
  public Presupuesto crearPresupuesto(float monto, float intervalo) {
    if (monto <= 0 || intervalo <= 0) return null;
    return new Presupuesto(monto, intervalo);
  }
}
