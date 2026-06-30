package com.tallerwebi.dominio.excepcion;

public class MontoPresupuestoInsuficienteException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private String mensaje;

  public MontoPresupuestoInsuficienteException(String mensaje) {
    this.mensaje = mensaje;
  }

  public MontoPresupuestoInsuficienteException(double presupuestoMinimoDiario, int intervalo) {
    this.mensaje =
      "El monto mínimo para un intervalo de " +
      intervalo +
      " días es de $" +
      presupuestoMinimoDiario * intervalo +
      ".";
  }

  public String getMensaje() {
    return mensaje;
  }
}
