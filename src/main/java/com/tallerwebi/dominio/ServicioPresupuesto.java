package com.tallerwebi.dominio;

import java.time.LocalDate;

public interface ServicioPresupuesto {
  Presupuesto crearPresupuesto(float monto, int intervalo, LocalDate fecha);
}
